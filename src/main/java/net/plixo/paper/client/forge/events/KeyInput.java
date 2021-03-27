package net.plixo.paper.client.forge.events;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.plixo.paper.Lodestone;
import net.plixo.paper.client.events.ClientEvent;
import net.plixo.paper.client.ui.GUI.GUIEditor;
import net.plixo.paper.client.avs.old.components.variable.Variable;
import net.plixo.paper.client.avs.old.components.variable.VariableType;
import net.plixo.paper.client.forge.KeyBinds;
import org.lwjgl.glfw.GLFW;

public class KeyInput {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {

        Lodestone.update(new ClientEvent.KeyEvent(event.getKey(),event.getAction() == GLFW.GLFW_PRESS,GLFW.glfwGetKeyName(event.getKey(), 0)));
        if (KeyBinds.openUI.isPressed()) {
            Minecraft.getInstance().displayGuiScreen(new GUIEditor());
        }
    }
}
