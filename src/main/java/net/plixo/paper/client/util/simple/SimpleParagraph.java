package net.plixo.paper.client.util.simple;

public class SimpleParagraph {
    public String value = "";

    public SimpleParagraph(String value) {
        this.value = value;
    }

    public SimpleParagraph() {

    }

    @Override
    public String toString() {
        return value;
    }
}
