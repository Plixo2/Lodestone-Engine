package net.plixo.paper.client.visualscript.functions.events;

public class KeyEvent extends Event {

    @Override
    public void set() {
        setInputs();
        setOutputs("Key","State","Name");
        setLinks("Event");
    }

    public void runWith(Object... objects) {
        for (int i = 0; i < objects.length; i++) {
            output(i,objects[i]);
        }
        execute();
    }
}
