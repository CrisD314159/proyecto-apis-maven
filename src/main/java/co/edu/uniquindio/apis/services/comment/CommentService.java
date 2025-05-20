package co.edu.uniquindio.apis.services.comment;

import co.edu.uniquindio.apis.dtos.CommentCreateDTO;
import co.edu.uniquindio.apis.dtos.CommentDTO;
import co.edu.uniquindio.apis.dtos.CommentResponseDTO;
import co.edu.uniquindio.apis.dtos.CommentUpdateDTO;
import co.edu.uniquindio.apis.model.Comment;
import co.edu.uniquindio.apis.repositories.example.ExampleRepository;
import jakarta.inject.Inject;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(CommentCreateDTO commentDTO);

    CommentDTO updateComment(CommentUpdateDTO commentDTO);

    void deleteComment(Long id);

    CommentDTO getComment(Long id);

    CommentDTO resolveComment(Long id);

}
