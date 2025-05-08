package co.edu.uniquindio.apis.mappers.domainMappers;

import co.edu.uniquindio.apis.dtos.CommentCreateDTO;
import co.edu.uniquindio.apis.dtos.CommentDTO;
import co.edu.uniquindio.apis.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "program", ignore = true)
    Comment toEntity(CommentCreateDTO commentDTO);

    CommentDTO toResponseDTO(Comment comment);

}
