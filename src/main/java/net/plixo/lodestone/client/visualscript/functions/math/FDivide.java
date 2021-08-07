package net.plixo.lodestone.client.visualscript.functions.math;

import net.plixo.lodestone.client.visualscript.Function;

public class FDivide extends Function {

    @Override
    public void calculate() {
        pullInputs();
        if (hasInput()) {
            Number obj1 = input(0, 0);
            Number obj2 = input(1, 0);

            output(0, dNumbers(obj1, obj2));
        }
    }

    public static Number dNumbers(Number a, Number b) {
        if (a instanceof Double || b instanceof Double) {
            return a.doubleValue() / b.doubleValue();
        } else if (a instanceof Float || b instanceof Float) {
            return a.floatValue() / b.floatValue();
        } else if (a instanceof Long || b instanceof Long) {
            return a.longValue() / b.longValue();
        } else {
            return a.intValue() / b.intValue();
        }
    }

    @Override
    public void set() {
        setInputs("Dividend", "Divisor");
        setOutputs("Quotient");
        setLinks();
    }
}
