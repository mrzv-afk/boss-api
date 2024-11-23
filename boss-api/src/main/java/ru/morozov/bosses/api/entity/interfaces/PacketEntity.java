package ru.morozov.bosses.api.entity.interfaces;

import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collection;
import java.util.Set;

/**
 * @author morozov
 */
public interface PacketEntity {

    @NotNull Location getLocation();
    void teleport(final @NonNull Location location);

    int getEntityId();
    @NotNull org.bukkit.entity.EntityType getEntityType();

    @NotNull com.comphenix.protocol.wrappers.WrappedDataWatcher getDataWatcher();

    void setCustomName(final @Nullable String name);
    @Nullable String getCustomName();
    boolean isCustomNameVisible();
    void setCustomNameVisible(final boolean visible);

    @NotNull
    @UnmodifiableView Set<Player> getViewers();

    default void addViewers(final @NonNull Player... players) {
        for (final Player player : players) {
            addViewer(player);
        }
    }

    void addViewers(final @NonNull Collection<Player> players);
    void addViewer(final @NonNull Player player);
    void removeViewer(final @NonNull Player player);

    void spawn();
    void spawn(final @NonNull Player player);
    void remove();
    void remove(final @NonNull Player player);

}
