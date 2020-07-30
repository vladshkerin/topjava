package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
	public static final int MEAL_ID = START_SEQ;

	public static final Meal MEAL_USER_1 = new Meal(MEAL_ID, LocalDateTime.now(), "Пользователь ланч", 430);
	public static final Meal MEAL_USER_2 = new Meal(MEAL_ID + 1, LocalDateTime.now(), "Пользователь ужин", 1400);
	public static final Meal MEAL_ADMIN_1 = new Meal(MEAL_ID + 2, LocalDateTime.now(), "Админ ланч", 510);
	public static final Meal MEAL_ADMIN_2 = new Meal(MEAL_ID + 3, LocalDateTime.now(), "Админ ужин", 1500);

	public static void assertMatch(Meal actual, Meal expected) {
		assertThat(actual).isEqualToIgnoringGivenFields(expected, "dateTime");
	}

	public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
		assertMatch(actual, Arrays.asList(expected));
	}

	public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
		assertThat(actual).usingElementComparatorIgnoringFields("dateTime").isEqualTo(expected);
	}

	public static Meal getUpdated() {
		Meal updated = new Meal(MEAL_USER_1);
		updated.setDescription("UpdatedDesc");
		updated.setCalories(1234);
		return updated;
	}

	public static Meal getNew() {
		return new Meal(null, LocalDateTime.now(), "New desc", 1555);
	}
}
