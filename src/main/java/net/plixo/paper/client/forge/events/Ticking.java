package net.plixo.paper.client.forge.events;


import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.plixo.paper.Lodestone;
import net.plixo.paper.client.events.ClientEvent;
import net.plixo.paper.client.util.Options;


public class Ticking {

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == (Options.usePreEvent ? TickEvent.Phase.START : TickEvent.Phase.END))
            Lodestone.update(ClientEvent.TickEvent.event);
    }


}
