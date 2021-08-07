package net.plixo.lodestone.client.ui.font;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.FontRenderer;

/**
 * super useless
 */
public class GlyphToMinecraft extends FontRenderer {

    GlyphPageFontRenderer fontRenderer;

    public GlyphToMinecraft(String font) {
        super(resourceLocation -> null);
        fontRenderer = GlyphPageFontRenderer.create(font,16,true,true,true);
    }


    @Override
    public int drawString(MatrixStack p_238421_1_, String text, float x, float y, int color) {
        int i = fontRenderer.drawString(text, x, y, color, false);
        return i;
    }

    @Override
    public int drawStringWithShadow(MatrixStack p_238405_1_, String text, float x, float y, int color) {
        int i = fontRenderer.drawString(text, x, y, color, true);
        return i;
    }

    @Override
    public int getStringWidth(String text) {
        return fontRenderer.getStringWidth(text);
    }
}
