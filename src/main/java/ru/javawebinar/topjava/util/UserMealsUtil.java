package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		List<UserMealWithExcess> mealsTo = getFilteredWithExceeded(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
		mealsTo.forEach(System.out::println);
	}

	public static List<UserMealWithExcess> getFilteredWithExceeded(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
		// TODO return filtered list with correctly exceeded field
		List<UserMeal> filterMeal = new ArrayList<>(meals.size());
		int exceed = 0;
		for (UserMeal meal : meals) {
			if (TimeUtil.isBetweenInclusive(meal.getDateTime().toLocalTime(), startTime, endTime)) {
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

	public static List<UserMealWithExcess> getFilteredWithExceededStream(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
		int exceed = meals.stream()
				.filter(meal -> TimeUtil.isBetweenInclusive(meal.getDateTime().toLocalTime(), startTime, endTime))
				.map(m -> m.getCalories())
				.reduce((m1, m2) -> m1 + m2)
				.get();

		return null;
	}
}
