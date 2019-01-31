package com.codetaylor.mc.pyrotech.modules.tech.basic.plugin.crafttweaker;

import com.codetaylor.mc.athenaeum.integration.crafttweaker.mtlib.helpers.CTLogHelper;
import com.codetaylor.mc.athenaeum.tools.ZenDocClass;
import com.codetaylor.mc.athenaeum.tools.ZenDocMethod;
import com.codetaylor.mc.pyrotech.modules.tech.basic.ModuleTechBasic;
import com.codetaylor.mc.pyrotech.modules.tech.basic.recipe.CampfireRecipe;
import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.CraftTweaker;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenDocClass("mods.pyrotech.Campfire")
@ZenClass("mods.pyrotech.Campfire")
public class ZenCampfire {

  @ZenDocMethod(
      order = 1,
      args = {"name", "output", "input"}
  )
  @ZenMethod
  public static void addRecipe(String name, IItemStack output, IIngredient input) {

    CraftTweaker.LATE_ACTIONS.add(new AddRecipe(
        name,
        CraftTweakerMC.getItemStack(output),
        CraftTweakerMC.getIngredient(input)
    ));
  }

  @ZenDocMethod(
      order = 2,
      description = {
          "|Parameter|Description|\n" +
              "|---------|-----------|\n" +
              "|output|furnace recipes that have an output that matches any of the given ingredients will be disallowed|"
      },
      args = {"output"}
  )
  @ZenMethod
  public static void blacklistSmeltingRecipes(IIngredient[] output) {

    CraftTweaker.LATE_ACTIONS.add(new IAction() {

      @Override
      public void apply() {

        for (IIngredient ingredient : output) {
          CampfireRecipe.blacklistSmeltingRecipe(CraftTweakerMC.getIngredient(ingredient));
        }
      }

      @Override
      public String describe() {

        return "Blacklisting smelting recipes for campfire: " + CTLogHelper.getStackDescription(output);
      }
    });
  }

  @ZenDocMethod(
      order = 3,
      description = {
          "|Parameter|Description|\n" +
              "|---------|-----------|\n" +
              "|output|only furnace recipes that have an output that matches any of the given ingredients will be allowed|"
      },
      args = {"output"}
  )
  @ZenMethod
  public static void whitelistSmeltingRecipes(IIngredient[] output) {

    CraftTweaker.LATE_ACTIONS.add(new IAction() {

      @Override
      public void apply() {

        for (IIngredient ingredient : output) {
          CampfireRecipe.whitelistSmeltingRecipe(CraftTweakerMC.getIngredient(ingredient));
        }
      }

      @Override
      public String describe() {

        return "Whitelisting smelting recipes for campfire: " + CTLogHelper.getStackDescription(output);
      }
    });
  }

  @ZenDocMethod(
      order = 4,
      description = "Remove all recipes with the given recipe output.",
      args = {"output"}
  )
  @ZenMethod
  public static void removeRecipes(IIngredient output) {

    CraftTweaker.LATE_ACTIONS.add(new RemoveRecipe(CraftTweakerMC.getIngredient(output)));
  }

  public static class RemoveRecipe
      implements IAction {

    private final Ingredient output;

    public RemoveRecipe(Ingredient output) {

      this.output = output;
    }

    @Override
    public void apply() {

      CampfireRecipe.removeRecipes(this.output);
    }

    @Override
    public String describe() {

      return "Removing campfire recipes for " + this.output;
    }
  }

  public static class AddRecipe
      implements IAction {

    private final String name;
    private final ItemStack output;
    private final Ingredient input;

    public AddRecipe(
        String name,
        ItemStack output,
        Ingredient input
    ) {

      this.name = name;
      this.input = input;
      this.output = output;
    }

    @Override
    public void apply() {

      CampfireRecipe recipe = new CampfireRecipe(
          this.output,
          this.input
      );
      ModuleTechBasic.Registries.CAMPFIRE_RECIPE.register(recipe.setRegistryName(new ResourceLocation("crafttweaker", this.name)));
    }

    @Override
    public String describe() {

      return "Adding campfire recipe for " + this.output;
    }
  }

}