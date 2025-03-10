package co.edu.uniquindio.apis.mappers.domainMappers;

import co.edu.uniquindio.apis.dtos.LoginResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface LoginMapper {

    LoginResponseDTO toLoginResponseDTO(String token, String password);
}
