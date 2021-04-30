package net.plixo.paper.client.avs.newVersion;

import net.minecraft.client.Minecraft;
import net.plixo.paper.client.engine.ecs.Resource;

import java.util.Random;
import java.util.UUID;

public abstract class nFunction {
    public static Random random = new Random();
    public static Minecraft mc = Minecraft.getInstance();

    public UUID id;

    public nFunction() {
        id = UUID.randomUUID();
    }

    Output[] output;
    Output[] input;
    Resource[] settings = new Resource[0];
    nFunction[] links;

    public nUIFunction ui;

    public void calculate() {
    }

    public abstract void set();

    public void run() {
    }

    protected void output(int id, Object o) {
        output[id].value = o;
    }

    protected Number input(int id, Number Default) {
        if (input[id] == null) {
            return Default;
        }
        return input[id].getAsNumber(Default);
    }

    protected Boolean input(int id, Boolean Default) {
        if (input[id] == null) {
            return Default;
        }
        return input[id].getAsBoolean(Default);
    }

    protected Object input(int id) {
        if (input[id] == null) {
            return null;
        }
        return input[id].get();
    }

    protected void execute() {
        for (int i = 0; i < links.length; i++) {
            if (links[i] != null) {
                links[i].run();
            }
        }
    }

    protected boolean execute(int id) {
        if (links[id] != null) {
            links[id].run();
            return true;
        }
        return false;
    }

    protected void set(int inputs, int outputs, int links) {
        this.links = new nFunction[links];
        input = new Output[inputs];
        output = new Output[outputs];
        for (int i = 0; i < output.length; i++) {
            output[i] = new Output(this);
        }
    }

    protected void custom(Resource... res) {
        settings = res;
    }

    protected Resource setting(int id) {
        return settings[id];
    }


    protected void pullInputs() {
        for (Output output1 : input) {
            if (output1 != null) {
                output1.calculate();
            }
        }
    }

    protected boolean hasInput(int id) {
        return input[id] != null;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public static class Output {
        public nFunction function;
        public Object value = null;

        public Output(nFunction function) {
            this.function = function;
        }

        public void calculate() {
            function.calculate();
        }

        public boolean getAsBoolean(Boolean Default) {
            if (isBoolean()) {
                return (Boolean) value;
            } else if (isNumber()) {
                return NumberToBoolean((Number) value);
            }
            return Default;
        }

        public Number getAsNumber(Number Default) {
            if (isNumber()) {
                return (Number) value;
            } else if (isBoolean()) {
                return BooleanToNumber((Boolean) value);
            }
            return Default;
        }

        public Object get() {
            return value;
        }

        public boolean isBoolean() {
            return value instanceof Boolean;
        }

        public boolean isNumber() {
            return value instanceof Boolean;
        }
    }

    public static boolean NumberToBoolean(Number b) {
        return b.doubleValue() >= 0.5;
    }

    public static Number BooleanToNumber(boolean b) {
        return b ? 1 : 0;
    }

}
