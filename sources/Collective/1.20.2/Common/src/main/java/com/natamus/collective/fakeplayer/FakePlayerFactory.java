/*
 * This is the latest source code of Collective.
 * Minecraft version: 1.20.2.
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

package com.natamus.collective.fakeplayer;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.level.ServerLevel;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.UUID;

public class FakePlayerFactory {
	private static final GameProfile MINECRAFT = new GameProfile(UUID.fromString("41C82C87-7AfB-4024-BA57-13D2C99CAE77"), "[Minecraft]");
	private static final Map<GameProfile, FakePlayer> fakePlayers = Maps.newHashMap();
	private static WeakReference<FakePlayer> MINECRAFT_PLAYER = null;

	public static FakePlayer getMinecraft(ServerLevel world) {
		FakePlayer ret = MINECRAFT_PLAYER != null ? MINECRAFT_PLAYER.get() : null;
		if (ret == null) {
			ret = FakePlayerFactory.get(world,  MINECRAFT);
			MINECRAFT_PLAYER = new WeakReference<>(ret);
		}
		return ret;
	}

	public static FakePlayer get(ServerLevel serverLevel, GameProfile gameProfile) {
		if (!fakePlayers.containsKey(gameProfile)) {
			FakePlayer fakePlayer = new FakePlayer(serverLevel.getServer(), serverLevel, gameProfile, null);
			fakePlayers.put(gameProfile, fakePlayer);
		}

		return fakePlayers.get(gameProfile);
	}

	public static void unloadWorld(ServerLevel world) {
		fakePlayers.entrySet().removeIf(entry -> entry.getValue().level() == world);
		if (MINECRAFT_PLAYER != null && MINECRAFT_PLAYER.get() != null && MINECRAFT_PLAYER.get().level() == world) {
			FakePlayer mc = MINECRAFT_PLAYER.get();
			if (mc != null && mc.level() == world) {
				MINECRAFT_PLAYER = null;
			}
		}
	}
}