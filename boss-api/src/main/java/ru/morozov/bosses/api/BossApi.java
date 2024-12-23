package ru.morozov.bosses.api;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.bukkit.plugin.java.JavaPlugin;
import org.jdbi.v3.cache.caffeine.CaffeineCachePlugin;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.async.JdbiExecutor;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.jetbrains.annotations.NotNull;
import ru.morozov.bosses.api.boss.SimpleBossManager;
import ru.morozov.bosses.api.boss.model.Boss;
import ru.morozov.bosses.api.config.GeneralConfig;
import ru.morozov.bosses.api.config.SimpleGeneralConfig;
import ru.morozov.bosses.api.entity.SimpleEntityManager;
import ru.morozov.bosses.api.entity.interfaces.PacketEntity;
import ru.morozov.bosses.api.hologram.SimpleHologramManager;
import ru.morozov.bosses.api.hologram.model.Hologram;
import ru.morozov.bosses.api.utils.Loadable;
import ru.morozov.bosses.api.utils.Manager;
import ru.morozov.bosses.api.utils.nms.NmsUtil;

import java.io.IOException;
import java.util.concurrent.Executors;

/**
 * @author morozov
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class BossApi implements Loadable {

    @NotNull JavaPlugin javaPlugin;

    public static @NotNull BossApi create(final @NonNull JavaPlugin javaPlugin) {
        return new BossApi(javaPlugin);
    }

    @NonFinal Manager<Hologram> hologramManager;

    @NonFinal Manager<Boss> bossManager;

    @NonFinal Manager<PacketEntity> entityManager;

    @NonFinal GeneralConfig generalConfig;

    @NonFinal Jdbi jdbi;

    @NonFinal JdbiExecutor executor;

    @Override
    public void load() {
        this.hologramManager = SimpleHologramManager.create();
        this.bossManager = SimpleBossManager.create();
        this.entityManager = SimpleEntityManager.create(javaPlugin);
        this.generalConfig = SimpleGeneralConfig.create(javaPlugin);

        generalConfig.load();
        NmsUtil.init(javaPlugin);
        initSQLite0();
    }

    @SneakyThrows
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void initSQLite0() {
        val file = javaPlugin.getDataFolder()
                .toPath()
                .resolve(generalConfig.getDatabaseFileName())
                .toFile();

        if (!file.exists()) file.createNewFile();

        val config = new HikariConfig();
        config.setDriverClassName("org.sqlite.JDBC");
        config.setJdbcUrl("jdbc:sqlite:" + file.getAbsolutePath());
        config.setConnectionTestQuery("SELECT 1");

        jdbi = Jdbi.create(new HikariDataSource(config));
        jdbi.installPlugin(new CaffeineCachePlugin());
        jdbi.installPlugin(new SqlObjectPlugin());
        executor = JdbiExecutor.create(jdbi, Executors.newFixedThreadPool(2));

        val ras = getClass().getResourceAsStream("/schema.sql");

        if (ras == null) {
            javaPlugin.getSLF4JLogger().warn("schema.sql not found");
            return;
        }

        try (val inputStream = ras) {
            jdbi.useHandle((handle) -> handle.createScript(new String(inputStream.readAllBytes()))
                    .execute());
        } catch (final IOException e) {
            javaPlugin.getSLF4JLogger().error("Failed to load schema.sql", e);
        }
    }

    @Override
    public void unload() {
        hologramManager.removeAll();
        bossManager.removeAll();
        entityManager.removeAll();
    }

}
