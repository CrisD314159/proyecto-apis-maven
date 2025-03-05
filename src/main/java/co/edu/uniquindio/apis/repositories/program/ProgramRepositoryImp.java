package co.edu.uniquindio.apis.repositories.program;

import co.edu.uniquindio.apis.model.Program;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProgramRepositoryImp implements  ProgramRespository, PanacheRepository<Program> {
}
