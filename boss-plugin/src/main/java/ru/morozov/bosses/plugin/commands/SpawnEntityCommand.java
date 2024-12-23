package ru.morozov.bosses.plugin.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.morozov.bosses.api.BossApi;
import ru.morozov.bosses.api.entity.impl.PacketArmorStandImpl;
import ru.morozov.bosses.api.entity.interfaces.PacketEntity;

/**
 * @author morozov
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Command(name = "spawnentity", aliases = "sentity")
public class SpawnEntityCommand {

    BossApi bossApi;

    public static @NotNull SpawnEntityCommand create(final @NonNull BossApi bossApi) {
        return new SpawnEntityCommand(bossApi);
    }

    @Execute
    void execute(@Context CommandSender sender) {
        if (!(sender instanceof Player player))
            return;

        bossApi.getEntityManager().registerEntity(createArmorStand(player, player.getLocation()
                .clone().add(1, 0, 0)));

        player.sendMessage(Component.text("Entity created!")
                .color(NamedTextColor.GREEN));
    }

    private @NotNull PacketEntity createArmorStand(
            final @NonNull Player player, final @NonNull Location location
    ) {
        val impl = PacketArmorStandImpl.create(location);

        impl.setCustomNameVisible(true);
        impl.setCustomName("DiamondWorld Test Task");
        impl.spawn(player);

        return impl;
    }

}
