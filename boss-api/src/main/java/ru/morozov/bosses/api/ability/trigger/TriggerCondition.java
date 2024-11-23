package ru.morozov.bosses.api.ability.trigger;

import lombok.NonNull;
import org.bukkit.entity.LivingEntity;

/**
 * @author morozov
 */
@FunctionalInterface
public interface TriggerCondition {

    boolean checkCondition(final @NonNull LivingEntity entity);

}
