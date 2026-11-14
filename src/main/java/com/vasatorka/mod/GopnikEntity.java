package com.vasatorka.mod;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.Level;

public class GopnikEntity extends Zombie {
    public GopnikEntity(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier createGopnikAttributes() {
        return Zombie.createAttributes().build();
    }

    // Гопники не горять на сонці
    @Override
    protected boolean isSunSensitive() {
        return false;
    }
}
