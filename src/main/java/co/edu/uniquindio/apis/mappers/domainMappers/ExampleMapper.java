package co.edu.uniquindio.apis.mappers.domainMappers;

import co.edu.uniquindio.apis.dtos.ExampleCreateDTO;
import co.edu.uniquindio.apis.dtos.ExampleResponseDTO;
import co.edu.uniquindio.apis.model.Example;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface ExampleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    Example toEntity(ExampleCreateDTO exampleCreateDTO);

    ExampleResponseDTO toResponseDTO(Example example);
}