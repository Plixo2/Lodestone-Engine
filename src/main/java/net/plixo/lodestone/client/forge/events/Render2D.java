package net.plixo.lodestone.client.forge.events;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.plixo.animation.Animation;

public class Render2D {
    @SubscribeEvent
    public void doOverlay(RenderGameOverlayEvent event) {
        if (event instanceof RenderGameOverlayEvent.Pre) {
            Animation.animate();
        }
    }

}
