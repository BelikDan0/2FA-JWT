package com.example.demo.service;

import com.example.demo.Models.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Role;
import com.example.demo.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers(){
        return userRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    public Optional<User> findByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    public UserDto createUser(UserDto dto){
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole("USER" ); // важно

        User savedUser = userRepository.save(user); // 🔥 ВОТ ГЛАВНОЕ

        return toDto(savedUser); // id уже есть
    }

    public UserDto updateUser(Long id,UserDto dto){
        User user=userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователем с таким айди "+id+" не найден"));
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());

        User update =userRepository.save(user);
        return toDto(update);
    }
    public void deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new RuntimeException("Пользователь а ID:"+id+" не найден");
        }
        userRepository.deleteById(id);
    }

    public UserDto toDto(User user){
        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole());
        return userDto;
    }
}
