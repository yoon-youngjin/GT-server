package dev.yoon.gridgetest.domain.message.api;

import dev.yoon.gridgetest.domain.message.application.MessageService;
import dev.yoon.gridgetest.domain.message.dto.GetConversationDto;
import dev.yoon.gridgetest.domain.message.dto.SendMessageReq;
import dev.yoon.gridgetest.global.ApiResult;
import dev.yoon.gridgetest.global.resolver.UserPhone;
import dev.yoon.gridgetest.global.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static dev.yoon.gridgetest.global.util.Constants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/message")
public class MessageApi {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<ApiResult<Void>> sendMessage(
            @RequestBody @Valid SendMessageReq request,
            @UserPhone String phone
    ) {
        messageService.sendMessage(request, phone);
        return ResponseEntity.ok(ApiUtils.success(null, SEND_MESSAGE));

    }

    @GetMapping
    public ResponseEntity<ApiResult<Slice<GetConversationDto.Response>>> getMessagesByToUser(
            @Valid GetConversationDto.Request request,
            @UserPhone String phone,
            Optional<Integer> page
    ) {

        Pageable pageable = PageRequest.of(
                page.isPresent() ? page.get() : 0,
                SET_PAGE_ITEM_MAX_COUNT
        );

        Slice<GetConversationDto.Response> response = messageService.getMessages(phone, request, pageable);
        return ResponseEntity.ok(ApiUtils.success(response, GET_NOTIFICATION));



    }


}
