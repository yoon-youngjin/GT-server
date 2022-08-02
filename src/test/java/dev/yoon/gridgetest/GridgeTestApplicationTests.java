package dev.yoon.gridgetest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class GridgeTestApplicationTests {

    @Test
    void contextLoads() {
    }

//    @Test
//    void sendMessage() {
//        String api_key = "NCS3MWXO5L4JQZTH";
//        String api_secret = "QFNUNIXRGD13HS0H23I0MKGCL9LVLP2N";
//
//        Message coolsms = new Message(api_key, api_secret);
//        HashMap<String, String> params = new HashMap<>();
//
//        params.put("to", "01064145948");
//        params.put("from", "01064145948");
//        params.put("type", "SMS");
//        params.put("text", "내용");
//
//        try {
//            coolsms.send(params);
//        } catch (CoolsmsException e) {
//            e.printStackTrace();
//        }
//    }


}
