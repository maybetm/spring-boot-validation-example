package ru.maybetm;

import jakarta.validation.GroupSequence;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

import static ru.maybetm.ValidationGroups.FirstPhase;
import static ru.maybetm.ValidationGroups.SecondPhase;

@GroupSequence({FirstPhase.class, SecondPhase.class})
interface ValidationOrder { }

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
