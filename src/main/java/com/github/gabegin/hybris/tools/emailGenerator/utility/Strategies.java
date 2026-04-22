package com.github.gabegin.hybris.tools.emailGenerator.utility;

import com.github.gabegin.hybris.tools.emailGenerator.traversal.safe.SafeMethod;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;
import java.util.ServiceLoader;

public class Strategies {
    @SuppressWarnings("unchecked")
    public static <S> S tryWith(final Class<S> strategyClass) {
        return (S) Proxy.newProxyInstance(
            strategyClass.getClassLoader(),
            new Class<?>[] { strategyClass },
            (proxy, method, arguments) -> tryStrategies(strategyClass, method, arguments)
        );
    }

    private static Object tryStrategies(final Class<?> strategyClass, final Method method, final Object[] arguments) {
        return ServiceLoader.load(strategyClass, strategyClass.getClassLoader()).stream()
            .map(ServiceLoader.Provider::get)
            .map((strategy) -> new SafeMethod(method, strategy).invoke(arguments))
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
    }
}
