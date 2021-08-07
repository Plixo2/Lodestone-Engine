package net.plixo.lodestone.client.util;


import net.plixo.lodestone.client.visualscript.Function;

public class FunctionCursorHolder {

    public DraggedType type;
    public int id;
    public Function object;

    public FunctionCursorHolder(int id, DraggedType type, Function object) {
        this.object = object;
        this.id = id;
        this.type = type;
    }


    public boolean isLink() {
        return type == DraggedType.LINK;
    }

    public boolean isOutput() {
        return type == DraggedType.OUTPUT;
    }

    public Function getLink() {
        return object;
    }


    public enum DraggedType {
        LINK, OUTPUT, NONE;
    }

    @Override
    public String toString() {
        return "CursorObject{" +
                "type=" + type +
                ", id=" + id +
                ", object=" + object +
                '}';
    }
}
