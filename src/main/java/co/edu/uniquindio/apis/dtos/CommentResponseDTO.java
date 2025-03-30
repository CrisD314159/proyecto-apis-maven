package co.edu.uniquindio.apis.dtos;

public record CommentResponseDTO
        (
                Long id,
                String content,
                Long authorId,
                String authorName,
                Long programId,
                String programName
        ){
}
