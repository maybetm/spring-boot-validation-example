### Пример с настраиваемым порядком валидации в hibernate validation

---

#### Структура данных [UserDto](src/main/java/ru/maybetm/UserDto.java)
```java
// Благодаря этому интерфейсу удаётся указать порядок валидации
@GroupSequence({FirstPhase.class, SecondPhase.class})
interface ValidationOrder {}

@UserDtoCorrectly(groups = SecondPhase.class)
public record UserDto(
        @NotBlank(groups = FirstPhase.class)
        String id,
        @NotBlank(groups = FirstPhase.class)
        String name,
        @NotEmpty(groups = FirstPhase.class)
        List<@Valid TagDto> tags
) { }

record TagDto(
        @NotBlank(groups = FirstPhase.class)
        String key,
        @NotBlank(groups = FirstPhase.class)
        String value
) { }

```
#### Пример вызова валидации на сервисном слое [UserService](src/main/java/ru/maybetm/UserService.java)
```java
@Service 
@Validated // Чтобы заработала валидация на сервисном слое (@Valid)
public class UserService {

    private final Map<String, UserDto> users = new HashMap<>();

    @Validated({ValidationOrder.class}) // Чтобы задать порядок
    public void createUser(@Valid UserDto user) {
        users.put(user.id(), user);
    }
}
```

#### Подготовленные запросы для тестирования
- [create_user.http](src/test/resources/create_user.http) - успешное создание юзера
- [create_user_tag_key_has_null.http](src/test/resources/create_user_tag_key_has_null.http) - запрос должен упасть пустом поле value в Tags, 
до кастомной валидации проверка не дойдёт
- [create_user_tag_incorrect_user_name.http](src/test/resources/create_user_tag_incorrect_user_name.http) - запрос упадёт в кастомном валидаторе
