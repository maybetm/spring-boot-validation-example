package ru.maybetm;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.Map;

@Service
@Validated
public class UserService {

    private final Map<String, UserDto> users = new HashMap<>();

    @Validated({ValidationOrder.class})
    public void createUser(@Valid UserDto user) {
        users.put(user.id(), user);
    }
}
