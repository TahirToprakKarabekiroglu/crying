package crying.tools.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import crying.tools.Crying;

@Mixin(InGameHud.class)
public class CryingArmorMixin {
    private static final Identifier ARMOR_EMPTY_TEXTURE = Identifier.of(Crying.MOD_ID, "hud/armor_empty");
    private static final Identifier ARMOR_HALF_TEXTURE = Identifier.of(Crying.MOD_ID, "hud/armor_half");
    private static final Identifier ARMOR_HALF_TEXTURE_2 = Identifier.of(Crying.MOD_ID, "hud/armor_half_2");
    private static final Identifier ARMOR_FULL_TEXTURE = Identifier.of(Crying.MOD_ID, "hud/armor_full");

    @Inject(method = "renderArmor", at = @At("HEAD"))
    private static void renderArmorMixin(DrawContext context, PlayerEntity player, int i, int j, int k, int x, CallbackInfo info) {
        int armor = player.getArmor();
        if (armor > 20) {
            var max = 20;
            if (armor > 40)
                max = 30;
            if (armor > 60)
                max = 40;

            int m = i - (j - 1) * k - 20;

            for (int n = 10; n < max; ++n) {
                int o = x + (n - 10) * 8;
                if (n >= 20)
                {
                    o = context.getScaledWindowWidth() / 2 + 91 - (n - 20) * 8 - 9;
                    m = i - (j - 1) * k - 10;
                }
                if (n >= 30)
                {
                    o = context.getScaledWindowWidth() / 2 + 91 - (n - 30) * 8 - 9;
                    m = i - (j - 1) * k - 20;
                }
                if (n * 2 + 1 < armor) {
                    context.drawGuiTexture(RenderLayer::getGuiTextured, ARMOR_FULL_TEXTURE, o, m, 9, 9);
                }
                if (n * 2 + 1 == armor) {
                    context.drawGuiTexture(RenderLayer::getGuiTextured, n >= 20 ? ARMOR_HALF_TEXTURE_2 : ARMOR_HALF_TEXTURE, o, m, 9, 9);
                }

                if (n * 2 + 1 > armor) {
                    context.drawGuiTexture(RenderLayer::getGuiTextured, ARMOR_EMPTY_TEXTURE, o, m, 9, 9);
                }
            }
        }
    }
}