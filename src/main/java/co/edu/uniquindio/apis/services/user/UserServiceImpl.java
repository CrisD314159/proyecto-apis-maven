package co.edu.uniquindio.apis.services.user;

import co.edu.uniquindio.apis.dtos.*;
import co.edu.uniquindio.apis.exceptions.EntityNotFoundException;
import co.edu.uniquindio.apis.exceptions.ValidationException;
import co.edu.uniquindio.apis.mappers.domainMappers.LoginMapper;
import co.edu.uniquindio.apis.mappers.domainMappers.UserMapper;
import co.edu.uniquindio.apis.model.User;
import co.edu.uniquindio.apis.model.enums.Role;
import co.edu.uniquindio.apis.model.enums.UserState;
import co.edu.uniquindio.apis.repositories.user.UserRepository;
import co.edu.uniquindio.apis.services.security.JWTService;
import co.edu.uniquindio.apis.services.security.JWTServiceImpl;
import io.micrometer.core.instrument.MeterRegistry;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped

public class UserServiceImpl implements UserService {
    @Inject
    UserRepository userRepository;
    @Inject
    UserMapper userMapper;
    @Inject
    JWTService jwtService;
    @Inject
    LoginMapper loginMapper;
    @Inject
    MeterRegistry meterRegistry; // Clase que almacena todas las metricas de la aplicación

    @Override
    @Transactional
    public UserResponseDTO CreateUser(UserCreateDTO userCreateDTO) {

        if(userExists(userCreateDTO.email())) throw new ValidationException("User already exists");

        User user = userMapper.toEntity(userCreateDTO);
        user.setCreationDate(LocalDateTime.now());
        int code = (int) (Math.random() * 9000) + 1000;
        user.setVerificationCode(code);
        user.setCodeModificationDate(LocalDateTime.now());
        user.setState(UserState.UNVERIFIED);
        user.setRole(Role.ESTUDENT);
        userRepository.persist(user);
        // Adición de un contador que acumula la cantidad de veces que se crea un usuario
        meterRegistry.counter("apis.user.created").increment();
        return userMapper.toResponseDTO(user);
    }


    private boolean userExists(String email) {
        boolean exists = userRepository.find("email", email).firstResult() != null;
        System.out.println(exists);
        return exists;
    }

    @Override
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

    @Override
    @Transactional
    public boolean UpdateUser(UserUpdateRequestDTO userUpdateRequestDTO) {
        User user = userRepository.findById(userUpdateRequestDTO.Id());
        System.out.println(user.getEmail());

        user.setFullName(userUpdateRequestDTO.fullName());
        userRepository.persist(user);

        return true;

    }

    @Override
    @Transactional
    public boolean DeleteUser(Long id) {
        User user = userRepository.findById(id);

        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }

        userRepository.delete(user);

        return true;
    }


    public LoginResponseDTO Login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.find("email", loginRequestDTO.email()).firstResult();
        if (user == null) throw  new EntityNotFoundException("Invalid email or password");
        System.out.println(user.getFullName());
        if(!BcryptUtil.matches(loginRequestDTO.password(), user.getPassword()))
        {
            System.out.println("Invalid password");
            throw new ValidationException("Invalid email or password");
        }

        String token = jwtService.generateUserToken(user.getEmail());
        return loginMapper.toLoginResponseDTO(token, token);
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
