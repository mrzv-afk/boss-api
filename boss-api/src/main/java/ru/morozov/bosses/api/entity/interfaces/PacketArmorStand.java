package ru.morozov.bosses.api.entity.interfaces;

import lombok.NonNull;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * @author morozov
 */
public interface PacketArmorStand {

    boolean isSmall();

    void setSmall(@NonNull final boolean small);

    boolean isInvisible();

    void setInvisible(@NotNull final boolean invisible);

    void setArms(@NotNull final boolean arms);

    boolean hasArms();

}
