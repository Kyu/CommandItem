package me.preciouso.commanditem;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CommandItemMod.MOD_ID)
public class CommandItemMod {
    public static final String MOD_ID = "commanditem";
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();


    public CommandItemMod() {
        Registry.ITEMS.register((FMLJavaModLoadingContext.get().getModEventBus()));
    }
}
