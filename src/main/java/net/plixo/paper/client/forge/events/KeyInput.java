package net.plixo.paper.client.forge.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.list.KeyBindingList;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.plixo.paper.Paper;
import net.plixo.paper.client.editor.ui.UIEditor;
import net.plixo.paper.client.engine.buildIn.blueprint.variable.Variable;
import net.plixo.paper.client.engine.buildIn.blueprint.variable.VariableType;
import net.plixo.paper.client.forge.Keybinds;
import org.lwjgl.glfw.GLFW;

public class KeyInput {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {

        Variable keyVar = new Variable(VariableType.INT, "Key");

        keyVar.booleanValue =   event.getAction() == GLFW.GLFW_PRESS;
        keyVar.intValue =   event.getKey();
    //    KeyBinding
    //    TranslationTextComponent translationTextComponent = new TranslationTextComponent(keybinding.getKeyDescription());

        Paper.update("OnKey", keyVar);
     //   event.
        if (Keybinds.openUI.isPressed()) {
            //   Minecraft.getInstance().player.abilities.isFlying = true;
            Minecraft.getInstance().displayGuiScreen(new UIEditor());
        }
       // System.out.println("Hell");
        //Minecraft.getInstance().player.abilities.
    }
}