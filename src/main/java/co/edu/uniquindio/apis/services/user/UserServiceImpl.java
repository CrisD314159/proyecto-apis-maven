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
import co.edu.uniquindio.apis.services.email.EmailSenderInteface;
import co.edu.uniquindio.apis.services.security.JWTService;
import io.micrometer.core.instrument.MeterRegistry;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    @Inject
    UserRepository userRepository;

    @Inject
    UserMapper userMapper;

    @Inject
    JWTService jwtService;

    @Inject
    LoginMapper loginMapper;

    @Inject
    MeterRegistry meterRegistry;

    @Inject
    EmailSenderInteface emailSender;

    @Override
    @Transactional
    public UserResponseDTO CreateUser(UserCreateDTO userCreateDTO) {
        if (userExists(userCreateDTO.email())) {
            throw new ValidationException("User already exists");
        }

        User user = userMapper.toEntity(userCreateDTO);
        user.setCreationDate(LocalDateTime.now());
        user.setVerificationCode(generateVerificationCode());
        user.setCodeModificationDate(LocalDateTime.now());
        user.setState(UserState.UNVERIFIED);
        user.setRole(Role.ESTUDENT);

        userRepository.persist(user);
        meterRegistry.counter("apis.user.created").increment();

        // Aquí podrías enviar correo de verificación si lo deseas
        // emailSender.sendVerificationEmail(user.getEmail(), user.getVerificationCode());

        return userMapper.toResponseDTO(user);
    }

    private boolean userExists(String email) {
        return userRepository.find("email", email).firstResult() != null;
    }

    private int generateVerificationCode() {
        return (int) (Math.random() * 9000) + 1000;
    }

    @Override
    public List<UserResponseDTO> GetAllUsers(int offset, int limit) {
        return userRepository.findAll()
                .range(offset, offset + limit)
                .stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO GetUserById(Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        return userMapper.toResponseDTO(user);
    }

    @Override
    @Transactional
    public boolean UpdateUser(UserUpdateRequestDTO dto) {
        User user = userRepository.findById(dto.id());
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }

        user.setFullName(dto.fullName());
        userRepository.persist(user);

        meterRegistry.counter("apis.user.updated").increment();
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
        meterRegistry.counter("apis.user.deleted").increment();
        return true;
    }

    @Override
    public LoginResponseDTO Login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.find("email", loginRequestDTO.email()).firstResult();

        if (user == null || !BcryptUtil.matches(loginRequestDTO.password(), user.getPassword())) {
            throw new ValidationException("Invalid email or password");
        }

        String token = jwtService.generateUserToken(user.getEmail());
        return loginMapper.toLoginResponseDTO(token, token);
    }

    @Override
    @Transactional
    public boolean VerifyAccount(AccountVerificationRequestDTO request) {
        User user = userRepository.find("email", request.email()).firstResult();

        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }

        if (user.getVerificationCode() == request.verificationCode()) {
            user.setVerificationCode(0);
            user.setState(UserState.ACTIVE);
            userRepository.persist(user);
            return true;
        }

        throw new ValidationException("Invalid verification code");
    }
}
