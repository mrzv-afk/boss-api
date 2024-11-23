package ru.morozov.bosses.api.logic;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import ru.morozov.bosses.api.BossApi;

/**
 * @author morozov
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class EventListener implements Listener {

    BossApi bossApi;

}
