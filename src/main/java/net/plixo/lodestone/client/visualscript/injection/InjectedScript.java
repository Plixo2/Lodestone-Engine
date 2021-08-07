package net.plixo.lodestone.client.visualscript.injection;

import net.plixo.lodestone.client.visualscript.Function;
import net.plixo.lodestone.client.visualscript.functions.FMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InjectedScript<T> {

    Class<T> type;
    Map<String, FMethod> functionMap = new HashMap<>();

    public InjectedScript(Class<T> type) {
        this.type = type;
    }
    public void registerMethods(List<Function> functions) {
        for (Function function : functions) {
            if(function instanceof FMethod) {
                FMethod javaMethod = (FMethod) function;
                functionMap.put(javaMethod.getMethod().getName(),javaMethod);
            }
        }
    }

    public T create(){
        try {
            T object = (T) type.getConstructors()[0].newInstance();
            Injector<T> injector = new Injector<>(((instance, method, objects) -> {
                String name = method.getName().split("\\$")[0];
                FMethod function = functionMap.get(name);
                if (function != null) {
                    try {
                        function.runWith(objects);
                        if (function.getInput().length > 0 && function.hasInput(0)) {
                            Object input = function.input(0);
                            return input;
                        } else {
                            return method.invoke(instance, objects);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                   // System.out.println(name + " is has no function");
                }

                return method.invoke(instance, objects);
            }));
            object = injector.createInstance(object, 0);

            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
