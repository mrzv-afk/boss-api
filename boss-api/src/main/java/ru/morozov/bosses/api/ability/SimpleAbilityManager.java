package ru.morozov.bosses.api.ability;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;
import ru.morozov.bosses.api.ability.model.Ability;
import ru.morozov.bosses.api.utils.Manager;

import java.util.Set;

/**
 * @author morozov
 */
public final class SimpleAbilityManager implements Manager<Ability> {
    @Override
    public @NotNull @UnmodifiableView Set<Ability> getEntities() {
        return Set.of();
    }

    @Override
    public void registerEntity(@NonNull Ability entity) {

    }

    @Override
    public void removeEntity(@NonNull Ability entity) {

    }

    @Override
    public void removeAll() {

    }
}
