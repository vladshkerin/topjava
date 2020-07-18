package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.UserTo;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UsersUtil {

	public static final List<User> USERS = Arrays.asList(
			new User("Admin", "admin@mail.ru", "admin", true, Role.ADMIN),
			new User("User1", "user1@mail.ru", "ivan1", true, Role.USER),
			new User("User2", "user2@mail.ru", "ivan2", true, Role.USER),
			new User("User3", "user3@mail.ru", "ivan3", false, Role.USER),
			new User("User4", "user4@mail.ru", "ivan4", true, Role.USER),
			new User("User5", "user5@mail.ru", "ivan5", false, Role.USER),
			new User("User6", "user6@mail.ru", "ivan6", true, Role.USER)
	);

	public static List<UserTo> getTos(Collection<User> users) {
		return users.stream()
				.map(user -> createTo(user))
				.collect(Collectors.toList());
	}

	private static UserTo createTo(User user) {
		return new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword(),
				user.isEnabled(), user.getRegistered(), user.getRoles(), user.getCaloriesPerDay());
	}
}
