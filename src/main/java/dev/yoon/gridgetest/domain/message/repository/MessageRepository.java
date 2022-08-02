package dev.yoon.gridgetest.domain.message.repository;

import dev.yoon.gridgetest.domain.message.domain.Message;
import dev.yoon.gridgetest.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "select m " +
            "from Message m " +
            "where (m.from=:from and m.to=:to) or (m.from=:to and m.to=:from)" +
            "order by m.createTime desc ")
    Slice<Message> findAllByFromOrTo(User from, User to, Pageable pageable);


}
