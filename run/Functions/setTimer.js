import java.lang.reflect.Modifier;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;
import java.lang.reflect.Field;
import net.plixo.paper.client.avs.newVersion.CustomFunction;

public class setTimer extends CustomFunction {

    public void link() {
       float speed = input(0,20).floatValue();
        try {
            Field timerField = Minecraft.class.getDeclaredField("timer"); 
            timerField.setAccessible(true); 
            Timer timer = (Timer)timerField.get(mc); 
            Field speedField = Timer.class.getDeclaredField("tickLength"); 
            Field f = Field.class.getDeclaredField("modifiers"); 
            f.setAccessible(true); 
            f.set(speedField,f.getModifiers() & Modifier.FINAL); 
            speedField.setAccessible(true); 
            speedField.set(timer , 1000.0F / speed); 

        } catch (Exception e) { 
            e.printStackTrace(); 
            print("Cant find field"); 
        }
    }

}
