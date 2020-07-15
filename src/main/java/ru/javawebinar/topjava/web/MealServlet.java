package ru.javawebinar.topjava.web;

import java.time.LocalTime;
import java.util.List;
import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("getAll");

//        req.setAttribute("meals", MealsUtil.getTos(MealsUtil.MEALS, 2000));
        final LocalTime startTime = LocalTime.of(7, 0);
        final LocalTime endTime = LocalTime.of(12, 0);
        List<MealTo> mealsTo = MealsUtil.filteredByStreams(MealsUtil.meals, startTime, endTime, 2000);

        req.setAttribute("meals", mealsTo);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }
}
