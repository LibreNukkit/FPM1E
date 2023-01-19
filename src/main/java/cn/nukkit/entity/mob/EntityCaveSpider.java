/*
 * Decompiled with CFR 0.152.
 */
package cn.nukkit.entity.mob;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityArthropod;
import cn.nukkit.entity.mob.EntityWalkingMob;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.Utils;
import java.util.ArrayList;
import java.util.HashMap;

public class EntityCaveSpider
extends EntityWalkingMob
implements EntityArthropod {
    public static final int NETWORK_ID = 40;

    public EntityCaveSpider(FullChunk fullChunk, CompoundTag compoundTag) {
        super(fullChunk, compoundTag);
    }

    @Override
    public int getNetworkId() {
        return 40;
    }

    @Override
    public float getWidth() {
        return 0.7f;
    }

    @Override
    public float getHeight() {
        return 0.5f;
    }

    @Override
    public double getSpeed() {
        return 1.3;
    }

    @Override
    public void initEntity() {
        this.setMaxHealth(12);
        super.initEntity();
        this.setDamage(new int[]{0, 2, 3, 3});
    }

    @Override
    public void attackEntity(Entity entity) {
        if (this.attackDelay > 23 && this.distanceSquared(entity) < 1.32) {
            this.attackDelay = 0;
            HashMap<EntityDamageEvent.DamageModifier, Float> hashMap = new HashMap<>();
            hashMap.put(EntityDamageEvent.DamageModifier.BASE, (float) this.getDamage());
            if (entity instanceof Player) {
                float f2 = 0.0f;
                for (Item item : ((Player)entity).getInventory().getArmorContents()) {
                    f2 += this.getArmorPoints(item.getId());
                }
                hashMap.put(EntityDamageEvent.DamageModifier.ARMOR, (float) ((double) hashMap.getOrDefault(EntityDamageEvent.DamageModifier.ARMOR, 0.0f).floatValue() - Math.floor((double) (hashMap.getOrDefault(EntityDamageEvent.DamageModifier.BASE, 1.0f).floatValue() * f2) * 0.04)));
                EntityDamageByEntityEvent entityDamageByEntityEvent = new EntityDamageByEntityEvent(this, entity, EntityDamageEvent.DamageCause.ENTITY_ATTACK, hashMap);
                if (entity.attack(entityDamageByEntityEvent) && !entityDamageByEntityEvent.isCancelled() && this.server.getDifficulty() > 0) {
                    entity.addEffect(Effect.getEffect(19).setDuration(this.server.getDifficulty() > 1 ? 300 : 140));
                }
            }
        }
    }

    @Override
    public Item[] getDrops() {
        ArrayList<Item> arrayList = new ArrayList<>();
        arrayList.add(Item.get(287, 0, Utils.rand(0, 2)));
        for (int k = 0; k < (Utils.rand(0, 2) == 0 ? 1 : 0); ++k) {
            arrayList.add(Item.get(375, 0, 1));
        }
        return arrayList.toArray(new Item[0]);
    }

    @Override
    public int getKillExperience() {
        return 5;
    }

    @Override
    public String getName() {
        return this.hasCustomName() ? this.getNameTag() : "Cave Spider";
    }
}

