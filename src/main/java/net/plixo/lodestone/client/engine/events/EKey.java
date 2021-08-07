package net.plixo.lodestone.client.engine.events;

public class EKey extends Event {
    public int key;
    public boolean state;
    public String name;

    public EKey(int key, boolean state, String name) {
        this.key = key;
        this.state = state;
        this.name = name;
    }
}
