package net.plixo.animation;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Job {

    Consumer<Float> floatConsumer;
    Supplier<Float> floatSupplier;
    float end , duration;
    float time = 0;
    public Job(Consumer<Float> floatConsumer, Supplier<Float> floatSupplier , float end , float duration) {
        this.floatConsumer = floatConsumer;
        this.floatSupplier = floatSupplier;
        this.end = end;
        this.duration = duration;
    }

    public boolean interpolate() {
        float start = floatSupplier.get();
        float diff = end - start;
        float value = diff * (time/duration);
        boolean isStopped = time >= duration;
        if(isStopped) {
            value = diff;
        }
        floatConsumer.accept(start + value);
        return isStopped;
    }

    public void addTime(float time) {
        this.time += time;
    }

}
