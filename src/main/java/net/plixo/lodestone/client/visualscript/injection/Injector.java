package net.plixo.lodestone.client.visualscript.injection;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperMethod;
import net.bytebuddy.implementation.bind.annotation.This;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Method;

public class Injector<O> {

    IInterceptor interceptor;

    public Injector(IInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    public O createInstance(O instance, int constructor, Object... args) throws ReflectiveOperationException {
        DynamicType.Builder<O> subclass = (DynamicType.Builder<O>) new ByteBuddy().subclass(instance.getClass());
        for (int i = 0; i < instance.getClass().getMethods().length - 9; i++) {
            Method method = instance.getClass().getMethods()[i];
            String methodName = method.getName();
            subclass = subclass.method(ElementMatchers.named(methodName)).intercept(MethodDelegation.to(new GenericInterceptor()));
        }
        Class<?> loaded = subclass.make().load(Injector.class.getClassLoader()).getLoaded();
        return (O) loaded.getConstructors()[constructor].newInstance(args);
    }

    public class GenericInterceptor {
        @RuntimeType
        public Object set(@This Object self, @SuperMethod Method superMethod, @AllArguments Object[] allArguments) {
            try {
                return interceptor.invoke(self, superMethod, allArguments);
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
            try {
                return superMethod.invoke(self, allArguments);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public interface IInterceptor {
        Object invoke(Object instance, Method method, Object[] objects) throws ReflectiveOperationException;
    }
}
