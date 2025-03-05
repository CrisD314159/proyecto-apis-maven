package co.edu.uniquindio.apis.services.user;

import co.edu.uniquindio.apis.dtos.*;
import co.edu.uniquindio.apis.exceptions.EntityNotFoundException;
import co.edu.uniquindio.apis.exceptions.ValidationException;
import co.edu.uniquindio.apis.mappers.domainMappers.UserMapper;
import co.edu.uniquindio.apis.model.User;
import co.edu.uniquindio.apis.model.enums.Role;
import co.edu.uniquindio.apis.model.enums.UserState;
import co.edu.uniquindio.apis.repositories.user.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped

public class UserServiceImpl implements UserService {
    @Inject
    UserRepository userRepository;
    @Inject
    UserMapper userMapper;


    @Transactional
    public UserResponseDTO CreateUser(UserCreateDTO userCreateDTO) {
        User user = userMapper.toEntity(userCreateDTO);
        user.setCreationDate(LocalDateTime.now());
        int code = (int) (Math.random() * 9000) + 1000;
        user.setVerificationCode(code);
        user.setCodeModificationDate(LocalDateTime.now());
        user.setState(UserState.UNVERIFIED);
        user.setRole(Role.ESTUDENT);
        userRepository.persist(user);
        return userMapper.toResponseDTO(user);
    }

    @Transactional
    public List<UserResponseDTO> GetAllUsers(int offset, int limit) {
        var users = userRepository.findAll().range(offset, offset + limit);
        return users.stream().map(userMapper::toResponseDTO).collect(Collectors.toList());
    }



    public UserResponseDTO GetUserById(Long id) {
        User user = userRepository.findById(id);

        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }

        return userMapper.toResponseDTO(user);
    }


    public boolean UpdateUser(UserUpdateRequestDTO userUpdateRequestDTO) {
        User user = userRepository.findById(userUpdateRequestDTO.Id());

        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }

        user.setFullName(userUpdateRequestDTO.fullName());

        userRepository.persist(user);

        return true;
    }


    public boolean DeleteUser(Long id) {
        User user = userRepository.findById(id);

        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }

        userRepository.delete(user);

        return true;
    }


    public LoginResponseDTO Login(LoginRequestDTO loginRequestDTO) {
        return new LoginResponseDTO(UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }


    public boolean VerifyAccount(AccountVerificationRequestDTO accountVerificationRequestDTO) {
        User user = (User) userRepository.find("email", accountVerificationRequestDTO.email());

        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        int verificationCode = user.getVerificationCode();
        if(verificationCode == accountVerificationRequestDTO.verificationCode()){
            user.setVerificationCode(0);
            userRepository.persist(user);
            return true;
        }
        throw new ValidationException("Invalid verification code");
    }
}
