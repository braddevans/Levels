package com.thexfactor117.levels.events;

import com.thexfactor117.levels.Reference;
import com.thexfactor117.levels.helpers.Experience;
import com.thexfactor117.levels.helpers.NBTHelper;
import com.thexfactor117.levels.helpers.Rarity;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
public class EventItemTooltip 
{
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void addInformation(ItemTooltipEvent event)
	{
		ItemStack stack = event.itemStack;
		Item item = stack.getItem();
		NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);

		if (item != null)
		{
			if (item instanceof ItemSword)
			{
				if (nbt != null)
				{	
					Rarity rarity = Rarity.getRarity(nbt);
					
					String exp;
					
					if (Experience.getLevel(nbt) == Reference.MAX_LEVEL) exp = I18n.format("levels.experience.max");
					else exp = Experience.getExperience(nbt) + " / " + Experience.getMaxLevelExp(Experience.getLevel(nbt));
					
					event.toolTip.add("");
					event.toolTip.add(rarity.getColor() + EnumChatFormatting.ITALIC + I18n.format("levels.rarity." + rarity.ordinal()));
					event.toolTip.add("Level: " + Experience.getLevel(nbt));
					event.toolTip.add("Experience: " + exp);
					event.toolTip.add("Durability: " + (stack.getMaxDamage() - stack.getItemDamage()) + " / " + stack.getMaxDamage());
				}
				else
				{
					nbt = new NBTTagCompound();
					stack.setTagCompound(nbt);
					
					Rarity.UNKNOWN.setRarity(nbt);
					Experience.setExperience(nbt, 0);
					Experience.setLevel(nbt, 1);
				}
			}
		}
	}
}
