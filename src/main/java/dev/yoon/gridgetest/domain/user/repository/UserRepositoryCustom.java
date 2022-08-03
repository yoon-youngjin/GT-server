package dev.yoon.gridgetest.domain.user.repository;

import dev.yoon.gridgetest.domain.admin.dto.GetUserInfoDto;
import dev.yoon.gridgetest.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {

    Page<User> findAllUsersByQuery(Pageable pageable, GetUserInfoDto.Request request);

}
