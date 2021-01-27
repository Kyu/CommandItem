package me.preciouso.commanditem.listener;

import me.preciouso.commanditem.item.CommandItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ItemListener {

    // Only way to listen to left click events.
    // TODO Send Packet to server? If necessary
    @SubscribeEvent
    public static void leftClick(PlayerInteractEvent.LeftClickEmpty event) {
        if (event.getItemStack().getItem() instanceof CommandItem) {
            ((CommandItem) event.getItemStack().getItem()).execCommand(event.getItemStack());
        }
    }
}
