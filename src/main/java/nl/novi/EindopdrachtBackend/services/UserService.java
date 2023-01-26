package nl.novi.EindopdrachtBackend.services;

import nl.novi.EindopdrachtBackend.dtos.UserDto;
import nl.novi.EindopdrachtBackend.exceptions.DuplicateRecordException;
import nl.novi.EindopdrachtBackend.exceptions.UserNotFoundException;
import nl.novi.EindopdrachtBackend.models.Authority;
import nl.novi.EindopdrachtBackend.models.User;
import nl.novi.EindopdrachtBackend.repositories.UserRepository;
import nl.novi.EindopdrachtBackend.utils.RandomStringGenerator;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getUsers() {
        List<UserDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(fromUser(user));
        }
        return collection;
    }

    public UserDto getUser(Long employeeId) {
        UserDto dto = new UserDto();
        Optional<User> user = userRepository.findByEmployeeId(employeeId);
        if (user.isEmpty())
            throw new UserNotFoundException(employeeId);

        dto = fromUser(user.get());

        return dto;
    }

    public boolean userExists(Long employeeId) {
        return userRepository.existsById(String.valueOf(employeeId));
    }

    public Long createUser(UserDto userDto) {
        Long id = userDto.getEmployeeId();
        if (userRepository.findByEmployeeId(id).isPresent())
            throw new DuplicateRecordException(String.format("A user with id %s already exists", id));

        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userDto.setApikey(randomString);
        User newUser = userRepository.save(toUser(userDto));
        return newUser.getEmployeeId();
    }

    public void deleteUser(Long employeeId) {
        if (userRepository.findByEmployeeId(employeeId).isEmpty())
            throw new UserNotFoundException(employeeId);

        userRepository.deleteById(String.valueOf(employeeId));
    }

    public void updateUser(Long employeeId, UserDto newUser) {
        if (userRepository.findByEmployeeId(employeeId).isEmpty())
            throw new UserNotFoundException(employeeId);

        User user = userRepository.findByEmployeeId(employeeId).get();
        user.setPassword(newUser.getPassword());
        userRepository.save(user);
    }

    public Set<Authority> getAuthorities(Long employeeId) {
        if (userRepository.findByEmployeeId(employeeId).isEmpty())
            throw new UserNotFoundException(employeeId);

        User user = userRepository.findByEmployeeId(employeeId).get();
        UserDto userDto = fromUser(user);
        return userDto.getAuthorities();
    }

    public void addAuthority(Long employeeId, String authority) {

        if (userRepository.findByEmployeeId(employeeId).isEmpty())
            throw new UserNotFoundException(employeeId);

        User user = userRepository.findByEmployeeId(employeeId).get();
        user.addAuthority(new Authority(employeeId, authority));
        userRepository.save(user);
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
