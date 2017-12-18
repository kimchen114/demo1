package com.example.ms_demo.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

@SuppressWarnings("unchecked")
public class Collections3 {
    
    /**
     * @author cjw
     * @param c 集合
     * @return true表示為空，false表示不為空
     * */
    public static <T> boolean isEmpty(Collection<T> c) {
        return c == null || c.isEmpty() ? true : false;
    }
    
    /**
     * @author cjw
     * @param c 集合
     * @return true表示不為空，false表示為空
     * */
    public static <T> boolean isNotEmpty(Collection<T> c) {
        return c == null || c.isEmpty() ? false : true;
    }
    
    /**
     * 提取集合中的对象的两个属性(通过Getter函数), 组合成Map.
     *
     * @param collection  来源集合.
     * @param keyPropertyName  要提取为Map中的Key值的属性名.
     * @param valuePropertyName  要提取为Map中的Value值的属性名.
     */
    public static <K, T, V> Map<K, V> extractToMap(final Collection<T> collection, final String keyPropertyName,
            final String valuePropertyName) {
        Map<K, V> map = new HashMap<K, V>(collection.size());
        try {
            for (T obj : collection) {
                map.put((K) PropertyUtils.getProperty(obj, keyPropertyName),
                        (V) PropertyUtils.getProperty(obj, valuePropertyName));
            }
        } catch (Exception e) {
        }
        return map;
    }
    
    /**
     * 提取集合中的对象的两个属性(通过Getter函数), 组合成Map.
     *
     * @param collection  来源集合.
     * @param keyPropertyName 要提取为Map中的Key值的属性名. -- > value self
     */
    public static <K, T> Map<K, T> extractToMap(final Collection<T> collection, final String keyPropertyName) {
        Map<K, T> map = new HashMap<K, T>(collection.size());
        try {
            for (T obj : collection) {
                map.put((K) PropertyUtils.getProperty(obj, keyPropertyName), obj);
            }
        } catch (Exception e) {
        }
        
        return map;
    }
    
    /**
     * 返回map<String,List<T>>; 根据传入的属性,返回一个map,key为属性值 value为传入的list中满足传入属性的list
     *
     * @param collection
     * @param keyPropertyName collection中的属性
     * @return
     */
    public static <K, T> Map<K, List<T>> extractToMapList(Collection<T> collection, String keyPropertyName) {
        Map<K, List<T>> map = new HashMap<K, List<T>>(collection.size());
        try {
            for (T obj : collection) {
                K key = (K) PropertyUtils.getProperty(obj, keyPropertyName);
                if (map.containsKey(key)) {
                    map.get(key).add(obj);
                    continue;
                }
                List<T> list = new ArrayList<>();
                list.add(obj);
                map.put(key, list);
            }
        } catch (Exception e) {
        }
        return map;
    }
    
    /**
     * 返回map<String,List<V>>; 根据传入的属性,返回一个map,key为属性值 value为obj.valueProperty
     *
     * @param collection
     * @param keyPropertyName  collection中的属性
     * @return
     */
    public static <K, V, T> Map<K, List<V>> extractToMapList(Collection<T> collection, String keyPropertyName,
            String valuePropertyName) {
        Map<K, List<V>> map = new HashMap<K, List<V>>(collection.size());
        try {
            for (T obj : collection) {
                K key = (K) PropertyUtils.getProperty(obj, keyPropertyName);
                V value = (V) PropertyUtils.getProperty(obj, valuePropertyName);
                if (map.containsKey(key)) {
                    map.get(key).add(value);
                    continue;
                }
                List<V> list = new ArrayList<>();
                list.add(value);
                map.put(key, list);
            }
        } catch (Exception e) {
        }
        return map;
    }
    
    /**
     * 转换Collection所有元素(通过toString())为String, 中间以 separator分隔。
     */
    public static <T> String convertToString(final Collection<T> collection, final String separator) {
        return StringUtils.join(collection, separator);
    }
    
}
