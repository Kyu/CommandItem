package me.preciouso.commanditem;

import me.preciouso.commanditem.item.CommandItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Registry {
    // Register Item Class to a Item Registry
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CommandItemMod.MOD_ID);

    public static final RegistryObject<Item> COMMAND_ITEM = ITEMS.register("commanditem",
            () -> new CommandItem(new Item.Properties().maxStackSize(1).group(ItemGroup.MISC)));
}
