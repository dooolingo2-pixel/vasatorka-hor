package com.vasatorka.mod;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ToolMaterial;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(VasatorkaMod.MOD_ID)
public class VasatorkaMod {
    public static final String MOD_ID = "vasatorka";

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MOD_ID);

    public static final RegistryObject<Item> TAIL_OF_ALOY = ITEMS.register("tail_of_aloy",
            () -> new TailOfAloyItem(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, id("tail_of_aloy")))
                    .sword(ToolMaterial.DIAMOND, 4.0F, -2.4F)
                    .rarity(Rarity.EPIC)));

    public static final RegistryObject<EntityType<VasylEntity>> VASYL = ENTITIES.register("vasyl",
            () -> EntityType.Builder.of(VasylEntity::new, MobCategory.MONSTER)
                    .sized(0.9F, 2.9F)
                    .clientTrackingRange(16)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, id("vasyl"))));

    public VasatorkaMod(FMLJavaModLoadingContext context) {
        var modBus = context.getModBusGroup();
        ITEMS.register(modBus);
        ENTITIES.register(modBus);
        EntityAttributeCreationEvent.getBus(modBus).addListener(VasatorkaMod::onAttributes);
        EntityRenderersEvent.RegisterRenderers.getBus(modBus).addListener(ClientSetup::registerRenderers);
    }

    public static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }

    private static void onAttributes(EntityAttributeCreationEvent event) {
        event.put(VASYL.get(), VasylEntity.createBossAttributes());
    }
}
