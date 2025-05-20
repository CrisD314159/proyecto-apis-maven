package co.edu.uniquindio.apis.services.comment;

import co.edu.uniquindio.apis.dtos.CommentCreateDTO;
import co.edu.uniquindio.apis.dtos.CommentDTO;
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
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CommentServiceImpl implements CommentService {

    private static final Logger LOGGER = Logger.getLogger(CommentServiceImpl.class);

    @Inject
    CommentRepository commentRepository;

    @Inject
    ProgramRespository programRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    CommentMapper commentMapper;

    @Inject
    MeterRegistry meterRegistry;

    @Override
    @Transactional
    public CommentDTO createComment(CommentCreateDTO dto) {
        Program program = findProgramOrThrow(dto.programId());
        User author  = findUserOrThrow(dto.authorId());

        Comment comment = commentMapper.toEntity(dto);
        comment.setProgram(program);
        comment.setAuthorId(author.getId());
        comment.setState(CommentState.OPEN);

        commentRepository.persist(comment);
        meterRegistry.counter("apis.comment.created").increment();

        LOGGER.infof("Comment %d created for Program %d by User %d",
                comment.getId(), program.getId(), author.getId());

        return commentMapper.toResponseDTO(comment);
    }


    @Override
    public CommentDTO getComment(Long id) {
        return commentMapper.toResponseDTO(findCommentOrThrow(id));
    }

    @Override
    @Transactional
    public CommentDTO updateComment(CommentUpdateDTO dto) {
        Comment comment = findCommentOrThrow(dto.id());
        comment.setContent(dto.content());

        commentRepository.persist(comment);
        meterRegistry.counter("apis.comment.updated").increment();

        LOGGER.infof("Comment %d updated", comment.getId());
        return commentMapper.toResponseDTO(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        Comment comment = findCommentOrThrow(id);
        commentRepository.delete(comment);
        meterRegistry.counter("apis.comment.deleted").increment();

        LOGGER.infof("Comment %d deleted", id);
    }

    @Override
    @Transactional
    public CommentDTO resolveComment(Long id) {
        Comment comment = findCommentOrThrow(id);
        comment.setState(CommentState.RESOLVED);

        commentRepository.persist(comment);
        meterRegistry.counter("apis.comment.resolved").increment();

        LOGGER.infof("Comment %d resolved", id);
        return commentMapper.toResponseDTO(comment);
    }

    // Métodos auxiliares
    private Comment findCommentOrThrow(Long id) {
        Comment c = commentRepository.findById(id);
        if (c == null) {
            throw new EntityNotFoundException("Comment with id " + id + " not found");
        }
        return c;
    }

    private Program findProgramOrThrow(Long id) {
        Program p = programRepository.findById(id);
        if (p == null) {
            throw new EntityNotFoundException("Program with id " + id + " not found");
        }
        return p;
    }

    private User findUserOrThrow(Long id) {
        User u = userRepository.findById(id);
        if (u == null) {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }
        return u;
    }
}
