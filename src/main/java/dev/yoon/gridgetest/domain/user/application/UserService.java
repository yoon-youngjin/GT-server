package dev.yoon.gridgetest.domain.user.application;

import dev.yoon.gridgetest.domain.user.model.Email;
import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.domain.user.model.Nickname;
import dev.yoon.gridgetest.domain.user.repository.UserRepository;
import dev.yoon.gridgetest.domain.user.validator.UserValidator;
import dev.yoon.gridgetest.global.error.exception.EntityNotFoundException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;

    public User register(User user) {
        validateRegisterMember(user);
        return userRepository.save(user);
    }

    private void validateRegisterMember(User user) {
        userValidator.validateDuplicateUser(user.getPhoneNumber(), user.getNickname());
    }

    public Optional<User> getUserBySocialId(String socialId) {
        return userRepository.findBySocialId(socialId);
    }

    public User getUserByPhoneNumber(String phone) {
        return userRepository.findByPhoneNumber(phone)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    public Boolean existsUser(String phone) {
        return userRepository.existsByPhoneNumber(phone);
    }

    public void existsUserByNickname(Nickname nickname) {
        userValidator.validateDuplicateNickname(nickname);
    }

    public User getUserByNicknameOrPhone(String id) {
        return userRepository.findByNicknameOrPhoneNumber(id, id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));

    }


//    @Transactional
//    public User updateUser(Email email, User updateUser) {
//
//        userValidator.validateDuplicateNickname(updateUser.getNickname());
//
//        User savedUser = getUserByEmail(email);
//        savedUser.update(updateUser);
//
//        return savedUser;
//
//    }
//
//    @Transactional
//    public void deleteUser(Email email) {
//        User savedUser = getUserByEmail(email);
//        savedUser.quit();
//    }
}
