package co.edu.uniquindio.apis.services.comment;

import co.edu.uniquindio.apis.dtos.CommentCreateDTO;
import co.edu.uniquindio.apis.dtos.CommentResponseDTO;
import co.edu.uniquindio.apis.dtos.CommentUpdateDTO;
import co.edu.uniquindio.apis.model.Comment;
import co.edu.uniquindio.apis.repositories.example.ExampleRepository;
import jakarta.inject.Inject;

public interface CommentService {
    void CreateComment(CommentCreateDTO comment);
    void UpdateComment(CommentUpdateDTO comment);
    void DeleteComment(long id);
    CommentResponseDTO GetComment(Long id);
    void ResolveComment(Long id);
}
