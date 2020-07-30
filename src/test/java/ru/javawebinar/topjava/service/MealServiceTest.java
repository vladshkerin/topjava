package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
		"classpath:spring/spring-app.xml",
		"classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB_meals.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

	static {
		SLF4JBridgeHandler.install();
	}

	@Autowired
	private MealService service;

	@Test
	public void get() {
		Meal meal = service.get(MEAL_ID, USER_ID);
		assertMatch(meal, MEAL_USER_1);
	}

	@Test(expected = NotFoundException.class)
	public void getNotFound() throws Exception {
		service.get(1, 1);
	}

	@Test(expected = NotFoundException.class)
	public void delete() {
		service.delete(MEAL_ID, USER_ID);
		service.get(MEAL_ID, USER_ID);
	}

	@Test(expected = NotFoundException.class)
	public void deleteNotFound() {
		service.delete(1, 1);
	}

	@Test
	public void getBetweenHalfOpen() {
	}

	@Test
	public void getAll() {
		List<Meal> mealsUsers = service.getAll(USER_ID);
		List<Meal> mealsAdmins = service.getAll(ADMIN_ID);

		assertMatch(mealsUsers, Arrays.asList(MEAL_USER_1, MEAL_USER_2));
		assertMatch(mealsAdmins, Arrays.asList(MEAL_ADMIN_1, MEAL_ADMIN_2));
	}

	@Test
	public void update() {
		Meal updated = getUpdated();
		service.update(updated, USER_ID);

		assertMatch(service.get(MEAL_ID, USER_ID), updated);
	}

	@Test
	public void create() {
		Meal newMeal = getNew();
		Meal created = service.create(newMeal, USER_ID);
		Integer newId = created.getId();
		newMeal.setId(newId);

		assertMatch(created, newMeal);
		assertMatch(service.get(newId, USER_ID), newMeal);
	}
}