package trainerredstone7.arwarptweaks.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import trainerredstone7.arwarptweaks.ARWarpTweaks;
import trainerredstone7.arwarptweaks.container.ContainerDilithiumStorage;
import trainerredstone7.arwarptweaks.tile.TileDilithiumStorage;

public class GuiDilithiumStorage extends GuiContainer {

	public static final int WIDTH = 176;
	public static final int HEIGHT = 166;
	private TileDilithiumStorage tile;

	private static final ResourceLocation background = new ResourceLocation(ARWarpTweaks.MODID, "textures/gui/dilithiumstorage.png");
	
	public GuiDilithiumStorage(TileDilithiumStorage tile, ContainerDilithiumStorage container) {
		super(container);
		xSize = WIDTH;
		ySize = HEIGHT;
		this.tile = tile;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(background);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX,  mouseY, partialTicks);
		drawFuel();
		renderHoveredToolTip(mouseX, mouseY);
	}
	
	private void drawFuel() {
		GlStateManager.disableLighting();
		drawCenteredStringNoShadow(fontRenderer, "Fuel: " + tile.getFuel(), guiLeft + 88, guiTop + 54, 0xFF000000);
		GlStateManager.enableLighting();
	}
	
	private void drawCenteredStringNoShadow(FontRenderer fontRendererIn, String text, int x, int y, int color) {
		fontRendererIn.drawString(text, (x - fontRendererIn.getStringWidth(text) / 2), y, color);
	}

}
