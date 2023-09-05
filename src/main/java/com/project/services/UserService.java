package com.project.services;

import com.project.dto.UserDto;
import com.project.exceptions.PositionNotFoundException;
import com.project.exceptions.UserNotFoundException;
import com.project.models.User;
import com.project.models.Position;
import com.project.repositories.PositionRepository;
import com.project.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PositionRepository positionRepository;

    /**
     * Get a paginated list of users as DTOs.
     *
     * @param page     Page number (starting from 1)
     * @param pageSize Number of users per page
     * @return Paginated list of users as DTOs
     */
    public Page<UserDto> getAllUsers(int page, int pageSize) {
        log.info("Get users on page {} with page size {}", page, pageSize);
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        return userRepository.findAll(pageRequest).map(this::mapToUserDto);
    }

    /**
     * Create a new user.
     *
     * @param userDto User DTO
     */
    @Transactional
    public void createNewUser(UserDto userDto) {
        if (userDto == null || userDto.getPositionId() == null) {
            throw new IllegalArgumentException("UserDto or PositionId cannot be null");
        }
        Position position = positionRepository
                .findById(userDto.getPositionId())
                .orElseThrow(() -> new PositionNotFoundException("Position not found"));

        User user = User.builder()
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .position(position)
                .date(new Date())
                .build();

        userRepository.save(user);
    }

    /**
     * Delete a user by ID.
     *
     * @param id User ID
     */
    @Transactional
    public void deleteUserById(Long id) {
        log.info("Deleting user with ID: {}", id);
        userRepository.deleteById(id);
    }

    /**
     * Get a user by ID.
     *
     * @param id User ID
     * @return User DTO
     */
    public UserDto getUserById(Long id) {
        log.info("Fetching user by ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return mapToUserDto(user);
    }

    /**
     * Update user information.
     *
     * @param id      User ID
     * @param userDto User DTO with updated information
     */
    @Transactional
    public void updateUser(Long id, UserDto userDto) {
        log.info("Updating user with ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Position position = positionRepository
                .findById(userDto.getPositionId())
                .orElseThrow(() -> new PositionNotFoundException("Position not found"));

        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPosition(position);

        userRepository.save(user);
    }

    // Helper method to map User to UserDto
    private UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .positionId(user.getPosition().getId())
                .id(user.getId())
                .date(user.getDate())
                .build();
    }
}
