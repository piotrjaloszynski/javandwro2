package pl.jwrabel.javandwro2.cars.java8;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;

/**
 * Created by jakubwrabel on 18.03.2017.
 */
public class Streams {
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(3);

		List<Person> personList = new ArrayList<>();
		personList.add(new Person("Adam", "Nowak", "Wrocław"));
		personList.add(new Person("Adam", "Kowalski", "Warszawa"));
		personList.add(new Person("Jerzy", "Polański", "Warszawa"));
		personList.add(new Person("Piotr", "Mickiewicz", "Sosnowiec"));
		personList.add(new Person("Jan", "Kowalski", "Wrocław"));

//
		// jeden argument, jedna operacja w metodzie
		list.stream().forEach(elem -> System.out.println(elem));

		for (Integer elem : list) {
			System.out.println(elem);
		}

		// 2 lub więcej parametrów -> musimy użyć nawiasów (x, y)
		list.stream().sorted((x, y) -> 0);

		list.stream().sorted(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return 0;
			}
		});


		// 3. więcej operacji w metodzie - po strzałce blok {}
		list.stream().forEach(x -> {
			System.out.println(x);
			System.out.println("Wypisałem kolejny element");
		});

		// 4. więcej operacji, metoda zwraca "Coś"
		list.stream().sorted((x, y) -> {
			System.out.println(x);
			return 0;
		});

		// 5. referencja metody
		list.stream().forEach(System.out::println);
		list.stream().forEach(x -> System.out.println(x));


		// FILTROWANIE -> zwraca stream
		list.stream().filter(x -> x < 2).forEach(x -> System.out.println(x));


		// CZY KTÓRYKOLWIEK ELEMENT SPEŁNIA WARUNEK -> zwraca boolean
		boolean matches = list.stream().anyMatch(x -> x == 2);
		// CZY ŻADEN NIE SPEŁNIA WARUNKU -> zwraca boolean
		boolean noZero = list.stream().noneMatch(x -> x == 0);
		// CZY WSZYSTKIE SPEŁNIAJĄ WARUNEK
		boolean allMatch = list.stream().allMatch(x -> x < 10);

		// ZLICZ WSZYSTKIE ELEMENTY, zwraca long
		long even = list.stream().filter(x -> x % 2 == 0).count();

		// PEEK -> FOR-EACH zwracający strumień
		list.stream()
				.peek(x -> System.out.println("WSZYSTKIE: " + x))
				.filter(x -> x % 2 == 0)
				.forEach(x -> System.out.println("Parzyste: " + x));

		// SORTOWANIE
		list.stream().sorted().forEach(x -> System.out.println(x));
		// SORTOWANIE, PRZYJMUJE COMPARATOR
		list.stream().sorted((x, y) -> {
			if (x > y) {
				return 1;
			} else if (x == y) {
				return 0;
			}
			return -1;

		}).forEach(x -> System.out.println(x));

		list.stream().sorted((x, y) -> x > y ? 1 : (x == y) ? 0 : -1).forEach(x -> System.out.println(x));


		// MAPOWANIE -> ZAMIANA ELEMENTÓW
		list.stream().map(x -> x * x).forEach(x -> System.out.println(x));
		list.stream().map(x -> "Number: " + x).forEach(x -> System.out.println(x));

		// MAX, MIN
		list.stream().max((x, y) -> x > y ? 1 : (x == y) ? 0 : -1);
		list.stream().min((x, y) -> x > y ? 1 : (x == y) ? 0 : -1);

		// COLLECTORS
		// toList
		List<Integer> collect = list.stream().filter(x -> x < 10).collect(toList());
		// toSet
		Set<Integer> collect1 = list.stream().filter(x -> x < 10).collect(Collectors.toSet());
		// joining (Tylko dla Stream<String>)
		String result = list.stream().map(value -> "" + value).collect(Collectors.joining(" "));

		String result2 = list.stream()
				.map(value -> "" + value)
				.collect(Collectors.joining(" ", "PREFIX", "SUFFIX"));
		System.out.println(result);

		// partitioningBy
		Map<Boolean, List<Person>> adam = personList.stream()
				.collect(partitioningBy(p -> p.getFirstName().equals("Adam")));
		List<Person> people = adam.get(false);// nie spełniają warunku
		List<Person> peopleAdams = adam.get(true);// spełniają warunek

		// groupingBy
		Map<String, List<Person>> firstNamesMap = personList.stream().collect(groupingBy(p -> p.getFirstName()));
		List<Person> adam1 = firstNamesMap.get("Adam");
		List<Person> piotr = firstNamesMap.get("Piotr");

		Set<Map.Entry<String, List<Person>>> entries = firstNamesMap.entrySet();

		for(Map.Entry<String, List<Person>> entry : firstNamesMap.entrySet()){
			System.out.println("MAP KEY: " + entry.getKey());
//			System.out.println("MAP VALUE: " + entry.getValue());

			List<Person> value = entry.getValue();
			for (Person person : value) {
				System.out.println(person);
			}
		}

		Set<String> keys = firstNamesMap.keySet();
		Collection<List<Person>> values = firstNamesMap.values();


		// STRUMIENIE RÓWNOLEGŁE (WIELOWĄTKOWE)
		list.parallelStream().forEach(x -> System.out.println(x));

		// IntStream
		IntStream.range(2, 30).forEach(x -> System.out.println(x));
		// DoubleStream
		DoubleStream.of(1, 2, 3d);


		// ZAMIANA TABLICY NA STRUMIEN
		int[] array = {1, 2, 3, 4, 5};
		Arrays.stream(array).forEach(elem -> System.out.println(elem));

		// ZAMIANA NA INT STREAM
		list.stream().mapToInt(value -> value);
		// ZAMIANA NA DOUBLE STREAM
		list.stream().mapToDouble(value -> value);

		// SUMMARY STATISTICS (INT STREAM, DOUBLE STREAM)
		IntSummaryStatistics intSummaryStatistics = list.stream().mapToInt(value -> value).summaryStatistics();
		double average = intSummaryStatistics.getAverage();
		int max = intSummaryStatistics.getMax();




		int reduce = IntStream.range(0, 100).reduce(0, (sumR, x) -> sumR += x);
//		list.stream().flatMap()

		// findFirst
	}
}
