package net.plixo.lodestone.client.forge;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class KeyBinds {

    public static KeyBinding openUI;
    public static void register() {
        openUI = new KeyBinding("open.engine", GLFW.GLFW_KEY_RIGHT_SHIFT, "key.categories.misc");
        ClientRegistry.registerKeyBinding(openUI);
    }
}
