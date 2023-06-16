package nl.novi.EindopdrachtBackend.services;

import nl.novi.EindopdrachtBackend.dtos.UserDto;
import nl.novi.EindopdrachtBackend.exceptions.DuplicateRecordException;
import nl.novi.EindopdrachtBackend.exceptions.UserNotFoundException;
import nl.novi.EindopdrachtBackend.models.Authority;
import nl.novi.EindopdrachtBackend.models.User;
import nl.novi.EindopdrachtBackend.repositories.UserRepository;
import nl.novi.EindopdrachtBackend.utils.RandomStringGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long createUser(UserDto userDto) {
        Long id = userDto.getEmployeeId();
        if (userRepository.findByEmployeeId(id).isPresent())
            throw new DuplicateRecordException(String.format("A user with id %s already exists", id));

        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userDto.setApikey(randomString);

        User newUser = toUser(userDto);
        userRepository.save(newUser);
        return newUser.getEmployeeId();
    }

    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(fromUser(user));
        }
        return ResponseEntity.ok().body(collection);
    }

    public ResponseEntity<UserDto> getUser(Long employeeId) {
        Optional<User> user = userRepository.findByEmployeeId(employeeId);

        if (user.isEmpty())
            throw new UserNotFoundException(employeeId);

        return ResponseEntity.ok().body(fromUser(user.get()));
    }

    public ResponseEntity<UserDto> updateUser(Long employeeId, UserDto newUser) {
        if (userRepository.findByEmployeeId(employeeId).isEmpty())
            throw new UserNotFoundException(employeeId);

        User user = userRepository.findByEmployeeId(employeeId).get();
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setPassword(newUser.getPassword());
        user.setDob(newUser.getDob());

        userRepository.save(user);

        return ResponseEntity.ok(fromUser(user));
    }

    public ResponseEntity<String> deleteUser(Long employeeId) {
        if (userRepository.findByEmployeeId(employeeId).isEmpty())
            throw new UserNotFoundException(employeeId);

        userRepository.deleteById(String.valueOf(employeeId));

        return ResponseEntity.ok(String.format("Employee %d was removed from the database", employeeId));
    }

    public ResponseEntity<Set<Authority>> getAuthorities(Long employeeId) {
        if (userRepository.findByEmployeeId(employeeId).isEmpty())
            throw new UserNotFoundException(employeeId);

        User user = userRepository.findByEmployeeId(employeeId).get();
        UserDto userDto = fromUser(user);

        return ResponseEntity.ok().body(userDto.getAuthorities());
    }

    public ResponseEntity<String> addAuthority(Long employeeId, String authority) {

        if (userRepository.findByEmployeeId(employeeId).isEmpty())
            throw new UserNotFoundException(employeeId);

        User user = userRepository.findByEmployeeId(employeeId).get();
        user.addAuthority(new Authority(employeeId, authority));
        userRepository.save(user);

        return ResponseEntity.ok(String.format("Authority %s was added to employee %d", authority, employeeId));
    }

    public void removeAuthority(Long employeeId, String authority) {
        if (userRepository.findByEmployeeId(employeeId).isEmpty())
            throw new UserNotFoundException(employeeId);

        User user = userRepository.findByEmployeeId(employeeId).get();
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }

    public static UserDto fromUser(User user) {

        var dto = new UserDto();

        dto.employeeId = user.getEmployeeId();
        dto.firstName = user.getFirstName();
        dto.lastName = user.getLastName();
        dto.password = user.getPassword();
        dto.dob = user.getDob();
        dto.enabled = user.isEnabled();
        dto.apikey = user.getApikey();
        dto.authorities = user.getAuthorities();

        return dto;
    }

    public User toUser(UserDto userDto) {

        var user = new User();

        user.setEmployeeId(userDto.getEmployeeId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setDob(userDto.getDob());
        user.setEnabled(userDto.getEnabled());
        user.setApikey(userDto.getApikey());

        return user;
    }

}
