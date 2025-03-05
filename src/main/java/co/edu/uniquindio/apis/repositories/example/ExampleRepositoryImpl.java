package co.edu.uniquindio.apis.repositories.example;

import co.edu.uniquindio.apis.model.Example;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExampleRepositoryImpl implements ExampleRepository, PanacheRepository<Example> {
}
