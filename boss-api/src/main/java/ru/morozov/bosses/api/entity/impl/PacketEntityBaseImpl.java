package ru.morozov.bosses.api.entity.impl;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import ru.morozov.bosses.api.entity.interfaces.PacketEntity;
import ru.morozov.bosses.api.utils.protocol.ProtocolLibUtil;

/**
 * @author morozov
 */
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class PacketEntityBaseImpl implements PacketEntity {

    @EqualsAndHashCode.Include
    @NonNull Location location;

    int entityId;

    @NonNull UUID entityUUID;

    @NonNull WrappedDataWatcher dataWatcher;

    @NonNull Set<Player> players;

    protected PacketEntityBaseImpl(final @NonNull Location location) {
        this.location = location;

        this.entityId = ProtocolLibUtil.getEntityId();
        this.entityUUID = UUID.randomUUID();
        this.entityType = getEntityType();

        this.players = new java.util.HashSet<>();
        this.dataWatcher = new WrappedDataWatcher();
        // https://wiki.vg/index.php?title=Protocol&oldid=18242#Spawn_Player
       /* dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(0,
                WrappedDataWatcher.Registry.get(Byte.class)), (byte) 0); */

        registerWatcher();
    }

    protected void registerWatcher() {
        // Для других классов (например, в ArmorStand нужно
        // будет переопределить этот метод)
    }

    @NonNull EntityType entityType;

    public abstract @NonNull EntityType getEntityType();

    @Override
    public void teleport(@NonNull Location location) {
        this.location = location;

        val packet = new PacketContainer(PacketType.Play.Server.ENTITY_TELEPORT);
        packet.getIntegers().write(0, entityId);

        //location
        packet.getDoubles().write(0, location.getX());
        packet.getDoubles().write(1, location.getY());
        packet.getDoubles().write(2, location.getZ());

        //on ground
        packet.getBooleans().write(0, false);

        ProtocolLibUtil.PROTOCOL_MANAGER.broadcastServerPacket(packet);
    }

    @Nullable
    String customName;

    @Override
    public void setCustomName(@Nullable String name) {
        if (customName != null && customName.equals(name))
            return;

        customName = name;
        sendMetadata();
    }

    boolean customNameVisible;

    @Override
    public boolean isCustomNameVisible() {
        return customNameVisible;
    }

    @Override
    public void setCustomNameVisible(boolean visible) {
        if (customNameVisible == visible)
            return;

        customNameVisible = visible;
        sendMetadata();
    }

    @Override
    public void spawn(final @NonNull Player player) {
        // Игрок уже видит энтити, пропускаем спавн
        // а также, лишнюю отправку пакетов

        // (?) Может быть сделать выполнение метода PacketEntity#remove
        if (players.contains(player)) return;

        val packet = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY);

        //base
        packet.getIntegers().write(0, entityId);
        packet.getUUIDs().write(0, entityUUID);

        val key = new net.minecraft.resources.ResourceLocation(entityType.getKey().toString());
        val registry = net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE;

        packet.getIntegers().write(1, registry.getId(registry.get(key)));

        //location
        packet.getDoubles().write(0, location.getX());
        packet.getDoubles().write(1, location.getY());
        packet.getDoubles().write(2, location.getZ());

        val yaw = (byte) (location.getYaw() * 256.0F / 360.0F);
        val pitch = (byte) (location.getPitch() * 256.0F / 360.0F);

        packet.getBytes().write(0, yaw);
        packet.getBytes().write(1, pitch);
        packet.getBytes().write(2, yaw);

        packet.getIntegers().write(2, 0); //velocity x
        packet.getIntegers().write(3, 0); //velocity y
        packet.getIntegers().write(4, 0); //velocity z

        ProtocolLibUtil.PROTOCOL_MANAGER.sendServerPacket(player, packet);
        addViewer(player);
    }

    @Override
    public void spawn() {
        Bukkit.getOnlinePlayers().forEach(this::spawn);
    }

    @Override
    public void remove() {
        val packet = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
        packet.getIntegerArrays().write(0, new int[]{entityId});

        ProtocolLibUtil.PROTOCOL_MANAGER.broadcastServerPacket(packet);
    }

    @Override
    public void remove(final @NonNull Player player) {
        val packet = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
        packet.getIntegerArrays().write(0, new int[]{entityId});

        ProtocolLibUtil.PROTOCOL_MANAGER.sendServerPacket(player, packet);
    }

    protected void sendMetadata() {
        val packet = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
        packet.getIntegers().write(0, entityId);

        val objects = dataWatcher.getWatchableObjects();
        packet.getWatchableCollectionModifier().write(0, objects);

        ProtocolLibUtil.PROTOCOL_MANAGER.broadcastServerPacket(packet);
    }

    @Override
    public @NotNull java.util.Set<Player> getViewers() {
        return java.util.Collections.unmodifiableSet(players);
    }

    @Override
    public void addViewers(@NonNull java.util.Collection<Player> players) {
        players.forEach(this::addViewer);
    }

    @Override
    public void addViewer(@NonNull Player player) {
        players.add(player);
    }

    @Override
    public void removeViewer(@NonNull Player player) {
        players.remove(player);
    }
}
