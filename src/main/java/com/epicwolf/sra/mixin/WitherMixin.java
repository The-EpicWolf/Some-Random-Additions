package com.epicwolf.sra.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(WitherEntity.class)
public abstract class WitherMixin extends HostileEntity {

    @Shadow public abstract boolean shouldRenderOverlay();

    @Unique
    private boolean halfHealthGoal;


    public WitherMixin(EntityType<? extends WitherEntity> entityType, World world) {
        super(entityType, world);
        this.halfHealthGoal = false;
    }

    /**
     * @author EpicWolf
     * @reason Upgrading Wither stats
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createWitherAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.MAX_HEALTH, 400.0).add(EntityAttributes.MOVEMENT_SPEED, 0.8f).add(EntityAttributes.FLYING_SPEED, 0.8f).add(EntityAttributes.FOLLOW_RANGE, 40.0).add(EntityAttributes.ARMOR, 4.0);
    }

    @Inject(method = "mobTick", at = @At("HEAD"))
    protected void tick(ServerWorld world, CallbackInfo ci) {
        if (this.shouldRenderOverlay() && !this.halfHealthGoal) {
            for (int i=0; i < 3; i++) {
                WitherSkeletonEntity skeleton = new WitherSkeletonEntity(EntityType.WITHER_SKELETON, world);
                skeleton.setStackInHand(Hand.MAIN_HAND, Items.STONE_SWORD.getDefaultStack());
                skeleton.setPos(this.getX()+(Math.random()*2-1), this.getY(), this.getZ()+(Math.random()*2-1));
                world.spawnEntity(skeleton);
            }
            this.setHalfHealthGoal(true);
        }
    }

    @Inject(method = "writeCustomData", at = @At("TAIL"))
    protected void writeCustomData(WriteView view, CallbackInfo ci) {
        view.putBoolean("HalfHealthGoal", this.halfHealthGoal);
    }

    @Inject(method = "readCustomData", at = @At("TAIL"))
    protected void readCustomData(ReadView view, CallbackInfo ci) {
        this.setHalfHealthGoal(view.getBoolean("HalfHealthGoal", false));
    }

    @Unique
    public void setHalfHealthGoal(boolean goal) {
        this.halfHealthGoal = goal;
    }

}
