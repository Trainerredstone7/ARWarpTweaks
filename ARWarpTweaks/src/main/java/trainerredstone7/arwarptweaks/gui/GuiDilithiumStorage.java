package trainerredstone7.arwarptweaks.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import trainerredstone7.arwarptweaks.ARWarpTweaks;
import trainerredstone7.arwarptweaks.container.ContainerDilithiumStorage;

public class GuiDilithiumStorage extends GuiContainer {

	public static final int WIDTH = 176;
	public static final int HEIGHT = 166;

	private static final ResourceLocation background = new ResourceLocation(ARWarpTweaks.MODID, "textures/gui/alloyformer.png");
	
	public GuiDilithiumStorage(ContainerDilithiumStorage container) {
		super(container);
		xSize = WIDTH;
		ySize = HEIGHT;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(background);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX,  mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}

}
