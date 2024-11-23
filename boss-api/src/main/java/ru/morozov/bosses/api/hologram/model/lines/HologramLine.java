package ru.morozov.bosses.api.hologram.model.lines;

import lombok.NonNull;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.morozov.bosses.api.entity.interfaces.PacketEntity;

import java.util.Collection;
import java.util.Set;

/**
 * @author morozov
 */
public interface HologramLine {

    @NotNull PacketEntity getEntity();
    @NotNull Hologram getHologram();
    @NotNull Location getLocation();

    double getLineModifier();

    void showTo(final Player player);
    void hideFrom(final Player player);

    void remove();

}
