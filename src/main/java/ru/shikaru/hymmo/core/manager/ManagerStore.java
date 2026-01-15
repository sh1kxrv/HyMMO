package ru.shikaru.hymmo.core.manager;

import ru.shikaru.hymmo.core.api.IManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ManagerStore {
    private static final Map<Class<? extends IManager>, IManager> MANAGERS =
            new ConcurrentHashMap<>();

    private ManagerStore() {
        throw new IllegalStateException("Utility class");
    }

    @SuppressWarnings("unchecked")
    public static <T extends IManager> T get(Class<T> managerClass) {
        return (T) MANAGERS.get(managerClass);
    }

    public static void remove(Class<? extends IManager> managerClass) {
        IManager manager = MANAGERS.remove(managerClass);
        if (manager != null) {
            manager.onDestroy();
        }
    }

    public static <T extends IManager> void add(T manager) {
        Class<? extends IManager> clazz = manager.getClass();

        IManager previous = MANAGERS.putIfAbsent(clazz, manager);
        if (previous != null) {
            throw new IllegalStateException(
                    "Manager already registered: " + clazz.getName()
            );
        }
    }
}
