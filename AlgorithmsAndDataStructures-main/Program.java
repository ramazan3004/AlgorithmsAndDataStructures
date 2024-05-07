public class Program {

    public static void main(String[] args) {
        Employee employee1 = new Employee("AAAA", 33);
        System.out.println(employee1.hashCode());
        Employee employee2 = new Employee("AAAA", 41);
        System.out.println(employee2.hashCode());

        HashMap<String, String> hashMap1 = new HashMap<>(4);

        String oldValue;
        oldValue = hashMap1.put("+79007774431", "AAAAAA");
        oldValue = hashMap1.put("+79007774432", "BBBBBB");
        oldValue = hashMap1.put("+79007774431", "CCCCC1");

        oldValue = hashMap1.put("+79007774433", "CCCCC2");
        oldValue = hashMap1.put("+79007774434", "CCCCC3");
        oldValue = hashMap1.put("+79007774435", "CCCCC4");
        oldValue = hashMap1.put("+79007774436", "CCCCC5");
        oldValue = hashMap1.put("+79007774437", "CCCCC6");
        oldValue = hashMap1.put("+79007774438", "CCCCC7");
        oldValue = hashMap1.put("+79007774439", "CCCCC8");


        String res;
        res = hashMap1.get("+79007774435");
        res = hashMap1.get("+79107774431");

        oldValue = hashMap1.remove("+79007774435");
        oldValue = hashMap1.remove("+79007774435");

        /** Переписанный метод toString */
        System.out.println(hashMap1);

        
        for (HashMap<String, String>.Entity entity : hashMap1) {
            if (entity != null) {
                System.out.printf("Ключ: %s; Значение: %s\n", entity.key, entity.value);
            }
        }
        
        System.out.println();
        for (HashMap<String, String>.Entity entity : hashMap1) {
            if (entity != null) {
                System.out.printf("Ключ: %s; Значение: %s\n", entity.key, entity.value);
            }
        }
    }
}
