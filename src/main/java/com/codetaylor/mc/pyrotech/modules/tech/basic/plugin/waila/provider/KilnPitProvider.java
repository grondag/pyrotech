package com.codetaylor.mc.pyrotech.modules.tech.basic.plugin.waila.provider;

import com.codetaylor.mc.pyrotech.library.util.plugin.waila.WailaUtil;
import com.codetaylor.mc.pyrotech.modules.tech.basic.plugin.waila.delegate.KilnPitProviderDelegate;
import com.codetaylor.mc.pyrotech.modules.tech.basic.tile.TileKilnPit;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class KilnPitProvider
    implements IWailaDataProvider,
    KilnPitProviderDelegate.IPitKilnDisplay {

  private final KilnPitProviderDelegate delegate;

  private List<String> tooltip;

  public KilnPitProvider() {

    this.delegate = new KilnPitProviderDelegate(this);
  }

  @Nonnull
  @Override
  public List<String> getWailaBody(
      ItemStack itemStack,
      List<String> tooltip,
      IWailaDataAccessor accessor,
      IWailaConfigHandler config
  ) {

    TileEntity tileEntity = accessor.getTileEntity();

    if (tileEntity instanceof TileKilnPit) {
      this.tooltip = tooltip;
      this.delegate.display((TileKilnPit) tileEntity);
      this.tooltip = null;
    }

    return tooltip;
  }

  @Override
  public void setOutputItems(ItemStackHandler stackHandler) {

    StringBuilder builder = new StringBuilder();

    for (int i = 0; i < stackHandler.getSlots(); i++) {
      ItemStack stackInSlot = stackHandler.getStackInSlot(i);

      if (!stackInSlot.isEmpty()) {
        builder.append(WailaUtil.getStackRenderString(stackInSlot));
      }
    }

    if (builder.length() > 0) {
      this.tooltip.add(builder.toString());
    }
  }

  @Override
  public void setRecipeProgress(ItemStack input, ItemStack output, int progress, int maxProgress) {

    String renderString = WailaUtil.getStackRenderString(input)
        + WailaUtil.getProgressRenderString(progress, maxProgress)
        + WailaUtil.getStackRenderString(output);
    this.tooltip.add(renderString);
  }
}
