package net.plixo.paper.client.avs.newVersion;

public class If extends nFunction {
    @Override
    public void run() {
        pullInputs();
        if(hasInput(0)) {
            boolean state = input(0, false);
            execute(state ? 0 : 1);
        }
    }

    @Override
    public void set() {
        set(1, 0, 2);
    }
}
