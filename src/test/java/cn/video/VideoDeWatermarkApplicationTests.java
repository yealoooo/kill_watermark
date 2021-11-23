package cn.video;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@Slf4j
class VideoDeWatermarkApplicationTests {

    @Test
    void contextLoads() throws IOException {


    }

    @Getter
    @Setter
    private static class Context {
        private int amount;
        private String nextScenario;
    }
}
