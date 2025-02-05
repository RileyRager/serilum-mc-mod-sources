/*
 * This is the latest source code of Inventory Totem.
 * Minecraft version: 1.19.4.
 *
 * Please don't distribute without permission.
 * For all Minecraft modding projects, feel free to visit my profile page on CurseForge or Modrinth.
 *  CurseForge: https://curseforge.com/members/serilum/projects
 *  Modrinth: https://modrinth.com/user/serilum
 *  Overview: https://serilum.com/
 *
 * If you are feeling generous and would like to support the development of the mods, you can!
 *  https://ricksouth.com/donate contains all the information. <3
 *
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.inventorytotem.events;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TotemEvent {
	public static boolean allowPlayerDeath (ServerLevel world, ServerPlayer player) {
		if (player.getMainHandItem().getItem().equals(Items.TOTEM_OF_UNDYING) || player.getOffhandItem().getItem().equals(Items.TOTEM_OF_UNDYING)) {
			return true;
		}
		
		Inventory inv = player.getInventory();

		ItemStack totemstack = null;
		for(int i = 0; i < inv.getContainerSize(); i++) {
			ItemStack stack = inv.getItem(i);
			if (stack.getItem().equals(Items.TOTEM_OF_UNDYING)) {
				totemstack = stack;
				break;
			}
		}
		
		if (totemstack == null) {
			return true;
		}

		player.awardStat(Stats.ITEM_USED.get(Items.TOTEM_OF_UNDYING));
		CriteriaTriggers.USED_TOTEM.trigger(player, totemstack);

		player.setHealth(1.0F);
		player.removeAllEffects();
		player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
		player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
		player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
		world.broadcastEntityEvent(player, (byte)35);
		totemstack.shrink(1);
		return false;
	}
}
