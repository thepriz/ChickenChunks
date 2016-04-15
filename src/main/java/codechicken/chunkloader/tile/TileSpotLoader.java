package codechicken.chunkloader.tile;

import codechicken.chunkloader.api.ChunkLoaderShape;
import codechicken.chunkloader.network.ChunkLoaderSPH;
import codechicken.lib.packet.PacketCustom;
import codechicken.lib.vec.BlockCoord;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class TileSpotLoader extends TileChunkLoaderBase {
    public static void handleDescriptionPacket(PacketCustom packet, World world) {
        TileEntity tile = world.getTileEntity(new BlockPos(packet.readInt(), packet.readInt(), packet.readInt()));
        if (tile instanceof TileSpotLoader) {
            TileSpotLoader ctile = (TileSpotLoader) tile;
            ctile.active = packet.readBoolean();
            if (packet.readBoolean()) {
                ctile.owner = packet.readString();
            }
        }
    }

    public Packet getDescriptionPacket() {
        PacketCustom packet = new PacketCustom(ChunkLoaderSPH.channel, 11);
        packet.writeCoord(new BlockCoord(getPos()));
        packet.writeBoolean(active);
        packet.writeBoolean(owner != null);
        if (owner != null) {
            packet.writeString(owner);
        }
        return packet.toPacket();
    }

    @Override
    public World getWorld() {
        return worldObj;
    }

    @Override
    public Collection<ChunkCoordIntPair> getChunks() {
        return Collections.singletonList(getChunkPosition());
    }

    public static HashSet<ChunkCoordIntPair> getContainedChunks(ChunkLoaderShape shape, int xCoord, int zCoord, int radius) {
        return shape.getLoadedChunks(xCoord >> 4, zCoord >> 4, radius - 1);
    }
}