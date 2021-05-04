package net.plixo.paper.client.avs.newVersion.functions;

import net.minecraft.client.Minecraft;
import net.plixo.paper.client.avs.newVersion.nFunction;
import net.plixo.paper.client.util.KeyboardUtil;

public class getGround extends nFunction {

    @Override
    public void calculate() {
        pullInputs();
        output(0,mc.player.isOnGround());
      //  if(hasInput(0))
      //  output(0,KeyboardUtil.isKeyDown(input(0,0).intValue()));

    }

    @Override
    public void set() {
        set(0,1,0);
    }
}
