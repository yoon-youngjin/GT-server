package dev.yoon.gridgetest.domain.follow.api;

import dev.yoon.gridgetest.domain.follow.application.FollowService;
import dev.yoon.gridgetest.domain.follow.dto.FollowUserReq;
import dev.yoon.gridgetest.domain.follow.dto.GetRequestFollowRes;
import dev.yoon.gridgetest.global.resolver.UserPhone;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow")
public class FollowApi {

    private final FollowService followService;

    @PostMapping
    public ResponseEntity<Void> follow(
            @RequestBody @Valid FollowUserReq request,
            @UserPhone String phone
    ) {

        followService.follow(request, phone);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<GetRequestFollowRes>> getRequestFollow(
            @UserPhone String phone
    ){
        List<GetRequestFollowRes> response = followService.getRequestFollow(phone);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{followId}/accept")
    public ResponseEntity<Void> acceptFollow(
            @PathVariable("followId") Long followId,
            @UserPhone String phone
    ){
        followService.acceptFollow(followId, phone);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{followId}/reject")
    public ResponseEntity<Void> rejectFollow(
            @PathVariable("followId") Long followId,
            @UserPhone String phone
    ){
        followService.rejectFollow(followId, phone);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{followerId}/cancel")
    public ResponseEntity<Void> cancelFollow(
            @PathVariable("followerId") Long followerId,
            @UserPhone String phone
    ){
        followService.cancelFollow(followerId, phone);
        return ResponseEntity.ok().build();

    }



}
