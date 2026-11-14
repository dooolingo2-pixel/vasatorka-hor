package com.vasatorka.mod;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TailOfAloyItem extends Item {
    public TailOfAloyItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.hurtEnemy(stack, target, attacker);
        // Сліпота 5 секунд і отруєння 5 секунд
        target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0));
        target.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 1));
    }
}
