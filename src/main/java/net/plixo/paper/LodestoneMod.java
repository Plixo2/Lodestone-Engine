package net.plixo.paper;

import com.bulenkov.darcula.DarculaLaf;
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
import net.plixo.paper.client.forge.events.Render2D;
import net.plixo.paper.client.forge.events.Ticking;
import net.plixo.paper.client.visualscript.CustomFunction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.mom.kafka.KafkaManager;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLookAndFeel;
import java.awt.*;
import java.util.stream.Collectors;

@Mod(LodestoneMod.Mod_ID)
public class LodestoneMod {
    public final static String Mod_ID = "lodestone";
    private static final Logger LOGGER = LogManager.getLogger();

    static {
        System.setProperty("java.awt.headless","false");
        /*
        UIManager.put( "control", new Color( 49, 56, 107) );
        UIManager.put( "info", new Color(42, 49, 94) );
        UIManager.put( "nimbusBase", new Color( 18, 30, 49) );
        UIManager.put( "nimbusAlertYellow", new Color( 248, 187, 0) );
        UIManager.put( "nimbusDisabledText", new Color( 34, 39, 110) );
        UIManager.put( "nimbusFocus", new Color(30, 50, 105) );
        UIManager.put( "nimbusGreen", new Color(176,179,50) );
        UIManager.put( "nimbusInfoBlue", new Color( 66, 139, 221) );
        UIManager.put( "nimbusLightBackground", new Color( 18, 30, 49) );
        UIManager.put( "nimbusOrange", new Color(191,98,4) );
        UIManager.put( "nimbusRed", new Color(169,46,34) );
        UIManager.put( "nimbusSelectedText", new Color( 222, 222, 222) );
        UIManager.put( "nimbusSelectionBackground", new Color( 84, 87, 148) );
        UIManager.put( "text", new Color( 189, 189, 189) );
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

         */

        BasicLookAndFeel darculaLookAndFeel = new DarculaLaf();
        try {
            UIManager.setLookAndFeel(darculaLookAndFeel);
        } catch (UnsupportedLookAndFeelException ex) {
            // ups!
        }

        System.out.println("Loaded Modclass");
    }

    //TODO more builtin Nodes, change "Function" to "Node"
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
        MinecraftForge.EVENT_BUS.register(new Ticking());
        MinecraftForge.EVENT_BUS.register(new Render2D());

        LOGGER.info("//PRE-INIT//");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    /**
     * Starts the client in {@link Lodestone}.
     *
     * @param event The initialise event. Variable only used to log settings.
     */
    private void doClientStuff(final FMLClientSetupEvent event) {
        LOGGER.info("Begin Startup");
        Lodestone.startClient();
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    /**
     * Simple start log.
     *
     * @param event Parameter needed for function call. Is being ignored.
     */

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
