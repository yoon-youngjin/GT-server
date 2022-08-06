package dev.yoon.gridgetest.domain.answer.api;

import dev.yoon.gridgetest.domain.answer.application.AnswerService;
import dev.yoon.gridgetest.domain.answer.dto.CreateAnswerReq;
import dev.yoon.gridgetest.domain.answer.dto.CreateReplyReq;
import dev.yoon.gridgetest.domain.answer.dto.GetAnswerRes;
import dev.yoon.gridgetest.global.ApiResult;
import dev.yoon.gridgetest.global.resolver.UserPhone;
import dev.yoon.gridgetest.global.util.ApiUtils;
import dev.yoon.gridgetest.global.util.Constants;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static dev.yoon.gridgetest.global.util.Constants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class AnswerApi {

    private final AnswerService answerService;

    @Operation(summary = "댓글 생성")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @PostMapping("/{boardId}/answer")
    public ResponseEntity<ApiResult<Void>> createAnswer(
            @PathVariable Long boardId,
            @RequestBody @Valid CreateAnswerReq request,
            @UserPhone String phone
    ) {
        answerService.createAnswer(boardId, request, phone);
        return ResponseEntity.ok(ApiUtils.success(null, CREATE_ANSWER));

    }

    @Operation(summary = "댓글 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @GetMapping("/{boardId}/answer")
    public ResponseEntity<ApiResult<GetAnswerRes>> getAnswer(
            @PathVariable Long boardId,
            @UserPhone String phone,
            Optional<Integer> page

    ) {

        Pageable pageable = PageRequest.of(
                page.orElse(0),
                Constants.SET_PAGE_ITEM_MAX_COUNT
        );

        GetAnswerRes response = answerService.getAnswerByBoard(pageable, phone, boardId);
        return ResponseEntity.ok(ApiUtils.success(response, GET_ANSWER));

    }

    @Operation(summary = "대댓글 생성")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @PostMapping("/answer/{answerId}/reply")
    public ResponseEntity<ApiResult<Void>> createReply(
            @PathVariable Long answerId,
            @RequestBody @Valid CreateReplyReq request,
            @UserPhone String phone
    ) {
        answerService.createReply(answerId, request, phone);
        return ResponseEntity.ok(ApiUtils.success(null, CREATE_REPLY));

    }

    @Operation(summary = "댓글 좋아요 추가 또는 취소")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @PostMapping("/answer/{answerId}/like")
    public ResponseEntity<ApiResult<Void>> addOrDeleteLike(
            @PathVariable Long answerId,
            @UserPhone String phone
    ) {

        answerService.addOrDeleteLike(answerId, phone);
        return ResponseEntity.ok(ApiUtils.success(null, ADD_OR_DELETE_LIKE));
    }


    @Operation(summary = "댓글 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @DeleteMapping("/answer/{answerId}")
    public ResponseEntity<ApiResult<Void>> deleteAnswer(
            @PathVariable("answerId") Long answerId,
            @UserPhone String phone
    ){
        answerService.deleteAnswer(answerId, phone);
        return ResponseEntity.ok(ApiUtils.success(null, DELETE_ANSWER));
    }



}
