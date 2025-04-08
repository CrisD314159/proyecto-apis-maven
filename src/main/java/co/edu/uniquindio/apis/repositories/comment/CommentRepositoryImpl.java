package co.edu.uniquindio.apis.repositories.comment;

import co.edu.uniquindio.apis.model.Comment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CommentRepositoryImpl implements CommentRepository, PanacheRepository<Comment> {
}
