package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {

    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }

        Meal result = null;
        if (meal.getUserId() == userId) {
            // handle case: update, but not present in storage
            result = repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        return result;
    }

    @Override
    public boolean delete(int id, int userId) {
        boolean result = false;
        Meal meal = repository.get(id);
        if (meal.getUserId() == userId) {
            result = repository.remove(id) != null;
        }
        return result;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal result = null;
        Meal meal = repository.get(id);
        if (meal.getUserId() == userId) {
            result = meal;
        }
        return result;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }
}

