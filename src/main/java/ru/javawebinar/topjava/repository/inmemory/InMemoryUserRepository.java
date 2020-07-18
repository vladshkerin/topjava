package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UsersUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {

	private Map<Integer, User> repository = new ConcurrentHashMap<>();
	private AtomicInteger counter = new AtomicInteger(0);

	{
		UsersUtil.USERS.forEach(this::save);
	}

	@Override
	public User save(User user) {
		if (user.isNew()) {
			user.setId(counter.incrementAndGet());
			repository.put(user.getId(), user);
			return user;
		}
		// handle case: update, but not present in storage
		return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
	}

	@Override
	public boolean delete(int id) {
		return repository.remove(id) != null;
	}

	@Override
	public User get(int id) {
		return repository.get(id);
	}

	@Override
	public User getByEmail(String email) {
		return repository.values().stream()
				.filter(user -> email.equals(user.getEmail()))
				.findFirst()
				.orElse(null);
	}

	@Override
	public List<User> getAll() {
		return repository.values().stream()
				.sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
				.collect(Collectors.toList());
	}
}
