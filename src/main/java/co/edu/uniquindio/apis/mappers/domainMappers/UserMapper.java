package co.edu.uniquindio.apis.mappers.domainMappers;

import co.edu.uniquindio.apis.dtos.LoginResponseDTO;
import co.edu.uniquindio.apis.dtos.UserCreateDTO;
import co.edu.uniquindio.apis.dtos.UserResponseDTO;
import co.edu.uniquindio.apis.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "codeModificationDate", ignore = true)
    @Mapping(target = "verificationCode", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", expression = "java( io.quarkus.elytron.security.common.BcryptUtil.bcryptHash(userCreateDTO.password()) )")
    User toEntity(UserCreateDTO userCreateDTO);


    UserResponseDTO toResponseDTO(User user);

}