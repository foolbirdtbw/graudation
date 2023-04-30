package com.bowen.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bowen
 */
public class ThreadLocalUtil {
    private ThreadLocalUtil(){
    }
    private final static ThreadLocal<Map<String,Object>> THREAD_CONTEXT = ThreadLocal.withInitial(() -> new HashMap<>(1 << 3));

    public static void put(String key,Object data){
        THREAD_CONTEXT.get().put(key,data);
    }

    public static Object get(String key){
        return THREAD_CONTEXT.get().get(key);
    }

    public static void remove(String key){
        THREAD_CONTEXT.get().remove(key);
    }

    public static void clear(){
        THREAD_CONTEXT.get().clear();
    }

    public static void clearAll(){
        THREAD_CONTEXT.remove();
    }
}
