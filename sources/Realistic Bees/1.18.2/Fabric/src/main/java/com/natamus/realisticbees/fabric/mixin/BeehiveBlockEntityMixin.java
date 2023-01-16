/*
 * This is the latest source code of Realistic Bees.
 * Minecraft version: 1.18.2.
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

package com.natamus.realisticbees.fabric.mixin;

import com.natamus.realisticbees.config.ConfigHandler;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BeehiveBlockEntity.class, priority = 1001)
public class BeehiveBlockEntityMixin {
    @Inject(method = "isFull()Z", at = @At(value = "HEAD"), cancellable = true)
    private void hurt(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(((BeehiveBlockEntity)(Object)this).getOccupantCount() == ConfigHandler.beeHiveBeeSpace);
    }

    @ModifyConstant(method = "addOccupantWithPresetTicks(Lnet/minecraft/world/entity/Entity;ZI)V", constant = @Constant(intValue = 3))
    public int addOccupantWithPresetTicks_increaseSize(int size) {
        return ConfigHandler.beeHiveBeeSpace;
    }
}
