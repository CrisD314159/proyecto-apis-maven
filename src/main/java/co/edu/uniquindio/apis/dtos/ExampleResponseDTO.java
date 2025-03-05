package co.edu.uniquindio.apis.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record ExampleResponseDTO(
        Long id,
        String title,
        String description,
        String content,
        String creatorId,
        List<String> tags,
        String difficulty,
        LocalDateTime creationDate
) {}
