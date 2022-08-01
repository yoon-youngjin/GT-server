package dev.yoon.gridgetest.domain.authcode.repository;

import dev.yoon.gridgetest.domain.authcode.entity.AuthCode;
import org.springframework.data.repository.CrudRepository;

public interface AuthCodeRedisRepository extends CrudRepository<AuthCode, String> {

}
