import java.util.*;
import java.util.stream.Collectors;

public class Sample {

    public static void main(String[] args) {
//        List<Product> products = Arrays.asList(
//                new Product("Laptop", "Electronics", 999.99),
//                new Product("Smartphone", "Electronics", 699.99),
//                new Product("Headphones", "Accessories", 199.99),
//                new Product("Mouse", "Accessories", 49.99),
//                new Product("Monitor", "Electronics", 299.99),
//                new Product("Charger", null, 29.99)
//        );
//
//        Map<String, List<String>> map = new HashMap<>();
//        products
//                .stream()
//                .forEach(product -> {
//                    List<String> arr = map.get(product.getCategory());
//                    if (arr == null) {
//                        arr = new ArrayList<>();
//                    }
//                    arr.add(product.getName());
//                    map.put(product.getCategory(), arr);
//                });
//
//        map.forEach((k, v) -> {
//            System.out.println(k + " - " + v);
//        });
//
//
        List<Person> people = new ArrayList<>();
        people.add(new Person("Bob", 25));
        people.add(new Person("Charlie", 35));
        people.add(new Person("Edward", 30));
        people.add(new Person("Alice", 25));
        people.add(new Person("Ace", 25));

//        people.sort(Comparator.comparing(Person::getAge).thenComparing(Person::getName));
//
//        people.forEach(p -> System.out.println(p.getName() + " - " + p.getAge()));
//
//
        List<String> stringList = new ArrayList<String>();

        stringList.add("Jack");
        stringList.add("Miel");
        stringList.add("Abie");

//        Optional<String> strs = stringList.stream()
//                .reduce((val, val2) -> val + val2);
//        System.out.println(strs.get());;

//        List<Integer> n = Arrays.asList(4,32,2,1);
//
//        Optional<Integer> min = n.stream()
//                .min(Integer::compareTo);
//
//        Optional<String> min2 = stringList.stream()
//                .min(String::compareTo);
//
//        Optional<Integer> sum = n.stream().reduce((total, i) -> total + i);
//        System.out.println(sum.get());

//        List<String> nStr = Arrays.asList("2", "3", "5", "7", "8", "9", "10");
//
//        List<Integer> mappednStr = nStr.stream()
//                .map(Integer::parseInt)
//                .collect(Collectors.toList());
//
//        System.out.println(nStr);
//        System.out.println(mappednStr.toString());

//        System.out.println(min.get());
//        System.out.println(min2.get());
//
        List<Person> sortedPeople = people.stream()
                .sorted(Comparator.comparing(Person::getAge).thenComparing(Person::getName))
                .collect(Collectors.toList());

        System.out.println(sortedPeople.toString());
    }



}
