package codechicken.chunkloader.api;

import codechicken.chunkloader.world.ChunkLoaderHandler;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Set;

/**
 * Handler for interacting with the ChickenChunks loading backend.
 * IChunkLoader implementations are expected to be TileEntities at this moment.
 * This Capability only exists on the {@link World#field_234918_g_} and handles all dimensions.
 * Due to complexity limitations, its not viable to move this to a per-world capability at the current moment.
 * <p>
 * Created by covers1624 on 5/4/20.
 */
public interface IChunkLoaderHandler {

    void addChunkLoader(IChunkLoader loader);

    void removeChunkLoader(IChunkLoader loader);

    boolean canLoadChunks(IChunkLoader loader, Set<ChunkPos> newChunks);

    void updateLoader(IChunkLoader loader);

    static IChunkLoaderHandler getCapability(IWorld world) {
        if (!(world instanceof ServerWorld)) {
            throw new IllegalArgumentException("ServerWorld required.");
        }
        ServerWorld overWorld = (ServerWorld) world;
        if (((ServerWorld) world).func_234923_W_() != World.field_234918_g_) {
            overWorld = overWorld.getServer().getWorld(World.field_234918_g_);
        }
        return overWorld.getCapability(ChunkLoaderHandler.HANDLER_CAPABILITY).orElse(null);
    }
}
