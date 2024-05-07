import java.util.Iterator;

public class HashMap<K, V> implements Iterable<HashMap.Entity> {

    private static final int INIT_BUCKET_COUNT = 16;
    private static final double LOAD_FACTOR = 0.5;
    
    
    private Bucket[] buckets;

    private int size;

    private static int countIterator;

    @Override
    public Iterator<HashMap.Entity> iterator() {
        countIterator = -1;
        return new HashMapIterator();
    }

    class HashMapIterator implements Iterator<HashMap.Entity> {

        @Override
        public boolean hasNext() {
            
            return countIterator < buckets.length;
        }

        @Override
        public HashMap.Entity next() {
            
            countIterator++;
            try {
                return buckets[countIterator].head.value;
            } catch (Exception e) {
                return null;
            }
        }

    }

    @Override
    public String toString() {
        String result = "";
        for (Bucket bucket : buckets) {
            if (bucket != null) {
                HashMap.Bucket.Node cuurNode = bucket.head;

                while (cuurNode != null) {
                    result += "Ключ: " + cuurNode.value.key + "; " +
                              "Значение: " + cuurNode.value.value + "\n";
                    
                    cuurNode = cuurNode.next;
                }
            }
        }
        return result;
    }

    /**
     * Элемент хеш0таблицы
     */
    class Entity {

        /**
         * Ключ
         */
        K key;

        /**
         * Значение элемента
         */
        V value;
    }

    /**
     * Бакет. Связаный список
     */
    class Bucket {
        
        /**
         * Указатель на главный элемент
         */
        Node head;

        /*
         * Узел балета (связанного списка)
         */
        class Node {

            /**
             * Указатель на следующий элемент связаного спсика
             */
            Node next;

            /**
             * Значнеие узла, указывающее на элемент хеш-таблицы
             */
            Entity value;
        }

        /**
         * Добавление сущности
         * @param entity
         * @return
         */
        public V add(Entity entity) {
            Node node = new Node();
            node.value = entity;

            if (head == null) {
                head = node;
                return null;
            }

            Node curNode = head;
            while (true) {
                if (curNode.value.key.equals(entity.key)) {
                    V buf = curNode.value.value;
                    curNode.value.value = entity.value;
                    return buf;
                }
                if (curNode.next != null) {
                    curNode = curNode.next;
                }
                else {
                    curNode.next = node;
                    return null;
                }
            }
        }

        /**
         * Получение значения по указанному ключу сущности
         * @param key
         * @return
         */
        public V get(K key) {
            Node node = head;
            while (node != null) {
                if (node.value.key.equals(key)) {
                    return node.value.value;
                }
                node = node.next;
            }
            return null;
        }

        /**
         * Удаление сущности по ключу
         * @param key
         * @return
         */
        public V remove(K key) {
            if (head == null) {
                return null;
            }
            if (head.value.key.equals(key)) {
                V buf = head.value.value;
                head = head.next;
                return buf;
            }

            Node node = head;
            while (node.next != null) {
                if (node.next.value.key.equals(key)) {
                    V buf = node.next.value.value;
                    node.next = node.next.next;
                    return buf;
                }
                node = node.next;
            }
            return null;
            
            
        }
    }

    /**
     * Вычичсление индекса сущности хеш-таблицы
     * @param key
     * @return
     */
    private int calculateBucketIndex(K key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }
    
    /**
     * Переопределение размера хеш-таблицы по вмещенных в нее сущностей
     */
    private void realculate() {
        size = 0;
        Bucket[] old = buckets;
        buckets = new HashMap.Bucket[old.length * 2];

        for (int i = 0; i < old.length; i++) {
            Bucket bucket = old[i];
            if (bucket != null) {
                Bucket.Node node = bucket.head;
                while (node != null) {
                    put(node.value.key, node.value.value);
                    node = node.next;
                }
            }
        }

    }

    /**
     * Добаление сущности
     * @param key
     * @param value
     * @return
     */
    public V put(K key, V value){
        if (size >= buckets.length * LOAD_FACTOR) {
            realculate();
        }
        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];

        if (bucket == null) {
            bucket = new Bucket();
            buckets[index] = bucket;
        }
        
        Entity entity = new Entity();
        entity.key = key;
        entity.value = value;

        V buf = buckets[index].add(entity);
        if (buf == null) size++;
        return buf;
    }

    /**
     * Получение значения по указанному ключу сущности
     * @param key
     * @return
     */
    public V get(K key) {
        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null) {
            return null;
        }
        return bucket.get(key);
    }

    /**
     * Удаление сущности по ключу
     * @param key
     * @return
     */
    public V remove(K key) {
        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null) {
            return null;
        }
        V buf = bucket.remove(key);
        if (buf != null) {
            size--;
        }
        return buf;
    }

    /**
     * Конструктор хеш-таблицы по умолчанию
     */
    public HashMap() {
        buckets = new HashMap.Bucket[INIT_BUCKET_COUNT];
    }

    /**
     * Конструктор с указанием начального размера хеш-таблицы
     * @param initSize
     */
    public HashMap(int initSize) {
        buckets = new HashMap.Bucket[initSize];
    }
}
