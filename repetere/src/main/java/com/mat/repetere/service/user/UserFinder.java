package com.mat.repetere.service.user;

import com.mat.repetere.dto.user.UserResponseDto;
import com.mat.repetere.exception.UserNotFoundException;
import com.mat.repetere.model.User;
import com.mat.repetere.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFinder {
    private  final UserRepository repository;

    public UserFinder(UserRepository repository){
        this.repository = repository;
    }

    public List<UserResponseDto> findAll(){
        return repository.findAll()
                .stream()
                .map(UserResponseDto::fromEntity)
                .toList();
    }

    public UserResponseDto findById(Long id){
        User user = repository.findById(id).orElseThrow( () -> new UserNotFoundException(id));
        return UserResponseDto.fromEntity(user);
    }
}
