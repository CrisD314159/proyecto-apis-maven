package co.edu.uniquindio.apis.repositories.comment;

import co.edu.uniquindio.apis.model.Comment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface CommentRepository extends PanacheRepository<Comment> {
}
