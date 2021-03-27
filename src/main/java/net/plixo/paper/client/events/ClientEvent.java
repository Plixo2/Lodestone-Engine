package net.plixo.paper.client.events;

public class ClientEvent {

    public static class StopEvent extends ClientEvent {
        public static StopEvent event = new StopEvent();
    }

    public static class InitEvent extends ClientEvent {
        public static InitEvent event = new InitEvent();
    }

    public static class TickEvent extends ClientEvent {
        public static TickEvent event = new TickEvent();
    }

    public static class KeyEvent extends ClientEvent {
        public int key;
        public boolean state;
        public String name;
        public KeyEvent(int key, boolean state,String name) {
            this.key = key;
            this.state = state;
            this.name = name;
        }
    }
}
