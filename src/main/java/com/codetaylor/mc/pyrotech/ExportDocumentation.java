package com.codetaylor.mc.pyrotech;

import com.codetaylor.mc.athenaeum.tools.ZenDocExporter;
import com.codetaylor.mc.pyrotech.modules.tech.basic.plugin.crafttweaker.*;
import com.codetaylor.mc.pyrotech.modules.tech.bloomery.plugin.crafttweaker.ZenBloomery;
import com.codetaylor.mc.pyrotech.modules.tech.machine.plugin.crafttweaker.ZenCrucibleStone;
import com.codetaylor.mc.pyrotech.modules.tech.machine.plugin.crafttweaker.ZenKilnStone;
import com.codetaylor.mc.pyrotech.modules.tech.machine.plugin.crafttweaker.ZenMillStone;
import com.codetaylor.mc.pyrotech.modules.tech.machine.plugin.crafttweaker.ZenOvenStone;
import com.codetaylor.mc.pyrotech.modules.tech.refractory.plugin.crafttweaker.ZenBurn;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExportDocumentation {

  public static void main(String[] args) {

    String targetPath = "docs/zs/";
    Class[] classes = {
        ZenBloomery.class,
        ZenBurn.class,
        ZenCampfire.class,
        ZenChoppingBlock.class,
        ZenCompactingBin.class,
        ZenCrucibleStone.class,
        ZenDryingRack.class,
        ZenGraniteAnvil.class,
        ZenKilnPit.class,
        ZenKilnStone.class,
        ZenMillStone.class,
        ZenOvenStone.class,
        ZenSoakingPot.class,
        ZenWorktable.class
    };

    ZenDocExporter export = new ZenDocExporter();
    Path path = Paths.get(targetPath);

    try {
      Files.createDirectories(path);
      export.export(path, classes);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
