package net.plixo.paper.client.visualscript.functions;

import net.plixo.paper.client.util.Util;
import net.plixo.paper.client.visualscript.Function;

public class Print extends Function {
    @Override
    public void run() {
        pullInputs();
        Util.print("Output: " + input(0));
        execute();
    }

    @Override
    public void set() {
        set(1, 0, 1);
    }
}
