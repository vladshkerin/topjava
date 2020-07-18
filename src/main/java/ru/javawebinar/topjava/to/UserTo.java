package ru.javawebinar.topjava.to;

import ru.javawebinar.topjava.model.Role;

import java.util.Date;
import java.util.Set;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class UserTo {
	private final Integer id;

	private String name;

	private String email;

	private String password;

	private boolean enabled;

	private Date registered = new Date();

	private Set<Role> roles;

	private int caloriesPerDay = DEFAULT_CALORIES_PER_DAY;

	public UserTo(Integer id, String name, String email, String password, boolean enabled, Date registered, Set<Role> roles, int caloriesPerDay) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.enabled = enabled;
		this.registered = registered;
		this.roles = roles;
		this.caloriesPerDay = caloriesPerDay;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public Date getRegistered() {
		return registered;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public int getCaloriesPerDay() {
		return caloriesPerDay;
	}

	@Override
	public String toString() {
		return "UserTo{" +
				"id=" + id +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", enabled=" + enabled +
				", registered=" + registered +
				", roles=" + roles +
				", caloriesPerDay=" + caloriesPerDay +
				'}';
	}
}
