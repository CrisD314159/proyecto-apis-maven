package co.edu.uniquindio.apis.services.program;

import co.edu.uniquindio.apis.dtos.CommentDTO;
import co.edu.uniquindio.apis.dtos.ProgramCreateDTO;
import co.edu.uniquindio.apis.dtos.ProgramResponseDTO;
import co.edu.uniquindio.apis.dtos.ProgramUpdateRequestDTO;
import co.edu.uniquindio.apis.exceptions.EntityNotFoundException;
import co.edu.uniquindio.apis.mappers.domainMappers.ProgramMapper;
import co.edu.uniquindio.apis.model.Program;
import co.edu.uniquindio.apis.repositories.program.ProgramRespository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProgramServiceImpl implements ProgramService {

    @Inject
    ProgramRespository programRepository;

    @Inject
    ProgramMapper programMapper;

    @Override
    @Transactional
    public ProgramResponseDTO createProgram(ProgramCreateDTO dto) {
        Program program = programMapper.parseOf(dto);
        programRepository.persist(program);
        return programMapper.toDTO(program);
    }

    @Override
    public List<ProgramResponseDTO> getAllPrograms() {
        return programRepository.findAll().stream()
                .map(programMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProgramResponseDTO getById(Long id) {
        Program program = findByIdOrThrow(id);
        return programMapper.toDTO(program);
    }

    @Override
    @Transactional
    public ProgramResponseDTO updateProgram(ProgramUpdateRequestDTO dto) {
        Program program = findByIdOrThrow(dto.id());

        program.setTitle(dto.title());
        program.setDescription(dto.description());
        program.setContent(dto.content());

        return programMapper.toDTO(program);
    }

    @Override
    @Transactional
    public boolean deleteProgram(Long id) {
        Program program = findByIdOrThrow(id);
        programRepository.delete(program);
        return true;
    }

    @Override
    public List<CommentDTO> getAllComments(Long programId, int offset, int limit) {
        // TODO: implementar cuando tengas CommentRepository
        return List.of();
    }


    private Program findByIdOrThrow(Long id) {
        Program program = programRepository.findById(id);
        if (program == null) {
            throw new EntityNotFoundException("Programa con ID " + id + " no encontrado");
        }
        return program;
    }
}
