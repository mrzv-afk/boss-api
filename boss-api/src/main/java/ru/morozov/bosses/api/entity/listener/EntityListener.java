package ru.morozov.bosses.api.entity.listener;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import ru.morozov.bosses.api.entity.SimpleEntityManager;

/**
 * @author morozov
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @NonNull)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class EntityListener implements Listener {

    @NotNull JavaPlugin javaPlugin;

    @NotNull SimpleEntityManager entityManager;

    public static EntityListener create(
            final @NonNull JavaPlugin javaPlugin, final @NonNull SimpleEntityManager entityManager
    ) {
        return new EntityListener(javaPlugin, entityManager);
    }

    @EventHandler(ignoreCancelled = true)
    public void onAsyncJoin(final PlayerJoinEvent event) {
        val player = event.getPlayer();
        javaPlugin.getServer().getScheduler()
                .runTaskAsynchronously(javaPlugin, () -> entityManager.getEntities()
                        .forEach(entity -> entity.addViewer(player)));
    }

    @EventHandler(ignoreCancelled = true)
    public void onAsyncQuit(final PlayerQuitEvent event) {
        val player = event.getPlayer();
        javaPlugin.getServer().getScheduler()
                .runTaskAsynchronously(javaPlugin, () -> entityManager.getEntities()
                        .forEach(entity -> entity.removeViewer(player)));
    }

}
