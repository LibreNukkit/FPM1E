/*
 * Decompiled with CFR 0.152.
 */
package cn.nukkit.inventory;

import cn.nukkit.Server;
import cn.nukkit.inventory.BrewingRecipe;
import cn.nukkit.inventory.CampfireRecipe;
import cn.nukkit.inventory.ContainerRecipe;
import cn.nukkit.inventory.CraftingRecipe;
import cn.nukkit.inventory.FurnaceRecipe;
import cn.nukkit.inventory.MultiRecipe;
import cn.nukkit.inventory.Recipe;
import cn.nukkit.inventory.ShapedRecipe;
import cn.nukkit.inventory.ShapelessRecipe;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemFirework;
import cn.nukkit.network.protocol.BatchPacket;
import cn.nukkit.network.protocol.CraftingDataPacket;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.utils.BinaryStream;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.MainLogger;
import cn.nukkit.utils.Utils;
import io.netty.util.collection.CharObjectHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.var;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CraftingManager {
    public static BatchPacket packet313;
    public static BatchPacket packet340;
    public static BatchPacket packet361;
    public static BatchPacket packet354;
    public static BatchPacket packet388;
    public static BatchPacket packet407;
    public static DataPacket packet419;
    public static DataPacket packet431;
    public static DataPacket packet440;
    public static DataPacket packet448;
    public static DataPacket packet465;
    public static DataPacket packet471;
    public static DataPacket packet486;
    public static DataPacket packet503;
    public static DataPacket packet527;
    public static DataPacket packet544;
    public static DataPacket packet554;
    public static DataPacket packet560;
    private final Collection<Recipe> b = new ArrayDeque<>();
    private final Collection<Recipe> r = new ArrayDeque<>();
    private final Collection<Recipe> q = new ArrayDeque<>();
    private final Collection<Recipe> h = new ArrayDeque<>();
    public final Collection<Recipe> recipes = new ArrayDeque<>();
    private final Map<Integer, Map<UUID, ShapedRecipe>> k = new Int2ObjectOpenHashMap<>();
    private final Map<Integer, Map<UUID, ShapedRecipe>> j = new Int2ObjectOpenHashMap<>();
    private final Map<Integer, Map<UUID, ShapedRecipe>> t = new Int2ObjectOpenHashMap<>();
    private final Map<Integer, Map<UUID, ShapedRecipe>> i = new Int2ObjectOpenHashMap<>();
    protected final Map<Integer, Map<UUID, ShapedRecipe>> shapedRecipes = new Int2ObjectOpenHashMap<>();
    private final Map<Integer, Map<UUID, ShapelessRecipe>> g = new Int2ObjectOpenHashMap<>();
    private final Map<Integer, Map<UUID, ShapelessRecipe>> o = new Int2ObjectOpenHashMap<>();
    private final Map<Integer, Map<UUID, ShapelessRecipe>> e = new Int2ObjectOpenHashMap<>();
    private final Map<Integer, Map<UUID, ShapelessRecipe>> d = new Int2ObjectOpenHashMap<>();
    protected final Map<Integer, Map<UUID, ShapelessRecipe>> shapelessRecipes = new Int2ObjectOpenHashMap<>();
    private final Map<Integer, ContainerRecipe> n = new Int2ObjectOpenHashMap<>();
    public final Map<Integer, ContainerRecipe> containerRecipes = new Int2ObjectOpenHashMap<>();
    public final Map<UUID, MultiRecipe> multiRecipes = new HashMap<>();
    private final Map<Integer, FurnaceRecipe> w = new Int2ObjectOpenHashMap<>();
    private final Map<Integer, FurnaceRecipe> s = new Int2ObjectOpenHashMap<>();
    public final Map<Integer, FurnaceRecipe> furnaceRecipes = new Int2ObjectOpenHashMap<>();
    private final Map<Integer, BrewingRecipe> m = new Int2ObjectOpenHashMap<>();
    public final Map<Integer, BrewingRecipe> brewingRecipes = new Int2ObjectOpenHashMap<>();
    public final Map<Integer, CampfireRecipe> campfireRecipes = new Int2ObjectOpenHashMap<>();
    private static int l;
    static int u;
    static final int v = 527;
    static final int a = 419;
    static final int p = 407;
    static final int c = 407;
    static final int f = 407;
    public static final Comparator<Item> recipeComparator;

    public CraftingManager() {
        this.a();
    }

    /*
     * WARNING - void declaration
     */
    private void a() {
        //TODO WTF METHOD
        this.c(527);
    }

    private void c(int n) {
        List<Item> list = Arrays.asList(Item.get(54, 0, 1), Item.get(333, 0, 1));
        list.sort(recipeComparator);
        this.registerRecipe(n, new ShapelessRecipe("craft_oak_chest_boat", 0, Item.get(638, 0, 1), list));
        List<Item> list2 = Arrays.asList(Item.get(54, 0, 1), Item.get(333, 2, 1));
        list2.sort(recipeComparator);
        this.registerRecipe(n, new ShapelessRecipe("craft_birch_chest_boat", 0, Item.get(639, 0, 1), list2));
        List<Item> list3 = Arrays.asList(Item.get(54, 0, 1), Item.get(333, 3, 1));
        list3.sort(recipeComparator);
        this.registerRecipe(n, new ShapelessRecipe("craft_jungle_chest_boat", 0, Item.get(640, 0, 1), list3));
        List<Item> list4 = Arrays.asList(Item.get(54, 0, 1), Item.get(333, 1, 1));
        list4.sort(recipeComparator);
        this.registerRecipe(n, new ShapelessRecipe("craft_spruce_chest_boat", 0, Item.get(641, 0, 1), list4));
        List<Item> list5 = Arrays.asList(Item.get(54, 0, 1), Item.get(333, 4, 1));
        list5.sort(recipeComparator);
        this.registerRecipe(n, new ShapelessRecipe("craft_acacia_chest_boat", 0, Item.get(642, 0, 1), list5));
        List<Item> list6 = Arrays.asList(Item.get(54, 0, 1), Item.get(333, 5, 1));
        list6.sort(recipeComparator);
        this.registerRecipe(n, new ShapelessRecipe("craft_dark_oak_chest_boat", 0, Item.get(643, 0, 1), list6));
        CharObjectHashMap<Item> charObjectHashMap = new CharObjectHashMap<>();
        charObjectHashMap.put(Character.valueOf('A'), Item.get(637, 0, 1));
        this.registerRecipe(n, new ShapedRecipe("craft_record_5", 0, Item.get(636), new String[]{"AAA", "AAA", "AAA"}, charObjectHashMap, new ArrayList<>()));
    }

    public void rebuildPacket() {
        packet560 = this.b(560);
        packet554 = this.b(554);
        packet544 = this.b(544);
        packet527 = this.b(527);
        packet503 = this.b(503);
        packet486 = this.b(486);
        packet471 = this.b(471);
        packet465 = this.b(465);
        packet448 = this.b(448);
        packet440 = this.b(440);
        packet431 = this.b(431);
        packet419 = this.b(419);
        packet407 = this.b(407).compress(9);
        packet388 = this.b(388).compress(9);
        packet361 = this.b(361).compress(9);
        packet354 = this.b(354).compress(9);
        packet340 = this.b(340).compress(9);
        packet313 = this.b(313).compress(9);
    }

    private CraftingDataPacket b(int n) {
        CraftingDataPacket craftingDataPacket = new CraftingDataPacket();
        craftingDataPacket.protocol = n;
        for (Recipe recipe : this.getRecipes(n)) {
            if (recipe instanceof ShapedRecipe) {
                craftingDataPacket.addShapedRecipe((ShapedRecipe)recipe);
                continue;
            }
            if (!(recipe instanceof ShapelessRecipe)) continue;
            craftingDataPacket.addShapelessRecipe((ShapelessRecipe)recipe);
        }
        for (FurnaceRecipe furnaceRecipe : this.getFurnaceRecipes(n).values()) {
            craftingDataPacket.addFurnaceRecipe(furnaceRecipe);
        }
        if (n >= 388) {
            for (BrewingRecipe brewingRecipe : this.getBrewingRecipes(n).values()) {
                craftingDataPacket.addBrewingRecipe(brewingRecipe);
            }
            for (ContainerRecipe containerRecipe : this.getContainerRecipes(n).values()) {
                craftingDataPacket.addContainerRecipe(containerRecipe);
            }
            if (n >= 407) {
                for (MultiRecipe multiRecipe : this.getMultiRecipes(n).values()) {
                    craftingDataPacket.addMultiRecipe(multiRecipe);
                }
            }
        }
        craftingDataPacket.tryEncode();
        return craftingDataPacket;
    }

    public Collection<Recipe> getRecipes() {
        Server.mvw("CraftingManager#getRecipes()");
        return this.getRecipes(ProtocolInfo.CURRENT_PROTOCOL);
    }

    public Collection<Recipe> getRecipes(int n) {
        if (n >= 524) {
            return this.recipes;
        }
        if (n >= 419) {
            return this.h;
        }
        if (n >= 354) {
            return this.q;
        }
        if (n >= 340) {
            return this.r;
        }
        return this.b;
    }

    private Collection<Recipe> a(int n) {
        if (n == 527) {
            return this.recipes;
        }
        if (n == 419) {
            return this.h;
        }
        if (n == 388) {
            return this.q;
        }
        if (n == 332) {
            return this.r;
        }
        if (n == 313) {
            return this.b;
        }
        throw new IllegalArgumentException("Invalid protocol: " + n + " Supported: 419, 388, 332, 313");
    }

    public Map<Integer, Map<UUID, ShapedRecipe>> getShapedRecipes(int n) {
        if (n >= 524) {
            return this.shapedRecipes;
        }
        if (n >= 419) {
            return this.i;
        }
        if (n >= 354) {
            return this.t;
        }
        if (n >= 340) {
            return this.j;
        }
        return this.k;
    }

    public Map<Integer, Map<UUID, ShapelessRecipe>> getShapelessRecipes(int n) {
        if (n >= 524) {
            return this.shapelessRecipes;
        }
        if (n >= 419) {
            return this.d;
        }
        if (n >= 354) {
            return this.e;
        }
        if (n >= 340) {
            return this.o;
        }
        return this.g;
    }

    public Map<Integer, FurnaceRecipe> getFurnaceRecipes() {
        Server.mvw("CraftingManager#getFurnaceRecipes()");
        return this.getFurnaceRecipes(ProtocolInfo.CURRENT_PROTOCOL);
    }

    public Map<Integer, FurnaceRecipe> getFurnaceRecipes(int n) {
        if (n >= 419) {
            return this.furnaceRecipes;
        }
        if (n >= 340) {
            return this.s;
        }
        return this.w;
    }

    public Map<Integer, ContainerRecipe> getContainerRecipes(int n) {
        if (n >= 407) {
            return this.containerRecipes;
        }
        return this.n;
    }

    public Map<Integer, BrewingRecipe> getBrewingRecipes(int n) {
        if (n >= 407) {
            return this.brewingRecipes;
        }
        return this.m;
    }

    public Map<UUID, MultiRecipe> getMultiRecipes(int n) {
        if (n >= 407) {
            return this.multiRecipes;
        }
        throw new IllegalArgumentException("Multi recipes are not supported for protocol " + n + " (< 407)");
    }

    public void registerRecipe(Recipe recipe) {
        Server.mvw("CraftingManager#registerRecipe(Recipe)");
        this.registerRecipe(527, recipe);
    }

    public void registerRecipe(int n, Recipe recipe) {
        if (recipe instanceof CraftingRecipe) {
            UUID uUID = Utils.dataToUUID(String.valueOf(++l), String.valueOf(recipe.getResult().getId()), String.valueOf(recipe.getResult().getDamage()), String.valueOf(recipe.getResult().getCount()), Arrays.toString(recipe.getResult().getCompoundTag()));
            ((CraftingRecipe)recipe).setId(uUID);
            this.a(n).add(recipe);
        }
        recipe.registerToCraftingManager(n, this);
    }

    public void registerShapedRecipe(ShapedRecipe shapedRecipe) {
        Server.mvw("CraftingManager#registerShapedRecipe(ShapedRecipe)");
        this.registerShapedRecipe(313, shapedRecipe);
        this.registerShapedRecipe(332, shapedRecipe);
        this.registerShapedRecipe(388, shapedRecipe);
        this.registerShapedRecipe(419, shapedRecipe);
        this.registerShapedRecipe(527, shapedRecipe);
    }

    public void registerShapedRecipe(int n2, ShapedRecipe shapedRecipe) {
        Map map;
        int n3 = CraftingManager.b(shapedRecipe.getResult());
        switch (n2) {
            case 313: {
                map = this.k.computeIfAbsent(n3, n -> new HashMap<>());
                break;
            }
            case 332: {
                map = this.j.computeIfAbsent(n3, n -> new HashMap<>());
                break;
            }
            case 388: {
                map = this.t.computeIfAbsent(n3, n -> new HashMap<>());
                break;
            }
            case 419: {
                map = this.i.computeIfAbsent(n3, n -> new HashMap<>());
                break;
            }
            case 527: {
                map = this.shapedRecipes.computeIfAbsent(n3, n -> new HashMap<>());
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid protocol: " + n2 + " Supported: 527, 419, 388, 332, 313");
            }
        }
        map.put(CraftingManager.a(new LinkedList<>(shapedRecipe.getIngredientsAggregate())), shapedRecipe);
    }

    public void registerShapelessRecipe(ShapelessRecipe shapelessRecipe) {
        Server.mvw("CraftingManager#registerShapelessRecipe(ShapelessRecipe)");
        this.registerShapelessRecipe(313, shapelessRecipe);
        this.registerShapelessRecipe(332, shapelessRecipe);
        this.registerShapelessRecipe(388, shapelessRecipe);
        this.registerShapelessRecipe(419, shapelessRecipe);
        this.registerShapelessRecipe(527, shapelessRecipe);
    }

    public void registerShapelessRecipe(int n2, ShapelessRecipe shapelessRecipe) {
        Map map;
        int n3 = CraftingManager.b(shapelessRecipe.getResult());
        switch (n2) {
            case 313: {
                map = this.g.computeIfAbsent(n3, n -> new HashMap<>());
                break;
            }
            case 332: {
                map = this.o.computeIfAbsent(n3, n -> new HashMap<>());
                break;
            }
            case 388: {
                map = this.e.computeIfAbsent(n3, n -> new HashMap<>());
                break;
            }
            case 419: {
                map = this.d.computeIfAbsent(n3, n -> new HashMap<>());
                break;
            }
            case 527: {
                map = this.shapelessRecipes.computeIfAbsent(n3, n -> new HashMap<>());
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid protocol: " + n2 + " Supported: 527, 419, 388, 332, 313");
            }
        }
        map.put(CraftingManager.a(shapelessRecipe.getIngredientsAggregate()), shapelessRecipe);
    }

    public void registerFurnaceRecipe(FurnaceRecipe furnaceRecipe) {
        Server.mvw("CraftingManager#registerFurnaceRecipe(FurnaceRecipe)");
        this.registerFurnaceRecipe(313, furnaceRecipe);
        this.registerFurnaceRecipe(388, furnaceRecipe);
        this.registerFurnaceRecipe(419, furnaceRecipe);
    }

    public void registerFurnaceRecipe(int n, FurnaceRecipe furnaceRecipe) {
        if (n == 419) {
            this.furnaceRecipes.put(CraftingManager.b(furnaceRecipe.getInput()), furnaceRecipe);
        } else if (n == 388) {
            this.s.put(CraftingManager.b(furnaceRecipe.getInput()), furnaceRecipe);
        } else if (n == 313) {
            this.w.put(CraftingManager.b(furnaceRecipe.getInput()), furnaceRecipe);
        } else {
            throw new IllegalArgumentException("Invalid protocol: " + n + " Supported: 419, 388, 313");
        }
    }

    public void registerContainerRecipe(ContainerRecipe containerRecipe) {
        Server.mvw("CraftingManager#registerContainerRecipe(ContainerRecipe)");
        this.registerContainerRecipe(388, containerRecipe);
        this.registerContainerRecipe(407, containerRecipe);
    }

    public void registerContainerRecipe(int n, ContainerRecipe containerRecipe) {
        Item item = containerRecipe.getIngredient();
        Item item2 = containerRecipe.getInput();
        if (n == 407) {
            this.containerRecipes.put(CraftingManager.a(item.getId(), item2.getId()), containerRecipe);
        } else if (n == 388) {
            this.n.put(CraftingManager.a(item.getId(), item2.getId()), containerRecipe);
        } else {
            throw new IllegalArgumentException("Invalid protocol: " + n + " Supported: 407, 388");
        }
    }

    public void registerBrewingRecipe(BrewingRecipe brewingRecipe) {
        Server.mvw("CraftingManager#registerBrewingRecipe(BrewingRecipe)");
        this.registerBrewingRecipe(388, brewingRecipe);
        this.registerBrewingRecipe(407, brewingRecipe);
    }

    public void registerBrewingRecipe(int n, BrewingRecipe brewingRecipe) {
        Item item = brewingRecipe.getIngredient();
        Item item2 = brewingRecipe.getInput();
        if (n == 407) {
            this.brewingRecipes.put(CraftingManager.a(item, item2), brewingRecipe);
        } else if (n == 388) {
            this.m.put(CraftingManager.a(item, item2), brewingRecipe);
        } else {
            throw new IllegalArgumentException("Invalid protocol: " + n + " Supported: 407, 388");
        }
    }

    public void registerMultiRecipe(MultiRecipe multiRecipe) {
        this.registerMultiRecipe(407, multiRecipe);
    }

    public void registerMultiRecipe(int n, MultiRecipe multiRecipe) {
        if (n != 407) {
            throw new IllegalArgumentException("Invalid protocol: " + n + " Supported: 407");
        }
        this.multiRecipes.put(multiRecipe.getId(), multiRecipe);
    }

    public void registerCampfireRecipe(int n, CampfireRecipe campfireRecipe) {
        this.campfireRecipes.put(CraftingManager.b(campfireRecipe.getInput()), campfireRecipe);
    }

    public CraftingRecipe matchRecipe(List<Item> list, Item item, List<Item> list2) {
        Server.mvw("CraftingManager#matchRecipe(List<Item>, Item , List<Item>)");
        return this.matchRecipe(ProtocolInfo.CURRENT_PROTOCOL, list, item, list2);
    }

    public CraftingRecipe matchRecipe(int n, List<Item> list, Item item, List<Item> list2) {
        int n2 = CraftingManager.b(item);
        Map<Integer, Map<UUID, ShapedRecipe>> map2 = this.getShapedRecipes(n);
        if (map2.containsKey(n2)) {
            list.sort(recipeComparator);
            var map = map2.get(n2);
            if (map != null) {
                var object2 = CraftingManager.a(list);
                var object = map.get(object2);
                if (object != null && (((ShapedRecipe)object).matchItems(list, list2) || CraftingManager.a((CraftingRecipe)object, list, item, list2))) {
                    return object;
                }
                for (var object3 : map.values()) {
                    if (!((ShapedRecipe)object3).matchItems(list, list2) && !CraftingManager.a((CraftingRecipe)object3, list, item, list2)) continue;
                    return object3;
                }
            }
        }
        var map = this.getShapelessRecipes(n);
        if (map.containsKey(n2)) {
            list.sort(recipeComparator);
            var object2 = map.get(n2);
            if (object2 == null) {
                return null;
            }
            var object = CraftingManager.a(list);
            ShapelessRecipe shapelessRecipe = (ShapelessRecipe)object2.get(object);
            if (shapelessRecipe != null && (shapelessRecipe.matchItems(list, list2) || CraftingManager.a(shapelessRecipe, list, item, list2))) {
                return shapelessRecipe;
            }
            for (ShapelessRecipe shapelessRecipe2 : object2.values()) {
                if (!shapelessRecipe2.matchItems(list, list2) && !CraftingManager.a(shapelessRecipe2, list, item, list2)) continue;
                return shapelessRecipe2;
            }
        }
        return null;
    }

    public FurnaceRecipe matchFurnaceRecipe(Item item) {
        Map<Integer, FurnaceRecipe> map = this.getFurnaceRecipes(ProtocolInfo.CURRENT_PROTOCOL);
        FurnaceRecipe furnaceRecipe = map.get(CraftingManager.b(item));
        if (furnaceRecipe == null) {
            furnaceRecipe = map.get(CraftingManager.b(item.getId(), 0));
        }
        return furnaceRecipe;
    }

    public ContainerRecipe matchContainerRecipe(Item item, Item item2) {
        return this.getContainerRecipes(ProtocolInfo.CURRENT_PROTOCOL).get(CraftingManager.a(item.getId(), item2.getId()));
    }

    public BrewingRecipe matchBrewingRecipe(Item item, Item item2) {
        return this.getBrewingRecipes(ProtocolInfo.CURRENT_PROTOCOL).get(CraftingManager.a(item, item2));
    }

    public CampfireRecipe matchCampfireRecipe(Item item) {
        CampfireRecipe campfireRecipe = this.campfireRecipes.get(CraftingManager.b(item));
        if (campfireRecipe == null) {
            campfireRecipe = this.campfireRecipes.get(CraftingManager.b(item.getId(), 0));
        }
        return campfireRecipe;
    }

    private static boolean a(CraftingRecipe craftingRecipe, List<Item> list, Item item, List<Item> list2) {
        Item item2 = craftingRecipe.getResult();
        if (item.equals(item2, item2.hasMeta(), item2.hasCompoundTag()) && item.getCount() % item2.getCount() == 0) {
            int n = item.getCount() / item2.getCount();
            return craftingRecipe.matchItems(list, list2, n);
        }
        return false;
    }

    private static int b(Item item) {
        return CraftingManager.b(item.getId(), item.getDamage());
    }

    private static int b(int n, int n2) {
        return n << 12 | n2 & 0xFFF;
    }

    private static int a(Item item) {
        return CraftingManager.b(item) << 6 | item.getCount() & 0x3F;
    }

    private static UUID a(Collection<Item> collection) {
        BinaryStream binaryStream = new BinaryStream();
        for (Item item : collection) {
            binaryStream.putVarInt(CraftingManager.a(item));
        }
        return UUID.nameUUIDFromBytes(binaryStream.getBuffer());
    }

    private static int a(int n, int n2) {
        return n << 15 | n2;
    }

    private static int a(Item item, Item item2) {
        int n = (item.getId() & 0x3FF) << 6 | item.getDamage() & 0x3F;
        int n2 = (item2.getId() & 0x3FF) << 6 | item2.getDamage() & 0x3F;
        return n << 16 | n2;
    }

    static {
        recipeComparator = (item, item2) -> {
            if (item.getId() > item2.getId()) {
                return 1;
            }
            if (item.getId() < item2.getId()) {
                return -1;
            }
            if (item.getDamage() > item2.getDamage()) {
                return 1;
            }
            if (item.getDamage() < item2.getDamage()) {
                return -1;
            }
            return Integer.compare(item.getCount(), item2.getCount());
        };
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public static class Entry {
        final int f;
        final int a;
        final int c;
        final int d;
        final String b;
        final int e;

        public Entry(int n, int n2, int n3, int n4, String string, int n5) {
            this.f = n;
            this.a = n2;
            this.c = n3;
            this.d = n4;
            this.b = string;
            this.e = n5;
        }
    }
}

