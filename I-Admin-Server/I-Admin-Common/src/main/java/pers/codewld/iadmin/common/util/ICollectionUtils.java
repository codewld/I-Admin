package pers.codewld.iadmin.common.util;

import java.util.*;

/**
 * 集合 工具类
 */
public class ICollectionUtils {

    /**
     *  去Null
     */
    public static <T> List<T> deNull(List<T> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     *  去Null
     */
    public static <T> Set<T> deNull(Set<T> set) {
        if (set == null) {
            set = new HashSet<>();
        }
        return set;
    }

    /**
     *  去Null
     */
    public static <T> Queue<T> deNull(Queue<T> queue) {
        if (queue == null) {
            queue = new LinkedList<>();
        }
        return queue;
    }

    /**
     *  去Null
     */
    public static <K, V> Map<K,V> deNull(Map<K,V> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        return map;
    }

}
