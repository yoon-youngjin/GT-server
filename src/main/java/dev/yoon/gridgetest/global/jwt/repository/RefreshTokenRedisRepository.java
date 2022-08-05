package dev.yoon.gridgetest.global.jwt.repository;

import dev.yoon.gridgetest.global.jwt.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {

}
