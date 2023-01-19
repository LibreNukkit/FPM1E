/*
 * Decompiled with CFR 0.152.
 */
package cn.nukkit.command.defaults;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.defaults.VanillaCommand;
import cn.nukkit.level.Level;
import cn.nukkit.math.NukkitMath;
import cn.nukkit.utils.TextFormat;

public class GarbageCollectorCommand
extends VanillaCommand {
    public GarbageCollectorCommand(String string) {
        super(string, "%nukkit.command.gc.description", "%nukkit.command.gc.usage");
        this.setPermission("nukkit.command.gc");
        this.commandParameters.clear();
    }

    @Override
    public boolean execute(CommandSender commandSender, String string, String[] stringArray) {
        if (!this.testPermission(commandSender)) {
            return true;
        }
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        long l = Runtime.getRuntime().freeMemory();
        for (Level level : commandSender.getServer().getLevels().values()) {
            int n4 = level.getChunks().size();
            int n5 = level.getEntities().length;
            int n6 = level.getBlockEntities().size();
            level.doChunkGarbageCollection();
            level.unloadChunks(true);
            n += n4 - level.getChunks().size();
            n2 += n5 - level.getEntities().length;
            n3 += n6 - level.getBlockEntities().size();
        }
        System.gc();
        long l2 = Runtime.getRuntime().freeMemory() - l;
        commandSender.sendMessage(TextFormat.GREEN + "---- " + TextFormat.WHITE + "Garbage collection result" + TextFormat.GREEN + " ----");
        commandSender.sendMessage(TextFormat.GOLD + "Chunks: " + TextFormat.RED + n);
        commandSender.sendMessage(TextFormat.GOLD + "Entities: " + TextFormat.RED + n2);
        commandSender.sendMessage(TextFormat.GOLD + "Block Entities: " + TextFormat.RED + n3);
        commandSender.sendMessage(TextFormat.GOLD + "Memory freed: " + TextFormat.RED + NukkitMath.round((double)l2 / 1024.0 / 1024.0, 2) + " MB");
        return true;
    }
}

