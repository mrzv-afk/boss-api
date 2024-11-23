package ru.morozov.bosses.plugin;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import ru.morozov.bosses.api.BossApi;
import ru.morozov.bosses.plugin.commands.SpawnEntityCommand;

/**
 * @author morozov
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class BossesPlugin extends JavaPlugin {

    BossApi bossApi;

    LiteCommands<CommandSender> builder;

    @Override
    public void onEnable() {
        bossApi = BossApi.create(this);
        bossApi.load();

        builder = LiteBukkitFactory.builder("boss-api")
                .commands(SpawnEntityCommand.create(bossApi))
                .build();
        builder.register();
    }

    @Override
    public void onDisable() {
        bossApi.unload();
        builder.unregister();
    }
}
