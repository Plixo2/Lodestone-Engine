package net.plixo.animation;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Animation {

    static CopyOnWriteArrayList<Job> jobs = new CopyOnWriteArrayList<>();

    static long lastMs = 0;
    public static void animate() {
        float delta = getDelta();
        jobs.removeIf(job -> {
            job.addTime(delta);
            return job.interpolate();
        });
        lastMs = System.currentTimeMillis();
    }

    public static void animate(Consumer<Float> setter, Supplier<Float> getter, float end, float time) {
        Job e = new Job(setter, getter, end, time);
        jobs.add(e);
    }


    static float getDelta() {
        return (System.currentTimeMillis() - lastMs)/1000f;
    }
}

