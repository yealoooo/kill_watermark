package cn.video;

import groovy.lang.GroovyClassLoader;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

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
