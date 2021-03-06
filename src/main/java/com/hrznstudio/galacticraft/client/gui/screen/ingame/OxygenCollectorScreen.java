/*
 * Copyright (c) 2019-2021 HRZN LTD
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.hrznstudio.galacticraft.client.gui.screen.ingame;

import com.hrznstudio.galacticraft.Constants;
import com.hrznstudio.galacticraft.api.screen.MachineHandledScreen;
import com.hrznstudio.galacticraft.client.gui.widget.machine.CapacitorWidget;
import com.hrznstudio.galacticraft.client.gui.widget.machine.OxygenTankWidget;
import com.hrznstudio.galacticraft.screen.OxygenCollectorScreenHandler;
import com.hrznstudio.galacticraft.util.DrawableUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

/**
 * @author <a href="https://github.com/StellarHorizons">StellarHorizons</a>
 */
@Environment(EnvType.CLIENT)
public class OxygenCollectorScreen extends MachineHandledScreen<OxygenCollectorScreenHandler> {
    private static final Identifier BACKGROUND = new Identifier(Constants.MOD_ID, Constants.ScreenTextures.getRaw(Constants.ScreenTextures.OXYGEN_COLLECTOR_SCREEN));

    public OxygenCollectorScreen(OxygenCollectorScreenHandler handler, PlayerInventory inv, Text title) {
        super(handler, inv, inv.player.world, handler.machine.getPos(), title);
        this.backgroundHeight = 181;

        this.addWidget(new CapacitorWidget(handler.machine.getCapacitor(), 13, 13, 48, this::getEnergyTooltipLines, handler.machine::getStatus));
        this.addWidget(new OxygenTankWidget(handler.machine.getFluidTank().getTank(0), 36, 13, 48));
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        this.renderBackground(matrices);
        this.client.getTextureManager().bindTexture(BACKGROUND);

        this.drawTexture(matrices, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        DrawableUtils.drawCenteredString(matrices, textRenderer, I18n.translate("block.galacticraft-rewoven.oxygen_collector"), (this.width / 2), this.y + 5, Formatting.DARK_GRAY.getColorValue());
        String statusText = I18n.translate("ui.galacticraft-rewoven.machine.status");

        int statusX = this.x + 38;
        int statusY = this.y + 64;

        this.textRenderer.draw(matrices, statusText, statusX, statusY, Formatting.DARK_GRAY.getColorValue());

        this.textRenderer.draw(matrices, handler.machine.getStatus().getName(), statusX + this.textRenderer.getWidth(statusText), statusY, 0);

        DrawableUtils.drawCenteredString(matrices, this.textRenderer, new TranslatableText("ui.galacticraft-rewoven.machine.collecting", this.handler.machine.collectionAmount).getString(), (this.width / 2) + 10, statusY + 12, Formatting.DARK_GRAY.getColorValue());
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}
