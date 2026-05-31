package com.onlineelection.system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest(classes = SystemApplication.class)
class OnlineApplicationTests {

    @MockBean
    private JavaMailSender mailSender;

    @Test
    void contextLoads() {
    }
}