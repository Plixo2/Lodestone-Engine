package net.plixo.lodestone.client.visualscript.injection.classes;

public class CModule {

    boolean enabled = false;
    int key = -1;

    public CModule() {
    }



    public void setKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    public void startEvent() {
    }

    public void stopEvent() {
    }

    public void tickEvent() {
    }

    public void keyEvent(int key, boolean state, String name) {
        if (key == getKey() && state) {
            enabled = !enabled;
        }
    }
}
