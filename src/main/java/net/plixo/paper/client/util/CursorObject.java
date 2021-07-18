package net.plixo.paper.client.util;

import net.plixo.paper.client.engine.ecs.Resource;

public class CursorObject<O> {
    O value;

    public CursorObject(O value) {
        this.value = value;
    }

    public Resource<O> getAsResource() {
        if (value == null) {
            return null;
        } else if (value instanceof Resource<?>) {
            return (Resource<O>) value;
        } else {
            return new Resource<>(value.getClass().getSimpleName(), value);
        }
    }

    public boolean isClass(Class<?> object) {
        if (value == null) {
            return false;
        }
        return value.getClass().isAssignableFrom(object);
    }

    public <A> A getAs(Class<? extends A> object) {
        if (!isClass(object)) {
            return null;
        }
        return (A) value;
    }
}
