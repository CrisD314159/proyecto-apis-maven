package co.edu.uniquindio.apis.services.program;

import co.edu.uniquindio.apis.dtos.CommentDTO;
import co.edu.uniquindio.apis.dtos.ProgramCreateDTO;
import co.edu.uniquindio.apis.dtos.ProgramResponseDTO;
import co.edu.uniquindio.apis.dtos.ProgramUpdateRequestDTO;

import java.util.List;

public interface ProgramService {
    ProgramResponseDTO createProgram(ProgramCreateDTO programCreateDTO);

    List<ProgramResponseDTO> getAllPrograms();

    List<CommentDTO> getAllComments(String id, int offset, int limit);

    ProgramResponseDTO getById(String id);

    ProgramResponseDTO updateProgram(ProgramUpdateRequestDTO programUpdateRequestDTO);

    boolean deleteProgram(String id);
}