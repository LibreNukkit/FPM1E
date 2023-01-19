/*
 * Decompiled with CFR 0.152.
 */
package cn.nukkit.entity.mob;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityCreature;
import cn.nukkit.entity.EntitySmite;
import cn.nukkit.entity.mob.EntityFlyingMob;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.Utils;
import java.util.HashMap;

public class EntityPhantom
extends EntityFlyingMob
implements EntitySmite {
    public static final int NETWORK_ID = 58;

    public EntityPhantom(FullChunk fullChunk, CompoundTag compoundTag) {
        super(fullChunk, compoundTag);
    }

    @Override
    public int getNetworkId() {
        return 58;
    }

    @Override
    public float getWidth() {
        return 0.9f;
    }

    @Override
    public float getHeight() {
        return 0.5f;
    }

    @Override
    public double getSpeed() {
        return 1.8;
    }

    @Override
    public void initEntity() {
        this.setMaxHealth(20);
        super.initEntity();
        this.setDamage(new int[]{0, 4, 6, 9});
    }

    @Override
    public boolean targetOption(EntityCreature entityCreature, double d2) {
        if (entityCreature instanceof Player) {
            Player player = (Player)entityCreature;
            return player.spawned && player.isAlive() && !player.closed && (player.isSurvival() || player.isAdventure()) && d2 <= 256.0;
        }
        return entityCreature.isAlive() && !entityCreature.closed && d2 <= 256.0;
    }

    @Override
    public void attackEntity(Entity entity) {
        if (this.attackDelay > 23 && entity.distanceSquared(this) <= 1.0) {
            this.attackDelay = 0;
            HashMap<EntityDamageEvent.DamageModifier, Float> hashMap = new HashMap<>();
            hashMap.put(EntityDamageEvent.DamageModifier.BASE, (float) this.getDamage());
            if (entity instanceof Player) {
                float f2 = 0.0f;
                for (Item item : ((Player)entity).getInventory().getArmorContents()) {
                    f2 += this.getArmorPoints(item.getId());
                }
                hashMap.put(EntityDamageEvent.DamageModifier.ARMOR, (float) ((double) hashMap.getOrDefault(EntityDamageEvent.DamageModifier.ARMOR, 0.0f).floatValue() - Math.floor((double) (hashMap.getOrDefault(EntityDamageEvent.DamageModifier.BASE, 1.0f).floatValue() * f2) * 0.04)));
            }
            entity.attack(new EntityDamageByEntityEvent(this, entity, EntityDamageEvent.DamageCause.ENTITY_ATTACK, hashMap));
        }
    }

    @Override
    public Item[] getDrops() {
        return new Item[]{Item.get(470, 0, Utils.rand(0, 1))};
    }

    @Override
    public int getKillExperience() {
        return 5;
    }

    @Override
    public boolean entityBaseTick(int n) {
        if (this.getServer().getDifficulty() == 0) {
            this.close();
            return true;
        }
        boolean bl = super.entityBaseTick(n);
        if (!this.closed && this.level.shouldMobBurn(this)) {
            this.setOnFire(100);
        }
        return bl;
    }

    @Override
    public boolean dropsOnNaturalDeath() {
        return false;
    }
}

