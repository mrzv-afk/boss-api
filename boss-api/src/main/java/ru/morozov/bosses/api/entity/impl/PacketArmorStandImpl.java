package ru.morozov.bosses.api.entity.impl;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import ru.morozov.bosses.api.entity.interfaces.PacketArmorStand;

/**
 * @author morozov
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PacketArmorStandImpl extends PacketEntityBaseImpl implements PacketArmorStand {

    private PacketArmorStandImpl(@NonNull Location location) {
        super(location);
    }

    public static @NotNull PacketEntity create(final @NonNull Location location) {
        return new PacketArmorStandImpl(location);
    }

    @Override
    public @NotNull EntityType getEntityType() {
        return EntityType.ARMOR_STAND;
     }

    boolean small = false, invisible = true, arms = false;

    @Override
    public void setSmall(boolean small) {
        if (this.small != small) {
            this.small = small;
            sendMetadata();
        }
    }

    @Override
    public boolean isInvisible() {
        return invisible;
    }

    @Override
    public void setInvisible(boolean invisible) {
        if (this.invisible != invisible) {
            this.invisible = invisible;
            sendMetadata();
        }
    }

    @Override
    public boolean hasArms() {
        return arms;
    }

    @Override
    public void setArms(boolean arms) {
        if (this.arms != arms) {
            this.arms = arms;
            sendMetadata();
        }
    }

}
