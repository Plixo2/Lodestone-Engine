package net.plixo.paper.client.avs.newVersion;

public class CursorObject {

    public static CursorObject none = new CursorObject(0,DraggedType.NONE,null);
    public DraggedType type;
    public int id;
    public Object object;

    public CursorObject(int id, DraggedType type, Object object) {
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

    public nFunction getLink() {
        return ((nFunction) object);
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
