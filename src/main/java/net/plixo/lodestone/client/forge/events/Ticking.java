package net.plixo.lodestone.client.forge.events;


import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.plixo.lodestone.Lodestone;
import net.plixo.lodestone.client.engine.events.ETick;
import net.plixo.lodestone.client.util.serialiable.Options;


public class Ticking {

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == (Options.options.usePreEvent.value ? TickEvent.Phase.START : TickEvent.Phase.END))
            Lodestone.update(new ETick());
    }


}
