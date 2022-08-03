package dev.yoon.gridgetest.domain.follow.domain;

import dev.yoon.gridgetest.domain.follow.model.FollowState;
import dev.yoon.gridgetest.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "follow")
@Getter
@NoArgsConstructor
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "from_id", nullable = false)
    private User from;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "to_id", nullable = false)
    private User to;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FollowState followState;


    @Builder
    public Follow(User from, User to, FollowState followState) {
        this.from = from;
        this.to = to;
        this.followState = followState;
    }

    public static Follow createFollow(User from, User to, FollowState followState) {

        return Follow.builder()
                .from(from)
                .to(to)
                .followState(followState)
                .build();
    }


    public void updateState() {
        this.followState = FollowState.ACCEPT;
    }
}
