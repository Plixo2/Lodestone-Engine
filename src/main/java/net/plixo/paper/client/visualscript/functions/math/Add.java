package net.plixo.paper.client.visualscript.functions.math;

import net.plixo.paper.client.visualscript.Function;

public class Add extends Function {

    @Override
    public void calculate() {
        pullInputs();
        if(hasInputs()) {
            Object obj1 = input(0);
            Object obj2 = input(1);
            if(obj1 instanceof Number && obj2 instanceof Number) {
                output(0,addNumbers((Number) obj1,(Number) obj2));
            }
            else if(obj1 instanceof String && obj2 instanceof String) {
                output(0,(String)obj1 + (String) obj2);
            } else {
                output(0,String.valueOf(obj1) +  String.valueOf(obj2));
            }
        }
    }
    public static Number addNumbers(Number a, Number b) {
        if(a instanceof Double || b instanceof Double) {
            return a.doubleValue() + b.doubleValue();
        } else if(a instanceof Float || b instanceof Float) {
            return a.floatValue() + b.floatValue();
        } else if(a instanceof Long || b instanceof Long) {
            return a.longValue() + b.longValue();
        } else {
            return a.intValue() + b.intValue();
        }
    }

    @Override
    public void set() {
        set(2,1,0);
    }
}
