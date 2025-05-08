package co.edu.uniquindio.apis.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UUID;

public record ProgramUpdateRequestDTO(


        Long id,

        @NotBlank
        @Size(max = 50, message = "tittle cannot have more than 50 characteres")
        String title,

        @NotBlank
        @Size(max = 255, message = "description cannot have more than 255 characteres")
        String description,

        @NotBlank
        @Size(max = 500, message = "content cannot have more than 500 characteres")
        String content

) {
}
