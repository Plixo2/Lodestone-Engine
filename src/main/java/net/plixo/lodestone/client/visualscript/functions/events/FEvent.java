package net.plixo.lodestone.client.visualscript.functions.events;
import net.plixo.lodestone.client.visualscript.Function;

public abstract class FEvent extends Function {

    public void runWith(Object... objects) {
        for (int i = 0; i < objects.length; i++) {
            output(i,objects[i]);
        }
        pullInputs();
        execute();
    }

}
