package net.plixo.paper.client.avs.newVersion.test;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.Timer;
import net.minecraft.util.math.vector.Vector3d;
import net.plixo.paper.client.avs.newVersion.nFunction;
import net.plixo.paper.client.ui.GUI.*;
import net.plixo.paper.client.ui.elements.*;
import net.plixo.paper.client.util.Util;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class GUITest extends nFunction {

    @Override
    public void run() {
        pullInputs();
        GUICanvas canvas = new GUICanvas() {
            @Override
            protected void init() {
                UISpinner spinner = new UISpinner();
                spinner.setNumber(1337);
                spinner.setDimensions(width/2-50,height/2 + 30,100,20);
                canvas.add(spinner);
            }
        };

        UIButton button = new UIButton() {
            public void actionPerformed() {
                super.actionPerformed();
            }
        };
        UITextbox box = new UITextbox();
        box.getText();



        try {
            float speed = 20;
            Field timerField = Minecraft.class.getDeclaredField("timer");
            timerField.setAccessible(true);
            Timer timer = (Timer)timerField.get(mc);
            Field speedField = Timer.class.getField("tickLength");

            Field f = Field.class.getField("modifiers");
            f.set(speedField,f.getModifiers() & Modifier.FINAL);
            speedField.set(timer , 1000.0F / speed);

           // File file

        } catch (Exception e) {
            e.printStackTrace();
        }


        // Timer timer = ;
        /*
        try {
           // Timer timer = (Timer) obj ;
          //  mc.getClass().
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        */

        LivingEntity livingEntity;
        for (Entity allEntity : mc.world.getAllEntities()) {
          //  allEntity.getPositionVec()
            if(allEntity instanceof LivingEntity) {
               // if(allEntity.getPositionVec().distanceTo(mc.player.getPositionVec()) < range) {
                    livingEntity = (LivingEntity) allEntity;
               // Vector3d pos = (Vector3d) obj;
             //   float yaw = Math.atan2(pos.x , pos.z) * 57.29f;
                Vector3d pos = livingEntity.getPositionVec();
                //mc.player.getEyeHeight()
                //}
            }
        }


        UIArray sideBar = new UIArray();
        sideBar.setDimensions(0,0,40,40);


        for(int index = 0; index < 40; index++) {
            Util.print("Hello");
            UIButton smallButton = new UIButton();
            smallButton.setDimensions(0,0,40,10);
            smallButton.setDisplayName("Hello" + Math.random());
            sideBar.add(smallButton);
        }



        mc.displayGuiScreen(canvas);
        execute();
    }

    @Override
    public void set() {
        set(0,0,1);
    }
}
