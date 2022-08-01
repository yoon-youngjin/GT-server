package dev.yoon.gridgetest.infra.email;

import dev.yoon.gridgetest.global.error.exception.ErrorCode;
import dev.yoon.gridgetest.infra.email.exception.FailedToSendSmsException;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {

    @Value("${sms.api-key}")
    private String API_KEY;

    @Value("${sms.api-secret}")
    private String API_SECRET;

    @Override
    public String sendSms(String to) {

        Message message = new Message(API_KEY, API_SECRET);
        HashMap<String, String> params = new HashMap<>();

        String authCode = createKey();

        params.put("to", to);
        params.put("from", "01064145948");
        params.put("type", "SMS");
        params.put("text", authCode);

        try {
            message.send(params);
            return authCode;
        } catch (CoolsmsException e) {
            throw new FailedToSendSmsException(ErrorCode.FAILED_TO_SEND_SMS);
        }

    }

    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 6; i++) { // 인증코드 6자리
            key.append((rnd.nextInt(10)));
        }

        return key.toString();
    }



}
