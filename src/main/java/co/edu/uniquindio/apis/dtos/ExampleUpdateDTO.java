package co.edu.uniquindio.apis.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ExampleUpdateDTO (
        @NotBlank
        Long id,
        @NotBlank(message = "Title is required")
        @Size(max = 100, message = "Title must not exceed 100 characters")
        String title,

        @NotBlank(message = "Description is required")
        @Size(max = 500, message = "Description must not exceed 500 characters")
        String description,

        @NotBlank(message = "Content is required")
        String content,

        @NotBlank(message = "Difficulty is required")
        String difficulty
) {
}
