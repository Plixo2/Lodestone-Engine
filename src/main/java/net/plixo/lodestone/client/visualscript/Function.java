package net.plixo.lodestone.client.visualscript;

import net.minecraft.client.Minecraft;
import net.plixo.lodestone.client.engine.core.Resource;
import net.plixo.lodestone.client.ui.visualscript.UIFunction;


import java.util.UUID;

public abstract class Function {
    public static Minecraft mc = Minecraft.getInstance();

    private UUID id;

    public Function() {
        id = UUID.randomUUID();
    }

    private Output[] output;
    private Output[] input;
    private Resource<?>[] settings = new Resource[0];
    private Function[] links;

    private String[] inputNames;
    private String[] outputNames;
    private String[] linkNames;

    private String customDisplayString = "";

    private UIFunction ui;

    public void calculate() {
    }
    public void run() {
    }
    public abstract void set();

    public void output(int id, Object o) {
        output[id].value = o;
    }

    public Number input(int id, Number Default) {
        if (input[id] == null) {
            return Default;
        }
        return input[id].getAsNumber(Default);
    }

    public Boolean input(int id, Boolean Default) {
        if (input[id] == null) {
            return Default;
        }
        return input[id].getAsBoolean(Default);
    }

    public Object input(int id) {
        if (input[id] == null) {
            return null;
        }
        return input[id].get();
    }

    public void pullInputs() {
        for (Output output1 : input) {
            if (output1 != null) {
                output1.calculate();
            }
        }
    }


    public void execute() {
        for (Function link : links) {
            if (link != null) {
                link.run();
            }
        }
    }

    public void execute(int id) {
        if (links[id] != null) {
            links[id].run();
        }
    }

    public boolean hasInput(int id) {
        return input[id] != null;
    }

    public boolean hasInput() {
        for (Output value : input) {
            if (value == null) {
                return false;
            }
        }
        return true;
    }

    public void setInputs(String... names) {
        setInputNames(names);
        input = new Output[names.length];
    }

    public void setOutputs(String... names) {
        setOutputNames(names);
        output = new Output[names.length];
        for (int i = 0; i < output.length; i++) {
            output[i] = new Output(this);
        }
    }

    public void setLinks(String... names) {
        setLinkNames(names);
        this.links = new Function[names.length];
    }

    public String getIDString() {
        return this.getClass().getName();
    }

    public String getDisplayName() {
        return this.getClass().getSimpleName().substring(1);
    }

    public String getCustomDisplayString() {
        if(customDisplayString.isEmpty()) {
            customDisplayString = getDisplayName();
        }
        return customDisplayString;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Output[] getOutput() {
        return output;
    }

    public void setOutput(Output[] output) {
        this.output = output;
    }

    public Output[] getInput() {
        return input;
    }

    public void setInput(Output[] input) {
        this.input = input;
    }

    public Resource<?>[] getSettings() {
        return settings;
    }

    public void setSettings(Resource<?>... settings) {
        this.settings = settings;
    }

    public Function[] getLinks() {
        return links;
    }

    public void setLinks(Function[] links) {
        this.links = links;
    }

    public String[] getInputNames() {
        return inputNames;
    }

    public void setInputNames(String[] inputNames) {
        this.inputNames = inputNames;
    }

    public String[] getOutputNames() {
        return outputNames;
    }

    public void setOutputNames(String[] outputNames) {
        this.outputNames = outputNames;
    }

    public String[] getLinkNames() {
        return linkNames;
    }

    public void setLinkNames(String[] linkNames) {
        this.linkNames = linkNames;
    }

    public void setCustomDisplayString(String customDisplayString) {
        this.customDisplayString = customDisplayString;
    }

    public UIFunction getUI() {
        return ui;
    }

    public void setUI(UIFunction ui) {
        this.ui = ui;
    }





    public static class Output {
        public Function function;
        public Object value = null;

        public Output(Function function) {
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
            return value instanceof Number;
        }
    }

    public static boolean NumberToBoolean(Number b) {
        return b.doubleValue() >= 0.5;
    }
    public static Number BooleanToNumber(boolean b) {
        return b ? 1 : 0;
    }

}
