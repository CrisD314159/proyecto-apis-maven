package co.edu.uniquindio.apis.services.comment;

import co.edu.uniquindio.apis.dtos.CommentCreateDTO;
import co.edu.uniquindio.apis.dtos.CommentResponseDTO;
import co.edu.uniquindio.apis.dtos.CommentUpdateDTO;
import co.edu.uniquindio.apis.exceptions.EntityNotFoundException;
import co.edu.uniquindio.apis.mappers.domainMappers.CommentMapper;
import co.edu.uniquindio.apis.model.Comment;
import co.edu.uniquindio.apis.model.Program;
import co.edu.uniquindio.apis.model.User;
import co.edu.uniquindio.apis.model.enums.CommentState;
import co.edu.uniquindio.apis.repositories.comment.CommentRepository;
import co.edu.uniquindio.apis.repositories.program.ProgramRespository;
import co.edu.uniquindio.apis.repositories.user.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CommentServiceImpl implements CommentService{

    @Inject
    CommentRepository commentRepository;

    @Inject
    ProgramRespository programRespository;

    @Inject
    UserRepository userRepository;

    @Inject
    CommentMapper commentMapper;

    @Override
    public void CreateComment(CommentCreateDTO commentDTO) {
        Comment comment = new Comment();
        Program program = programRespository.findById(commentDTO.programId());
        if(program == null) throw  new EntityNotFoundException("No se encontró el programa");
        comment.setAuthorId(commentDTO.authorId());
        comment.setContent(commentDTO.content());
        comment.setProgram(program);
        commentRepository.persist(comment);
    }

    @Override
    public void UpdateComment(CommentUpdateDTO commentDTO) {
        Comment comment = commentRepository.findById(commentDTO.id());
        comment.setContent(commentDTO.content());
        commentRepository.persist(comment);
    }

    @Override
    public void DeleteComment(long id) {
        Comment comment = commentRepository.findById(id);
        commentRepository.delete(comment);
    }

    @Override
    public CommentResponseDTO GetComment(Long id) {
        Comment comment = commentRepository.findById(id);
        Program program = programRespository.findById(comment.getProgram().getId());
        User author = userRepository.findById(comment.getAuthorId());
        return new CommentResponseDTO(
                comment.getId(), comment.getContent(), comment.getAuthorId(), author.getFullName(),
                program.getId(), program.getTitle()
        );
    }

    @Override
    public void ResolveComment(Long id) {
        Comment comment = commentRepository.findById(id);
        comment.setState(CommentState.RESOLVED);
        commentRepository.persist(comment);
    }
}
