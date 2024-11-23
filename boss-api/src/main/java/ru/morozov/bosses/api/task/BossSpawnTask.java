package ru.morozov.bosses.api.task;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import ru.morozov.bosses.api.BossApi;

/**
 * @author morozov
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class BossSpawnTask extends BukkitRunnable {

    @NotNull BossApi bossApi;

    public static @NotNull BossSpawnTask create(final @NonNull BossApi bossApi) {
        return new BossSpawnTask(bossApi);
    }

    @Override
    public void run() {

    }
}
