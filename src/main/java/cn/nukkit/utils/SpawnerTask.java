/*
 * Decompiled with CFR 0.152.
 */
package cn.nukkit.utils;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.entity.BaseEntity;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.entity.CreatureSpawnEvent;
import cn.nukkit.level.GameRule;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.level.format.generic.BaseFullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.EntitySpawner;
import cn.nukkit.utils.Utils;
import cn.nukkit.utils.spawners.ChickenSpawner;
import cn.nukkit.utils.spawners.CodSpawner;
import cn.nukkit.utils.spawners.CowSpawner;
import cn.nukkit.utils.spawners.CreeperSpawner;
import cn.nukkit.utils.spawners.DolphinSpawner;
import cn.nukkit.utils.spawners.DonkeySpawner;
import cn.nukkit.utils.spawners.DrownedSpawner;
import cn.nukkit.utils.spawners.EndermanSpawner;
import cn.nukkit.utils.spawners.FoxSpawner;
import cn.nukkit.utils.spawners.GhastSpawner;
import cn.nukkit.utils.spawners.HorseSpawner;
import cn.nukkit.utils.spawners.HuskSpawner;
import cn.nukkit.utils.spawners.LlamaSpawner;
import cn.nukkit.utils.spawners.MagmaCubeSpawner;
import cn.nukkit.utils.spawners.MooshroomSpawner;
import cn.nukkit.utils.spawners.OcelotSpawner;
import cn.nukkit.utils.spawners.PandaSpawner;
import cn.nukkit.utils.spawners.ParrotSpawner;
import cn.nukkit.utils.spawners.PhantomSpawner;
import cn.nukkit.utils.spawners.PigSpawner;
import cn.nukkit.utils.spawners.PiglinSpawner;
import cn.nukkit.utils.spawners.PolarBearSpawner;
import cn.nukkit.utils.spawners.PufferfishSpawner;
import cn.nukkit.utils.spawners.RabbitSpawner;
import cn.nukkit.utils.spawners.SalmonSpawner;
import cn.nukkit.utils.spawners.SheepSpawner;
import cn.nukkit.utils.spawners.SkeletonSpawner;
import cn.nukkit.utils.spawners.SlimeSpawner;
import cn.nukkit.utils.spawners.SpiderSpawner;
import cn.nukkit.utils.spawners.SquidSpawner;
import cn.nukkit.utils.spawners.StraySpawner;
import cn.nukkit.utils.spawners.StriderSpawner;
import cn.nukkit.utils.spawners.TropicalFishSpawner;
import cn.nukkit.utils.spawners.TurtleSpawner;
import cn.nukkit.utils.spawners.WitchSpawner;
import cn.nukkit.utils.spawners.WitherSkeletonSpawner;
import cn.nukkit.utils.spawners.WolfSpawner;
import cn.nukkit.utils.spawners.ZombiePigmanSpawner;
import cn.nukkit.utils.spawners.ZombieSpawner;
import lombok.var;

import java.util.HashMap;
import java.util.Map;

public class SpawnerTask
implements Runnable {
    private final Map<Class<?>, EntitySpawner> c = new HashMap<>();
    private final Map<Class<?>, EntitySpawner> b = new HashMap<>();
    private boolean a;

    public SpawnerTask() {
        this.registerAnimalSpawner(ChickenSpawner.class);
        this.registerAnimalSpawner(CowSpawner.class);
        this.registerAnimalSpawner(DolphinSpawner.class);
        this.registerAnimalSpawner(DonkeySpawner.class);
        this.registerAnimalSpawner(HorseSpawner.class);
        this.registerAnimalSpawner(MooshroomSpawner.class);
        this.registerAnimalSpawner(OcelotSpawner.class);
        this.registerAnimalSpawner(ParrotSpawner.class);
        this.registerAnimalSpawner(PigSpawner.class);
        this.registerAnimalSpawner(PolarBearSpawner.class);
        this.registerAnimalSpawner(PufferfishSpawner.class);
        this.registerAnimalSpawner(RabbitSpawner.class);
        this.registerAnimalSpawner(SalmonSpawner.class);
        this.registerAnimalSpawner(CodSpawner.class);
        this.registerAnimalSpawner(SheepSpawner.class);
        this.registerAnimalSpawner(SquidSpawner.class);
        this.registerAnimalSpawner(TropicalFishSpawner.class);
        this.registerAnimalSpawner(TurtleSpawner.class);
        this.registerAnimalSpawner(WolfSpawner.class);
        this.registerAnimalSpawner(PandaSpawner.class);
        this.registerAnimalSpawner(FoxSpawner.class);
        this.registerAnimalSpawner(LlamaSpawner.class);
        this.registerMobSpawner(CreeperSpawner.class);
        this.registerMobSpawner(EndermanSpawner.class);
        this.registerMobSpawner(GhastSpawner.class);
        this.registerMobSpawner(HuskSpawner.class);
        this.registerMobSpawner(MagmaCubeSpawner.class);
        this.registerMobSpawner(SkeletonSpawner.class);
        this.registerMobSpawner(SlimeSpawner.class);
        this.registerMobSpawner(SpiderSpawner.class);
        this.registerMobSpawner(StraySpawner.class);
        this.registerMobSpawner(ZombieSpawner.class);
        this.registerMobSpawner(ZombiePigmanSpawner.class);
        this.registerMobSpawner(WitchSpawner.class);
        this.registerMobSpawner(WitherSkeletonSpawner.class);
        this.registerMobSpawner(DrownedSpawner.class);
        this.registerMobSpawner(PhantomSpawner.class);
        this.registerMobSpawner(PiglinSpawner.class);
        this.registerMobSpawner(StriderSpawner.class);
    }

    public boolean registerAnimalSpawner(Class<?> clazz) {
        if (this.c.containsKey(clazz)) {
            return false;
        }
        try {
            EntitySpawner entitySpawner = (EntitySpawner)clazz.getConstructor(SpawnerTask.class).newInstance(this);
            this.c.put(clazz, entitySpawner);
        }
        catch (Exception exception) {
            return false;
        }
        return true;
    }

    public EntitySpawner getAnimalSpawner(Class<?> clazz) {
        return this.c.get(clazz);
    }

    public boolean unregisterAnimalSpawner(Class<?> clazz) {
        return this.c.remove(clazz) != null;
    }

    public boolean registerMobSpawner(Class<?> clazz) {
        if (this.b.containsKey(clazz)) {
            return false;
        }
        try {
            EntitySpawner entitySpawner = (EntitySpawner)clazz.getConstructor(SpawnerTask.class).newInstance(this);
            this.b.put(clazz, entitySpawner);
        }
        catch (Exception exception) {
            return false;
        }
        return true;
    }

    public EntitySpawner getMobSpawner(Class<?> clazz) {
        return this.b.get(clazz);
    }

    public boolean unregisterMobSpawner(Class<?> clazz) {
        return this.b.remove(clazz) != null;
    }

    @Override
    public void run() {
        block3: {
            block4: {
                if (Server.getInstance().getOnlinePlayersCount() == 0) break block3;
                if (!this.a) break block4;
                this.a = false;
                if (!Server.getInstance().spawnMonsters) break block3;
                for (EntitySpawner entitySpawner : this.b.values()) {
                    entitySpawner.spawn();
                }
                break block3;
            }
            this.a = true;
            if (Server.getInstance().spawnAnimals) {
                for (EntitySpawner entitySpawner : this.c.values()) {
                    entitySpawner.spawn();
                }
            }
        }
    }

    static boolean a(Level level, int n, Player player) {
        if (n == 58 && (player.getTimeSinceRest() < 72000 || player.isSleeping() || player.isSpectator() || !level.getGameRules().getBoolean(GameRule.DO_INSOMNIA))) {
            return false;
        }
        int n2 = SpawnerTask.a(n, level.getDimension() == 1, level.getDimension() == 2);
        if (n2 == 0) {
            return false;
        }
        int n3 = 0;
        for (Entity entity : level.entities.values()) {
            if (!entity.isAlive() || entity.getNetworkId() != n) continue;
            Vector3 vector3 = new Vector3(player.x, entity.y, player.z);
            if (!(vector3.distanceSquared(entity) < 10000.0) || ++n3 <= n2) continue;
            return false;
        }
        return n3 < n2;
    }

    public BaseEntity createEntity(Object object, Position position) {
        BaseEntity baseEntity = (BaseEntity)Entity.createEntity((String)object, position, new Object[0]);
        if (baseEntity != null) {
            if (!baseEntity.isInsideOfSolid()) {
                CreatureSpawnEvent creatureSpawnEvent = new CreatureSpawnEvent(baseEntity.getNetworkId(), position, baseEntity.namedTag, CreatureSpawnEvent.SpawnReason.NATURAL);
                Server.getInstance().getPluginManager().callEvent(creatureSpawnEvent);
                if (!creatureSpawnEvent.isCancelled()) {
                    baseEntity.spawnToAll();
                } else {
                    baseEntity.close();
                    baseEntity = null;
                }
            } else {
                baseEntity.close();
                baseEntity = null;
            }
        }
        return baseEntity;
    }

    static int a(int n, int n2, int n3) {
        int n4 = Utils.rand((n >> 1) * -1, n >> 1);
        if (n4 >= 0) {
            if (n < n2) {
                n4 = n2;
                n4 += Utils.rand((n3 >> 1) * -1, n3 >> 1);
            }
        } else if (n > n2) {
            n4 = -n2;
            n4 += Utils.rand((n3 >> 1) * -1, n3 >> 1);
        }
        return n4;
    }

    /*
     * Unable to fully structure code
     */
    static int a(Level level, Position pos) {
        int x = (int) pos.x;
        int y = (int) pos.y;
        int z = (int) pos.z;

        BaseFullChunk chunk = level.getChunk(x >> 4, z >> 4, true);
        if (level.getBlockIdAt(chunk, x, y, z) == Block.AIR) {
            while (true) {
                y--;
                if (y > 255) {
                    y = 256;
                    break;
                }
                if (y < 1) {
                    y = 0;
                    break;
                }
                if (level.getBlockIdAt(chunk, x, y, z) != Block.AIR) {
                    int checkNeedDegree = 3;
                    int checkY = y;
                    while (true) {
                        checkY++;
                        checkNeedDegree--;
                        if (checkY > 255 || checkY < 1 || level.getBlockIdAt(chunk, x, checkY, z) != Block.AIR) {
                            break;
                        }
                        if (checkNeedDegree <= 0) {
                            return y;
                        }
                    }
                }
            }
        } else {
            while (true) {
                y++;
                if (y > 255) {
                    y = 256;
                    break;
                }
                if (y < 1) {
                    y = 0;
                    break;
                }
                if (level.getBlockIdAt(chunk, x, y, z) != Block.AIR) {
                    int checkNeedDegree = 3;
                    int checkY = y;
                    while (true) {
                        checkY--;
                        checkNeedDegree--;
                        if (checkY > 255 || checkY < 1 || level.getBlockIdAt(chunk, x, checkY, z) != Block.AIR) {
                            break;
                        }
                        if (checkNeedDegree <= 0) {
                            return y;
                        }
                    }
                }
            }
        }
        return y;
    }

    private static int a(int n, boolean bl, boolean bl2) {
        switch (n) {
            case 36: 
            case 123: 
            case 124: {
                return bl ? 4 : 0;
            }
            case 41: 
            case 42: 
            case 43: 
            case 48: 
            case 125: {
                return bl ? 2 : 0;
            }
            case 38: {
                return bl2 ? 10 : 2;
            }
            case 109: 
            case 112: {
                return bl2 || bl ? 0 : 4;
            }
            case 45: {
                return bl2 || bl ? 0 : 1;
            }
            case 58: {
                int n2 = Server.getInstance().getDifficulty();
                return bl2 || bl ? 0 : (n2 == 1 ? 2 : (n2 == 2 ? 3 : 4));
            }
        }
        return bl2 || bl ? 0 : 2;
    }

    private static Exception a(Exception exception) {
        return exception;
    }
}

