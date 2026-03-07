package ru.itis.dis403.lab2_01.di.config;

import ru.itis.dis403.lab2_01.di.annotation.Controller;
import ru.itis.dis403.lab2_01.di.annotation.GetMapping;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {

    private String scanPath = "ru.itis.dis403.lab2_01.di";

    private Map<Class<?>, Object> component = new HashMap<>();
    private Map<String, Method> handlerMethod = new HashMap<>();
    private Map<Method, Object> methodOwners = new HashMap<>();

    public Context() {
        scanComponent();
        initHandlerMethods();
    }

    private void scanComponent() {
        List<Class<?>> classes = PathScan.find(scanPath);

        int countClasses = classes.size();

        while (countClasses > 0) {
            objectNotFound:
            for (Class<?> c: classes) {
                if (component.get(c) != null) {
                    continue;
                }

                Constructor<?> constructor = c.getConstructors()[0];
                Class<?>[] types = constructor.getParameterTypes();
                Object[] args = new Object[types.length];

                for (int i = 0; i < args.length; i++) {
                    args[i] = component.get(types[i]);

                    if (args[i] == null) {
                        continue objectNotFound;
                    }
                }

                try {
                    Object o = constructor.newInstance(args);

                    component.put(c, o);
                    countClasses--;
                    System.out.println(c + " добавлен");

                } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void initHandlerMethods() {
        for (Object object: component.values()) {
            Class<?> clazz = object.getClass();

            if (clazz.isAnnotationPresent(Controller.class)) {
                for (Method method: clazz.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(GetMapping.class)) {

                        GetMapping mapping = method.getAnnotation(GetMapping.class);
                        String path = mapping.value();

                        handlerMethod.put(path, method);
                        methodOwners.put(method, object);
                    }
                }
            }
        }
    }

    public Object getComponent(Class clazz) {
        return component.get(clazz);
    }

    public Method getHandler(String path) {
        return handlerMethod.get(path);
    }

    public Object getMethodOwner(Method method) {
        return methodOwners.get(method);
    }

}

