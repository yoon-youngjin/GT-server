package dev.yoon.gridgetest.domain.user.validator;

import dev.yoon.gridgetest.domain.user.exception.EmailDuplicateException;
import dev.yoon.gridgetest.domain.user.exception.NicknameDuplicateException;
import dev.yoon.gridgetest.domain.user.model.Nickname;
import dev.yoon.gridgetest.domain.user.repository.UserRepository;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void validateDuplicateUser(String phoneNumber, Nickname nickname) {
        validateDuplicatePhoneNumber(phoneNumber);
        validateDuplicateNickname(nickname.getValue());
    }

    public void validateDuplicatePhoneNumber(String phoneNumber) {
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new EmailDuplicateException(ErrorCode.DUPLICATE_PHONE_NUMBER);
        }

    }

    public void validateDuplicateNickname(String nickname) {
        if (userRepository.existsByNicknameValue(nickname)) {
            throw new NicknameDuplicateException(ErrorCode.DUPLICATE_NICKNAME);
        }
    }



}
