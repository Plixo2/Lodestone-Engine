package net.plixo.paper;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.plixo.paper.client.forge.KeyBinds;
import net.plixo.paper.client.forge.events.KeyInput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

@Mod(LodestoneMod.Mod_ID)
public class LodestoneMod {
    public final static String Mod_ID = "lodestone";
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * First function called by forge. Used to initialise.
     * Eventbuses(Listeners) are being registered.
     * Therefore setups are being called.
     *
     * Only forge events.
     */
    public LodestoneMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
    }


    /**
     * Setup function that registers key-input-events.
     *
     * @param event The initialise event. Variable is being ignored.
     */
    private void setup(final FMLCommonSetupEvent event) {
        KeyBinds.register();
        MinecraftForge.EVENT_BUS.register(new KeyInput());
        LOGGER.info("//PRE-INIT//");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    /**
     * Starts the client in {@link Lodestone}.
     *
     * @param event The initialise event. Variable only used to log settings.
     */
    private void doClientStuff(final FMLClientSetupEvent event) {
        Lodestone.startClient();
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    /**
     * Simple start log.
     *
     * @param event Parameter needed for function call. Is being ignored.
     */
    @SuppressWarnings("SpellCheckingInspection")
    private void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo("examplemod", "helloworld", () -> {
            LOGGER.info("//MDK//");
            return "Hello world";
        });
    }

    /**
     * Simple start log.
     *
     * @param event Parameter needed for function call. Used for logging.
     */
    private void processIMC(final InterModProcessEvent event) {
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    /**
     * Simple start log.
     *
     * @param event Parameter needed for function call. Is being ignored.
     */
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        LOGGER.info("//Server Starting//");
    }

    /**
     * Simple logger on event registry
     */
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            LOGGER.info("//Register Block//");
        }
    }
}
