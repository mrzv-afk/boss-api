package ru.morozov.bosses.api.boss.model;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import ru.morozov.bosses.api.entity.interfaces.PacketEntity;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

/**
 * @author morozov
 */
@lombok.Getter
@lombok.EqualsAndHashCode(onlyExplicitlyIncluded = true)
@lombok.ToString(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SimpleBoss implements PacketEntity {

    @lombok.EqualsAndHashCode.Include @lombok.ToString.Include
    @NotNull Location location;

    @lombok.EqualsAndHashCode.Include @lombok.ToString.Include
    @NotNull EntityType entityType;

    @Nullable String customName;

    @lombok.EqualsAndHashCode.Include @lombok.ToString.Include
    double maxHealth, damage;

    @lombok.NonFinal @Nullable LivingEntity entity;
    @lombok.NonFinal Map<EquipmentSlot, ItemStack> equipment;

    @Override
    public @NotNull Optional<LivingEntity> getEntity() {
        return Optional.ofNullable(entity);
    }

    @Override
    public void delete() {
        if (entity == null) {
            return;
        }

        entity.remove();
    }

    @Override
    public void spawn() {
        if (entity != null) {
            return;
        }

        entity = (LivingEntity) location.getWorld().spawnEntity(location, entityType);

        if (customName != null) {
            entity.setCustomNameVisible(true);
            entity.customName(Component.text(customName));
        }

        entity.setRemoveWhenFarAway(false);
        entity.setAI(false);
        entity.setCanPickupItems(false);

        setHealth0(maxHealth); // todo : add / remove attributes options
    }

    private void setHealth0(final double health) {
        if (entity == null) {
            return;
        }

        val attribute = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (attribute != null) attribute.setBaseValue(health);

        entity.setHealth(health);
    }

    @Override
    public boolean setEquipment(@NonNull Map<EquipmentSlot, ItemStack> equip) {
        return false;
    }

}
