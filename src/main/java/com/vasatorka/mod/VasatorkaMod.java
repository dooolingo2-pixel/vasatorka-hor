package com.vasatorka.mod;

import java.util.Map;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
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

    // Броня Алоя: сильніша за незерит (у незериту 3/8/6/3, міцність 37, твердість 3)
    public static final ArmorMaterial ALOY_MATERIAL = new ArmorMaterial(
            50,
            Map.of(
                    ArmorType.HELMET, 4,
                    ArmorType.CHESTPLATE, 9,
                    ArmorType.LEGGINGS, 7,
                    ArmorType.BOOTS, 4),
            25,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            4.0F,
            0.2F,
            ItemTags.REPAIRS_NETHERITE_ARMOR,
            ResourceKey.create(EquipmentAssets.ROOT_ID, id("aloy")));

    public static final RegistryObject<Item> TAIL_OF_ALOY = ITEMS.register("tail_of_aloy",
            () -> new TailOfAloyItem(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, id("tail_of_aloy")))
                    .sword(ToolMaterial.DIAMOND, 4.0F, -2.4F)
                    .rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> ALOY_HELMET = armor("aloy_helmet", ArmorType.HELMET);
    public static final RegistryObject<Item> ALOY_CHESTPLATE = armor("aloy_chestplate", ArmorType.CHESTPLATE);
    public static final RegistryObject<Item> ALOY_LEGGINGS = armor("aloy_leggings", ArmorType.LEGGINGS);
    public static final RegistryObject<Item> ALOY_BOOTS = armor("aloy_boots", ArmorType.BOOTS);

    public static final RegistryObject<EntityType<VasylEntity>> VASYL = ENTITIES.register("vasyl",
            () -> EntityType.Builder.of(VasylEntity::new, MobCategory.MONSTER)
                    .sized(0.9F, 2.9F)
                    .clientTrackingRange(16)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, id("vasyl"))));

    public static final RegistryObject<EntityType<GopnikEntity>> GOPNIK = ENTITIES.register("gopnik",
            () -> EntityType.Builder.of(GopnikEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(8)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, id("gopnik"))));

    public VasatorkaMod(FMLJavaModLoadingContext context) {
        var modBus = context.getModBusGroup();
        ITEMS.register(modBus);
        ENTITIES.register(modBus);
        EntityAttributeCreationEvent.getBus(modBus).addListener(VasatorkaMod::onAttributes);
        EntityRenderersEvent.RegisterRenderers.getBus(modBus).addListener(ClientSetup::registerRenderers);
        LivingHurtEvent.BUS.addListener(ArmorEvents::onHurt);
    }

    private static RegistryObject<Item> armor(String name, ArmorType type) {
        return ITEMS.register(name,
                () -> new Item(new Item.Properties()
                        .setId(ResourceKey.create(Registries.ITEM, id(name)))
                        .humanoidArmor(ALOY_MATERIAL, type)
                        .rarity(Rarity.EPIC)));
    }

    public static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }

    private static void onAttributes(EntityAttributeCreationEvent event) {
        event.put(VASYL.get(), VasylEntity.createBossAttributes());
        event.put(GOPNIK.get(), GopnikEntity.createGopnikAttributes());
    }
}
