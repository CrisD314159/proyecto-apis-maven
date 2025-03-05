package co.edu.uniquindio.apis.repositories.user;

import co.edu.uniquindio.apis.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;


public interface UserRepository extends PanacheRepository<User> {
}
