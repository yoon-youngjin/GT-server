package dev.yoon.gridgetest.domain.message.application;

import dev.yoon.gridgetest.domain.message.domain.Message;
import dev.yoon.gridgetest.domain.message.dto.GetConversationDto;
import dev.yoon.gridgetest.domain.message.dto.SendMessageReq;
import dev.yoon.gridgetest.domain.message.exception.CantSendMeException;
import dev.yoon.gridgetest.domain.message.repository.MessageRepository;
import dev.yoon.gridgetest.domain.user.application.UserService;
import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;
//    private final FcmService fcmService;

    @Transactional
    public void sendMessage(SendMessageReq request, String phone) {

        User from = userService.getUserByPhoneNumber(phone);
        User to = userService.getUserById(request.getToUserId());

        if (from.getId() == to.getId()) {
            throw new CantSendMeException(ErrorCode.CANT_SEND_ME_MESSAGE);
        }

        Message message = Message.createMessage(from, to, request.getContent());
        messageRepository.save(message);
    }


    public Slice<GetConversationDto.Response> getMessages(String phone, GetConversationDto.Request request, Pageable pageable) {
        User me = userService.getUserByPhoneNumber(phone);
        User other = userService.getUserById(request.getUserId());

        Slice<Message> messages = messageRepository.findAllByFromOrTo(me, other, pageable);

        List<GetConversationDto.Response> res = messages.stream()
                .map(GetConversationDto.Response::from).collect(Collectors.toList());

        return new SliceImpl<>(res, pageable, messages.hasNext());
    }


}
