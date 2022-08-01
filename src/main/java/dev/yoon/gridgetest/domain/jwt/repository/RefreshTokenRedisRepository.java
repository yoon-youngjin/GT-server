package dev.yoon.gridgetest.domain.jwt.repository;

import dev.yoon.gridgetest.domain.jwt.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {

}
