package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserMealsUtil {
	public static void main(String[] args) {
		List<UserMeal> meals = Arrays.asList(
				new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
				new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
				new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
				new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
				new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
				new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
				new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
		);
		List<UserMealWithExcess> userMealWithExceeds = getFilteredWithExceededStreamBetter(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
		userMealWithExceeds.forEach(System.out::println);
	}

	private static List<UserMealWithExcess> getFilteredWithExceeded(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
		List<UserMeal> filterMeal = new ArrayList<>(meals.size());
		int exceed = 0;
		for (UserMeal meal : meals) {
			if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
				filterMeal.add(meal);
				exceed += meal.getCalories();
			}
		}

		List<UserMealWithExcess> mealWithExceeds = new ArrayList<>(filterMeal.size());
		boolean isExceed = exceed > caloriesPerDay;
		for (UserMeal meal : filterMeal) {
			mealWithExceeds.add(new UserMealWithExcess(
					meal.getDateTime(), meal.getDescription(), meal.getCalories(), isExceed));
		}

		return mealWithExceeds;
	}

	private static List<UserMealWithExcess> getFilteredWithExceededOld(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
		Map<LocalDate, Integer> caloriesSumPerDate = new HashMap<>();
		for (UserMeal meal : meals) {
			LocalDate mealDate = meal.getDateTime().toLocalDate();
			caloriesSumPerDate.put(mealDate, caloriesSumPerDate.getOrDefault(mealDate, 0) + meal.getCalories());
		}

		List<UserMealWithExcess> mealExceeds = new ArrayList<>();
		for (UserMeal meal : meals) {
			LocalDateTime dateTime = meal.getDateTime();
			if (TimeUtil.isBetweenHalfOpen(dateTime.toLocalTime(), startTime, endTime)) {
				mealExceeds.add(new UserMealWithExcess(dateTime, meal.getDescription(), meal.getCalories(),
						caloriesSumPerDate.get(dateTime.toLocalDate()) > caloriesPerDay));
			}
		}

		return mealExceeds;
	}

	private static List<UserMealWithExcess> getFilteredWithExceededStream(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
		Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
				.collect(Collectors.groupingBy(
						um -> um.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

		return meals.stream()
				.filter(um -> TimeUtil.isBetweenHalfOpen(um.getDateTime().toLocalTime(), startTime, endTime))
				.map(um -> new UserMealWithExcess(um.getDateTime(), um.getDescription(), um.getCalories(),
						caloriesSumByDate.get(um.getDateTime().toLocalDate()) > caloriesPerDay))
				.collect(Collectors.toList());
	}

	private static List<UserMealWithExcess> getFilteredWithExceededStreamBetter(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
		final class Aggregate {
			private final List<UserMeal> dailyMeals = new ArrayList<>();
			private int dailySumOfCalories;

			private void accumulate(UserMeal meal) {
				dailySumOfCalories += meal.getCalories();
				if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
					dailyMeals.add(meal);
				}
			}

			private Aggregate combine(Aggregate that) {
				this.dailySumOfCalories += that.dailySumOfCalories;
				this.dailyMeals.addAll(that.dailyMeals);
				return this;
			}

			private Stream<UserMealWithExcess> finisher() {
				final boolean exceed = dailySumOfCalories > caloriesPerDay;
				return meals.stream().map(meal -> createWithExceed(meal, exceed));
			}
		}

		Collection<Stream<UserMealWithExcess>> values = meals.stream()
				.collect(Collectors.groupingBy(UserMeal::getDate,
						Collector.of(Aggregate::new, Aggregate::accumulate, Aggregate::combine, Aggregate::finisher)))
				.values();

		return values.stream().flatMap(x -> x).collect(Collectors.toList());
	}

	private static UserMealWithExcess createWithExceed(UserMeal meal, boolean exceed) {
		return new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceed);
	}
}
