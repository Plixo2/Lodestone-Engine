package net.plixo.lodestone.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;

public class CodeUtil {
    public Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }
    public ClientPlayerEntity getPlayer() {
        return Minecraft.getInstance().player;
    }
}
