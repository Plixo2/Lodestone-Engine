package net.plixo.paper.client.visualscript;

public class FunctionCursorHolder {

    public static FunctionCursorHolder none = new FunctionCursorHolder(0,DraggedType.NONE,null);
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
        return ((Function) object);
    }


    @Override
    public String toString() {
        return "CursorObject{" +
                "type=" + type +
                ", id=" + id +
                ", object=" + object +
                '}';
    }

    public enum DraggedType {
        LINK, OUTPUT,NONE;
    }
}
