package me.preciouso.commanditem.item;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CommandItem extends Item {
    private static final String COMMAND_TAG = "item_command";

    public CommandItem(Properties properties) {
        super(properties);
    }

    // Get NBT Command tag
    public String getCommand(ItemStack stack) {
        String command = "";

        if (stack.hasTag()) {
            assert stack.getTag() != null;
            if (stack.getTag().contains(COMMAND_TAG)) {
                command = stack.getTag().getString(COMMAND_TAG);
            }
        }
        return command;
    }

    // Set NBT Command Tag
    public void setCommand(ItemStack stack, String command) {
        CompoundNBT nbt;

        if (stack.hasTag()) {
            nbt = stack.serializeNBT().getCompound(COMMAND_TAG);
        } else {
            nbt = new CompoundNBT();
        }

        if (StringUtils.isNullOrEmpty(command) && command.charAt(0) != '/') {
            command = "/" + command;
        }

        nbt.putString(COMMAND_TAG, command);
        stack.setTag(nbt);
    }

    // Run command from item
    public void execCommand(ItemStack stack) {
        String command = getCommand(stack);

        if (Minecraft.getInstance().player != null && !StringUtils.isNullOrEmpty(command)) {
            Minecraft.getInstance().player.sendChatMessage(command);
        }
    }

    // Non tool item
    @Override
    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        return false; // super.canPlayerBreakBlockWhileHolding(state, worldIn, pos, player);
    }

    // Left click on entity
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        this.execCommand(stack);
        return super.hitEntity(stack, target, attacker);
    }

    // Left Click on Block
    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, PlayerEntity player) {
        this.execCommand(itemstack);
        return false;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return true;
    }

    // On right click, open Item GUI
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack items = playerIn.getHeldItemMainhand();
        if (!(items.getItem() instanceof CommandItem)) {
            // Right clicked in offhand most likely
            items = playerIn.getHeldItemOffhand();
        }

        Minecraft.getInstance().displayGuiScreen(new ItemGui(items));

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

}
