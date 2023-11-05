/*
 * This is the latest source code of Superflat World No Slimes.
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

import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.fabric.callbacks.CollectiveEntityEvents;
import com.natamus.superflatworldnoslimes.events.SlimeEvent;
import com.natamus.superflatworldnoslimes.util.Reference;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class ModFabric implements ModInitializer {
    private static boolean allowSlimes = false;

    @Override
    public void onInitialize() {
        setGlobalConstants();
        ModCommon.init();

        loadEvents();

        RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
    }

    private void loadEvents() {
        CollectiveEntityEvents.PRE_ENTITY_JOIN_WORLD.register((Level world, Entity entity) -> {
            return SlimeEvent.onWorldJoin(world, entity);
        });
    }

    private static void setGlobalConstants() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(CommandManager.literal("allowslimes")
                .executes(context -> {
                    allowSlimes = true;
                    return 1;
                })
                .then(CommandManager.literal("c")
                    .executes(context -> {
                        allowSlimes = false;
                        return 1;
                    })
                )
            );
        });
    }
}

public class SlimeEvent {
    public static boolean onWorldJoin(Level world, Entity entity) {
        if (entity.getType().equals(EntityType.SLIME) && !allowSlimes) {
            entity.remove();
            return false;
        }
        return true;
    }
}
