package com.vasatorka.mod;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ArmorEvents {
    // Хто носить хоч одну частину броні Алоя, той не отримує шкоди від вибухів
    public static void onHurt(LivingHurtEvent event) {
        if (!event.getSource().is(DamageTypeTags.IS_EXPLOSION)) {
            return;
        }
        LivingEntity entity = event.getEntity();
        if (wearsAloy(entity)) {
            event.setAmount(0.0F);
        }
    }

    private static boolean wearsAloy(LivingEntity entity) {
        ItemStack head = entity.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack legs = entity.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack feet = entity.getItemBySlot(EquipmentSlot.FEET);
        return head.is(VasatorkaMod.ALOY_HELMET.get())
                || chest.is(VasatorkaMod.ALOY_CHESTPLATE.get())
                || legs.is(VasatorkaMod.ALOY_LEGGINGS.get())
                || feet.is(VasatorkaMod.ALOY_BOOTS.get());
    }
}
