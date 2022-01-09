package com.ryangar46.apollo.tag;

import com.ryangar46.apollo.Apollo;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class TagManager {
    public static final Tag.Identified<EntityType<?>> VACUUM_IMMUNE_CREATURES = TagFactory.ENTITY_TYPE.create(new Identifier(Apollo.MOD_ID, "vacuum_immune_creatures"));
    public static final Tag.Identified<Item> AIRTIGHT_BOOTS = TagFactory.ITEM.create(new Identifier(Apollo.MOD_ID, "airtight_boots"));
    public static final Tag.Identified<Item> AIRTIGHT_CHESTPLATES = TagFactory.ITEM.create(new Identifier(Apollo.MOD_ID, "airtight_chestplates"));
    public static final Tag.Identified<Item> AIRTIGHT_HELMETS = TagFactory.ITEM.create(new Identifier(Apollo.MOD_ID, "airtight_helmets"));
    public static final Tag.Identified<Item> AIRTIGHT_LEGGINGS = TagFactory.ITEM.create(new Identifier(Apollo.MOD_ID, "airtight_leggings"));

    public static void register() {
        Apollo.LOGGER.info("Registering tags");
    }
}
