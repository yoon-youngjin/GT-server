package dev.yoon.gridgetest.domain.message.domain;

import dev.yoon.gridgetest.domain.base.BaseTimeEntity;
import dev.yoon.gridgetest.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "MESSAGE")
@Getter
@NoArgsConstructor
public class Message extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MESSAGE_ID")
    private Long Id;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "FROM_USER_ID", nullable = false)
    private User from;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "FROM_TO_ID", nullable = false)
    private User to;

    @Column(nullable = false)
    private String content;

    @Builder
    public Message(User from, User to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    public static Message createMessage(User from, User to, String content) {

        return Message.builder()
                .from(from)
                .to(to)
                .content(content)
                .build();
    }


}
