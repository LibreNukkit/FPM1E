/*
 * Decompiled with CFR 0.152.
 */
package cn.nukkit;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.a;
import cn.nukkit.utils.TextFormat;
import java.util.HashMap;

public class Achievement {
    public static final HashMap<String, Achievement> achievements = new a();
    public final String message;
    public final String[] requires;

    public static boolean broadcast(Player player, String string) {
        if (!achievements.containsKey(string)) {
            return false;
        }
        String string2 = Server.getInstance().getLanguage().translateString("chat.type.achievement", player.getDisplayName(), TextFormat.GREEN + "[" + Achievement.achievements.get(string).message + "]", null);
        if (Server.getInstance().announceAchievements) {
            Server.getInstance().broadcastMessage(string2);
        } else {
            player.sendMessage(string2);
        }
        if (player.protocol >= 526) {
            player.sendToast(TextFormat.YELLOW + "Achievement get!" + TextFormat.RESET, Achievement.achievements.get(string).message);
        } else {
            player.sendTip(TextFormat.YELLOW + "Achievement get!\n" + TextFormat.RESET + Achievement.achievements.get(string).message);
        }
        return true;
    }

    public static boolean add(String string, Achievement achievement) {
        if (achievements.containsKey(string)) {
            return false;
        }
        achievements.put(string, achievement);
        return true;
    }

    public Achievement(String string, String ... stringArray) {
        this.message = string;
        this.requires = stringArray;
    }

    public String getMessage() {
        return this.message;
    }

    public void broadcast(Player player) {
        String string = Server.getInstance().getLanguage().translateString("chat.type.achievement", player.getDisplayName(), TextFormat.GREEN + "[" + this.message + "]", null);
        if (Server.getInstance().announceAchievements) {
            Server.getInstance().broadcastMessage(string);
        } else {
            player.sendMessage(string);
        }
        if (player.protocol >= 526) {
            player.sendToast(TextFormat.YELLOW + "Achievement get!" + TextFormat.RESET, this.message);
        } else {
            player.sendTip(TextFormat.YELLOW + "Achievement get!\n" + TextFormat.RESET + this.message);
        }
    }
}

