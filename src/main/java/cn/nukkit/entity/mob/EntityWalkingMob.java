/*
 * Decompiled with CFR 0.152.
 */
package cn.nukkit.entity.mob;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityWalking;
import cn.nukkit.entity.mob.EntityMob;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.Utils;

public abstract class EntityWalkingMob
extends EntityWalking
implements EntityMob {
    private int[] v;
    private int[] u;
    private boolean t = true;
    public long isAngryTo = -1L;

    public EntityWalkingMob(FullChunk fullChunk, CompoundTag compoundTag) {
        super(fullChunk, compoundTag);
    }

    @Override
    public void setTarget(Entity entity) {
        this.setTarget(entity, true);
    }

    public void setTarget(Entity entity, boolean bl) {
        super.setTarget(entity);
        this.t = bl;
    }

    @Override
    public int getDamage() {
        return this.getDamage(null);
    }

    @Override
    public int getDamage(Integer n) {
        return Utils.rand(this.getMinDamage(n), this.getMaxDamage(n));
    }

    @Override
    public int getMinDamage() {
        return this.getMinDamage(null);
    }

    @Override
    public int getMinDamage(Integer n) {
        if (n == null || n > 3 || n < 0) {
            n = Server.getInstance().getDifficulty();
        }
        return this.v[n];
    }

    @Override
    public int getMaxDamage() {
        return this.getMaxDamage(null);
    }

    @Override
    public int getMaxDamage(Integer n) {
        if (n == null || n > 3 || n < 0) {
            n = Server.getInstance().getDifficulty();
        }
        return this.u[n];
    }

    @Override
    public void setDamage(int n) {
        this.setDamage(n, Server.getInstance().getDifficulty());
    }

    @Override
    public void setDamage(int n, int n2) {
        block0: {
            if (n2 < 1 || n2 > 3) break block0;
            this.v[n2] = n;
            this.u[n2] = n;
        }
    }

    @Override
    public void setDamage(int[] nArray) {
        if (nArray.length < 4) {
            throw new IllegalArgumentException("Invalid damage array length");
        }
        if (this.v == null || this.v.length < 4) {
            this.v = Utils.emptyDamageArray;
        }
        if (this.u == null || this.u.length < 4) {
            this.u = Utils.emptyDamageArray;
        }
        for (int k = 0; k < 4; ++k) {
            this.v[k] = nArray[k];
            this.u[k] = nArray[k];
        }
    }

    @Override
    public void setMinDamage(int[] nArray) {
        if (nArray.length < 4) {
            return;
        }
        for (int k = 0; k < 4; ++k) {
            this.setMinDamage(Math.min(nArray[k], this.getMaxDamage(k)), k);
        }
    }

    @Override
    public void setMinDamage(int n) {
        this.setMinDamage(n, Server.getInstance().getDifficulty());
    }

    @Override
    public void setMinDamage(int n, int n2) {
        block0: {
            if (n2 < 1 || n2 > 3) break block0;
            this.v[n2] = Math.min(n, this.getMaxDamage(n2));
        }
    }

    @Override
    public void setMaxDamage(int[] nArray) {
        if (nArray.length < 4) {
            return;
        }
        for (int k = 0; k < 4; ++k) {
            this.setMaxDamage(Math.max(nArray[k], this.getMinDamage(k)), k);
        }
    }

    @Override
    public void setMaxDamage(int n) {
        this.setMinDamage(n, Server.getInstance().getDifficulty());
    }

    @Override
    public void setMaxDamage(int n, int n2) {
        block0: {
            if (n2 < 1 || n2 > 3) break block0;
            this.u[n2] = Math.max(n, this.getMinDamage(n2));
        }
    }

    @Override
    public boolean onUpdate(int n) {
        block5: {
            if (this.closed) {
                return false;
            }
            if (this.server.getDifficulty() < 1) {
                this.close();
                return false;
            }
            if (!this.isAlive()) {
                if (++this.deadTicks >= 23) {
                    this.close();
                    return false;
                }
                return true;
            }
            int n2 = n - this.lastUpdate;
            this.lastUpdate = n;
            this.entityBaseTick(n2);
            Vector3 vector3 = this.updateMove(n2);
            if (!this.getServer().getMobAiEnabled() || !(vector3 instanceof Entity) || this.isFriendly() && vector3 instanceof Player && ((Entity)vector3).getId() != this.isAngryTo) break block5;
            Entity entity = (Entity)vector3;
            if (!entity.closed && (vector3 != this.followTarget || this.t)) {
                this.attackEntity(entity);
            }
        }
        return true;
    }

    private static IllegalArgumentException a(IllegalArgumentException illegalArgumentException) {
        return illegalArgumentException;
    }
}

