package co.edu.uniquindio.apis.services.user;

import co.edu.uniquindio.apis.dtos.*;

import java.util.List;

public interface UserService {
    UserResponseDTO CreateUser(UserCreateDTO userCreateDTO);

    List<UserResponseDTO> GetAllUsers(int offset, int limit);


    UserResponseDTO GetUserById(Long id);


    boolean UpdateUser(UserUpdateRequestDTO updateRequestDTO);

    boolean DeleteUser(Long id);

    LoginResponseDTO Login(LoginRequestDTO loginRequestDTO);

    boolean VerifyAccount(AccountVerificationRequestDTO accountVerificationRequestDTO);



}