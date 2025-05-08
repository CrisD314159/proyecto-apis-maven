package co.edu.uniquindio.apis.services.program;

import co.edu.uniquindio.apis.dtos.CommentDTO;
import co.edu.uniquindio.apis.dtos.ProgramCreateDTO;
import co.edu.uniquindio.apis.dtos.ProgramResponseDTO;
import co.edu.uniquindio.apis.dtos.ProgramUpdateRequestDTO;
import co.edu.uniquindio.apis.exceptions.EntityNotFoundException;
import co.edu.uniquindio.apis.mappers.domainMappers.CommentMapper;
import co.edu.uniquindio.apis.mappers.domainMappers.ProgramMapper;
import co.edu.uniquindio.apis.model.Program;
import co.edu.uniquindio.apis.repositories.program.ProgramRespository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

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
    public ProgramResponseDTO createProgram(ProgramCreateDTO programCreateDTO) {
        Program program = programMapper.parseOf(programCreateDTO);
        programRepository.persist(program);
        return programMapper.toDTO(program);
    }

    @Override
    @Transactional
    public List<ProgramResponseDTO> getAllPrograms() { //puede estar ordenado
        var programs = programRepository.findAll();
        return programs.stream().map(programMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<CommentDTO> getAllComments(Long id, int offset, int limit) {
        return List.of(); //TODO Revisar si se debe crear repository de comentario
    }


    @Override
    @Transactional
    public ProgramResponseDTO getById(Long id) {
        Program program = programRepository.findById(id);
        if (program == null) {
            throw new EntityNotFoundException("Programa no encontrado");
        }
        return programMapper.toDTO(program);
    }

    @Override
    @Transactional
    public ProgramResponseDTO updateProgram(ProgramUpdateRequestDTO programUpdateRequestDTO) {
        Program program = programRepository.findById(programUpdateRequestDTO.id());

        if (program != null) {
            program.setTitle(programUpdateRequestDTO.title());
            program.setDescription(programUpdateRequestDTO.description());
            program.setContent(programUpdateRequestDTO.content());

            programRepository.persist(program);

            return programMapper.toDTO(program);
        }

        return null;
    }

    @Override
    @Transactional
    public boolean deleteProgram(Long id) {
        var program = programRepository.find("id",id).firstResult();

        if (program == null) {
            throw new EntityNotFoundException("Programa no encontrado");
        }

        programRepository.delete(program);

        return true;
    }


}