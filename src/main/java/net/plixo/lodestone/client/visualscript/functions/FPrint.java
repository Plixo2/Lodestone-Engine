package net.plixo.lodestone.client.visualscript.functions;

import net.plixo.lodestone.client.util.UMath;
import net.plixo.lodestone.client.visualscript.Function;

import java.util.Arrays;

public class FPrint extends Function {
    @Override
    public void run() {
        pullInputs();
        Object input = input(0);
        if(input != null && input.getClass().isArray()) {
            Object[] o = (Object[]) input;
            UMath.print("Output: " + Arrays.toString(o));
        } else {
            UMath.print("Output: " + input);
        }
        execute();
    }

    @Override
    public void set() {
        setInputs("Object");
        setOutputs();
        setLinks("");
    }
}
