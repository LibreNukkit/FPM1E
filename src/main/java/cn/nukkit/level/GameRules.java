/*
 * Decompiled with CFR 0.152.
 */
package cn.nukkit.level;

import cn.nukkit.NOBF;
import cn.nukkit.Server;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.utils.BinaryStream;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class GameRules {
    private final EnumMap<GameRule, Value> a = new EnumMap<>(GameRule.class);
    private boolean b;

    public static GameRules getDefault() {
        GameRules gameRules = new GameRules();
        gameRules.a.put(GameRule.COMMAND_BLOCKS_ENABLED, new Value<>(Type.BOOLEAN, false, 291));
        gameRules.a.put(GameRule.COMMAND_BLOCK_OUTPUT, new Value<>(Type.BOOLEAN, true));
        gameRules.a.put(GameRule.DO_DAYLIGHT_CYCLE, new Value<>(Type.BOOLEAN, true));
        gameRules.a.put(GameRule.DO_ENTITY_DROPS, new Value<>(Type.BOOLEAN, true));
        gameRules.a.put(GameRule.DO_FIRE_TICK, new Value<>(Type.BOOLEAN, true));
        gameRules.a.put(GameRule.DO_INSOMNIA, new Value<>(Type.BOOLEAN, false, 281));
        gameRules.a.put(GameRule.DO_IMMEDIATE_RESPAWN, new Value<>(Type.BOOLEAN, false, 332));
        gameRules.a.put(GameRule.DO_MOB_LOOT, new Value<>(Type.BOOLEAN, true));
        gameRules.a.put(GameRule.DO_MOB_SPAWNING, new Value<>(Type.BOOLEAN, true));
        gameRules.a.put(GameRule.DO_TILE_DROPS, new Value<>(Type.BOOLEAN, true));
        gameRules.a.put(GameRule.DO_WEATHER_CYCLE, new Value<>(Type.BOOLEAN, true));
        gameRules.a.put(GameRule.DROWNING_DAMAGE, new Value<>(Type.BOOLEAN, true));
        gameRules.a.put(GameRule.FALL_DAMAGE, new Value<>(Type.BOOLEAN, true));
        gameRules.a.put(GameRule.FIRE_DAMAGE, new Value<>(Type.BOOLEAN, true));
        gameRules.a.put(GameRule.FREEZE_DAMAGE, new Value<>(Type.BOOLEAN, true, 440));
        gameRules.a.put(GameRule.FUNCTION_COMMAND_LIMIT, new Value<>(Type.INTEGER, 10000, 332));
        gameRules.a.put(GameRule.KEEP_INVENTORY, new Value<>(Type.BOOLEAN, false));
        gameRules.a.put(GameRule.MAX_COMMAND_CHAIN_LENGTH, new Value<>(Type.INTEGER, 65536));
        gameRules.a.put(GameRule.MOB_GRIEFING, new Value<>(Type.BOOLEAN, true));
        gameRules.a.put(GameRule.NATURAL_REGENERATION, new Value<>(Type.BOOLEAN, true));
        gameRules.a.put(GameRule.PVP, new Value<>(Type.BOOLEAN, true));
        gameRules.a.put(GameRule.RANDOM_TICK_SPEED, new Value<>(Type.INTEGER, 3, 313));
        gameRules.a.put(GameRule.RESPAWN_BLOCKS_EXPLODE, new Value<>(Type.BOOLEAN, true, 475));
        gameRules.a.put(GameRule.SEND_COMMAND_FEEDBACK, new Value<>(Type.BOOLEAN, true, 361));
        gameRules.a.put(GameRule.SHOW_COORDINATES, new Value<>(Type.BOOLEAN, false));
        gameRules.a.put(GameRule.SHOW_DEATH_MESSAGES, new Value<>(Type.BOOLEAN, true, 332));
        gameRules.a.put(GameRule.SHOW_TAGS, new Value<>(Type.BOOLEAN, true, 389));
        gameRules.a.put(GameRule.SPAWN_RADIUS, new Value<>(Type.INTEGER, 5, 361));
        gameRules.a.put(GameRule.TNT_EXPLODES, new Value<>(Type.BOOLEAN, true));
        return gameRules;
    }

    public Map<GameRule, Value> getGameRules() {
        return ImmutableMap.copyOf(this.a);
    }

    public boolean isStale() {
        return this.b;
    }

    public void refresh() {
        this.b = false;
    }

    public void setGameRule(GameRule gameRule, boolean bl) {
        if (!this.a.containsKey(gameRule)) {
            throw new IllegalArgumentException("Gamerule does not exist");
        }
        this.a.get(gameRule).setValue(bl, Type.BOOLEAN);
        this.b = true;
    }

    public void setGameRule(GameRule gameRule, int n) {
        if (!this.a.containsKey(gameRule)) {
            throw new IllegalArgumentException("Gamerule does not exist");
        }
        this.a.get(gameRule).setValue(n, Type.INTEGER);
        this.b = true;
    }

    public void setGameRule(GameRule gameRule, float f2) {
        if (!this.a.containsKey(gameRule)) {
            throw new IllegalArgumentException("Gamerule does not exist");
        }
        this.a.get(gameRule).setValue(f2, Type.FLOAT);
        this.b = true;
    }

    public void setGameRules(GameRule gameRule, String string) throws IllegalArgumentException {
        Preconditions.checkNotNull(gameRule, "gameRule");
        Preconditions.checkNotNull(string, "value");
        switch (this.getGameRuleType(gameRule)) {
            case BOOLEAN: {
                if (string.equalsIgnoreCase("true")) {
                    this.setGameRule(gameRule, true);
                    break;
                }
                if (string.equalsIgnoreCase("false")) {
                    this.setGameRule(gameRule, false);
                    break;
                }
                throw new IllegalArgumentException("Was not a boolean");
            }
            case INTEGER: {
                this.setGameRule(gameRule, Integer.parseInt(string));
                break;
            }
            case FLOAT: {
                this.setGameRule(gameRule, Float.parseFloat(string));
            }
        }
    }

    public boolean getBoolean(GameRule gameRule) {
        return this.a.get(gameRule).getValueAsBoolean();
    }

    public int getInteger(GameRule gameRule) {
        Preconditions.checkNotNull(gameRule, "gameRule");
        return this.a.get(gameRule).getValueAsInteger();
    }

    public float getFloat(GameRule gameRule) {
        Preconditions.checkNotNull(gameRule, "gameRule");
        return this.a.get(gameRule).getValueAsFloat();
    }

    public String getString(GameRule gameRule) {
        Preconditions.checkNotNull(gameRule, "gameRule");
        return this.a.get(gameRule).value.toString();
    }

    public Type getGameRuleType(GameRule gameRule) {
        Preconditions.checkNotNull(gameRule, "gameRule");
        return this.a.get(gameRule).getType();
    }

    public boolean hasRule(GameRule gameRule) {
        return this.a.containsKey(gameRule);
    }

    public GameRule[] getRules() {
        return this.a.keySet().toArray(new GameRule[0]);
    }

    public CompoundTag writeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        for (Map.Entry<GameRule, Value> entry : this.a.entrySet()) {
            compoundTag.putString(entry.getKey().getName(), entry.getValue().value.toString());
        }
        return compoundTag;
    }

    public void readNBT(CompoundTag compoundTag) {
        Preconditions.checkNotNull(compoundTag);
        for (String string : compoundTag.getTags().keySet()) {
            Optional<GameRule> optional = GameRule.parseString(string);
            if (!optional.isPresent()) continue;
            this.setGameRules(optional.get(), compoundTag.getString(string));
        }
    }

    private static IllegalArgumentException a(IllegalArgumentException illegalArgumentException) {
        return illegalArgumentException;
    }

    @NOBF
    public static class Value<T> {
        private final Type type;
        private T value;
        private boolean canBeChanged;
        private int minProtocol;

        public Value(Type type, T value) {
            this.type = type;
            this.value = value;
        }

        public Value(Type type, T value, int minProtocol) {
            this.type = type;
            this.value = value;
            this.minProtocol = minProtocol;
        }

        private void setValue(T value, Type type) {
            if (this.type != type) {
                throw new UnsupportedOperationException("Rule not of type " + type.name().toLowerCase());
            }
            this.value = value;
        }

        public boolean isCanBeChanged() {
            return this.canBeChanged;
        }

        public void setCanBeChanged(boolean canBeChanged) {
            this.canBeChanged = canBeChanged;
        }

        public Type getType() {
            return this.type;
        }

        public int getMinProtocol() {
            return minProtocol;
        }

        private boolean getValueAsBoolean() {
            if (type != Type.BOOLEAN) {
                throw new UnsupportedOperationException("Rule not of type boolean");
            }
            return (Boolean) value;
        }

        private int getValueAsInteger() {
            if (type != Type.INTEGER) {
                throw new UnsupportedOperationException("Rule not of type integer");
            }
            return (Integer) value;
        }

        private float getValueAsFloat() {
            if (type != Type.FLOAT) {
                throw new UnsupportedOperationException("Rule not of type float");
            }
            return (Float) value;
        }

        public void write(BinaryStream pk) {
            Server.mvw("GameRules#write(BinaryStream)");
            write(ProtocolInfo.CURRENT_PROTOCOL, pk);
        }

        public void write(int protocol, BinaryStream pk) {
            if (protocol >= ProtocolInfo.v1_17_0) {
                pk.putBoolean(this.canBeChanged);
            }
            pk.putUnsignedVarInt(type.ordinal());
            type.write(pk, this);
        }

        private static UnsupportedOperationException a(UnsupportedOperationException unsupportedOperationException) {
            return unsupportedOperationException;
        }
    }

    public enum Type {
        UNKNOWN {
            @Override
            void write(BinaryStream pk, Value value) {
            }
        },
        BOOLEAN {
            @Override
            void write(BinaryStream pk, Value value) {
                pk.putBoolean(value.getValueAsBoolean());
            }
        },
        INTEGER {
            @Override
            void write(BinaryStream pk, Value value) {
                pk.putUnsignedVarInt(value.getValueAsInteger());
            }
        },
        FLOAT {
            @Override
            void write(BinaryStream pk, Value value) {
                pk.putLFloat(value.getValueAsFloat());
            }
        };

        abstract void write(BinaryStream pk, Value value);
    }
}

