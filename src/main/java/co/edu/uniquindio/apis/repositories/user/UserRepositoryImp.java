package co.edu.uniquindio.apis.repositories.user;

import co.edu.uniquindio.apis.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepositoryImp implements UserRepository, PanacheRepository<User> {
}
