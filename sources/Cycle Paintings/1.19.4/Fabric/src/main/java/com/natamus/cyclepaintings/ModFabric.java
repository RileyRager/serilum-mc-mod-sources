/*
 * This is the latest source code of Cycle Paintings.
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

package com.natamus.cyclepaintings;

import com.natamus.collective.check.RegisterMod;
import com.natamus.cyclepaintings.data.Constants;
import com.natamus.cyclepaintings.events.PaintingEvent;
import com.natamus.cyclepaintings.util.Reference;
import com.natamus.cyclepaintings.util.Util;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.core.registries.Registries;

public class ModFabric implements ModInitializer {
	
	@Override
	public void onInitialize() {
		setGlobalConstants();
		ModCommon.init();

		loadEvents();

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void loadEvents() {
		ServerLifecycleEvents.SERVER_STARTING.register(server -> {
			try {
				Util.setPaintings(server.registryAccess().registryOrThrow(Registries.PAINTING_VARIANT));
			}
			catch (Exception ex) {
				Constants.logger.warn("[" + Reference.NAME + "] Something went wrong while loading all paintings.");
			}
		});

		UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			return PaintingEvent.onClick(player, world, hand, entity, hitResult);
		});
	}

	private static void setGlobalConstants() {

	}
}
