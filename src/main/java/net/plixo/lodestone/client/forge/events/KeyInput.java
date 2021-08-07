package net.plixo.lodestone.client.forge.events;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.plixo.lodestone.Lodestone;
import net.plixo.lodestone.client.engine.events.EKey;
import net.plixo.lodestone.client.forge.KeyBinds;
import net.plixo.lodestone.client.ui.ScreenMain;
import org.lwjgl.glfw.GLFW;

public class KeyInput {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        Lodestone.update(new EKey(event.getKey(), event.getAction() == GLFW.GLFW_PRESS, GLFW.glfwGetKeyName(event.getKey(), 0)));
        if (KeyBinds.openUI.isPressed()) {
                Minecraft.getInstance().displayGuiScreen(new ScreenMain());
        }
    }
}
