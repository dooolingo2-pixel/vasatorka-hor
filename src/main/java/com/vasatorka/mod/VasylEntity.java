package com.vasatorka.mod;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class VasylEntity extends Warden {
    private final ServerBossEvent bossEvent = new ServerBossEvent(
            Component.literal("Василь"),
            BossEvent.BossBarColor.PURPLE,
            BossEvent.BossBarOverlay.PROGRESS);

    public VasylEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.setCustomName(Component.literal("Василь"));
    }

    public static AttributeSupplier createBossAttributes() {
        return Warden.createAttributes()
                .add(Attributes.MAX_HEALTH, 250.0D)
                .add(Attributes.ATTACK_DAMAGE, 14.0D)
                .build();
    }

    // Гопники для Василя свої: він на них не нападає
    @Override
    public boolean isAlliedTo(Entity other) {
        return other instanceof GopnikEntity || super.isAlliedTo(other);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level() instanceof ServerLevel serverLevel) {
            this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());

            // Завжди агресивний: раз на секунду "лютує" на найближчого гравця
            if (this.tickCount % 20 == 0) {
                Player player = serverLevel.getNearestPlayer(this, 40.0D);
                if (player != null) {
                    this.increaseAngerAt(player, 150, false);
                }
            }

            // Раз на 15 секунд призиває трьох гопників
            LivingEntity target = this.getTarget();
            if (target != null && this.tickCount % 300 == 0) {
                for (int i = 0; i < 3; i++) {
                    summonGopnik(serverLevel, target);
                }
            }
        }
    }

    private void summonGopnik(ServerLevel level, LivingEntity target) {
        GopnikEntity gopnik = new GopnikEntity(VasatorkaMod.GOPNIK.get(), level);
        double x = this.getX() + (this.random.nextDouble() - 0.5D) * 6.0D;
        double z = this.getZ() + (this.random.nextDouble() - 0.5D) * 6.0D;
        gopnik.setPos(x, this.getY(), z);
        gopnik.setBaby(true);
        gopnik.setCustomName(Component.literal("Гопник"));
        gopnik.setCustomNameVisible(true);
        gopnik.setTarget(target);
        level.addFreshEntity(gopnik);
    }

    // Кожен удар Василя отруює
    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        boolean hit = super.doHurtTarget(level, target);
        if (hit && target instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 1));
        }
        return hit;
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
    }
}
