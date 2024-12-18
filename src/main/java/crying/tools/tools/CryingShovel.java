package crying.tools.tools;

import crying.tools.Crying;
import crying.tools.other.CryingTags;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class CryingShovel extends ShovelItem {
    private static int durability = 16247;

    private static float speed = 32F;

    private static float attackDamageBonus = 0F;

    private static int enchantability = 120;

    public CryingShovel() {
        super(new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, durability, speed, attackDamageBonus, enchantability, CryingTags.CryingTag), 6.5F, -3F, new Item.Settings()
        .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Crying.MOD_ID, "crying_shovel")))
        .fireproof()
        .enchantable(enchantability));

        Crying.register(this, "crying_shovel");
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register((itemGroup) -> itemGroup.addAfter(Items.NETHERITE_SHOVEL, this));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        PlayerEntity player = null;
        if (entity instanceof PlayerEntity) 
            player = (PlayerEntity) entity;
        if (player == null)
            return;

        if (world.isClient && (player.getMainHandStack() == stack || player.getOffHandStack() == stack)) {
            Random random = world.getRandom();
            if (random.nextInt(16) == 0) {
                Hand hand = player.getMainHandStack() == stack ? Hand.MAIN_HAND : Hand.OFF_HAND;
                double x = player.getX() + (hand == Hand.MAIN_HAND ? -0.3 : 0.3);
                double y = player.getEyeY() - 0.5;
                double z = player.getZ() + random.nextDouble() - 0.5;
                world.addParticle(ParticleTypes.FALLING_OBSIDIAN_TEAR, x, y, z, 0.0, 0.0, 0.0);
            }
        }
    }
}
