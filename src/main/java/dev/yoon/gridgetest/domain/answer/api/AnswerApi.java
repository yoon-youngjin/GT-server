package dev.yoon.gridgetest.domain.answer.api;

import dev.yoon.gridgetest.domain.answer.application.AnswerService;
import dev.yoon.gridgetest.domain.answer.dto.CreateAnswerReq;
import dev.yoon.gridgetest.domain.answer.dto.CreateReplyReq;
import dev.yoon.gridgetest.domain.answer.dto.GetAnswerRes;
import dev.yoon.gridgetest.domain.answer.dto.ReportAnswerReq;
import dev.yoon.gridgetest.global.resolver.UserPhone;
import dev.yoon.gridgetest.global.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class AnswerApi {

    private final AnswerService answerService;


    @PostMapping("/{boardId}/answers")
    public ResponseEntity<Void> createAnswer(
            @PathVariable Long boardId,
            @RequestBody @Valid CreateAnswerReq request,
            @UserPhone String phone
    ) {

        answerService.createAnswer(boardId, request, phone);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/{boardId}/answers")
    public ResponseEntity<GetAnswerRes> getAnswer(
            @PathVariable Long boardId,
            @UserPhone String phone,
            Optional<Integer> page

    ) {

        Pageable pageable = PageRequest.of(
                page.isPresent() ? page.get() : 0,
                Constants.SET_PAGE_ITEM_MAX_COUNT
        );

        GetAnswerRes response = answerService.getAnswerByBoard(pageable, phone, boardId);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/answers/{answerId}/reply")
    public ResponseEntity<Void> createReply(
            @PathVariable Long answerId,
            @RequestBody @Valid CreateReplyReq request,
            @UserPhone String phone
    ) {
        answerService.createReply(answerId, request, phone);
        return ResponseEntity.ok().build();

    }

    @PostMapping("/answers/{answerId}/like")
    public ResponseEntity<Void> addOrDeleteLike(
            @PathVariable Long answerId,
            @UserPhone String phone
    ) {

        answerService.addOrDeleteLike(answerId, phone);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/answers/report")
    public ResponseEntity<Void> reportAnswer(
            @RequestBody @Valid ReportAnswerReq request,
            @UserPhone String phone
    ) {
        answerService.reportAnswer(request, phone);
        return ResponseEntity.ok().build();

    }

    @PatchMapping("/answers/{answerId}")
    public ResponseEntity<Void> deleteAnswer(
            @PathVariable("answerId") Long answerId,
            @UserPhone String phone
    ){
        answerService.deleteAnswer(answerId, phone);
        return ResponseEntity.ok().build();
    }






}
