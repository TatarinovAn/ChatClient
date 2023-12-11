package ru.netology;


import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;;

class LoggerTest {
    @Test
    public void loggerTest() {
        String s = "Петя";
        Logger logger = Logger.getInstance();
        String res = logger.log(s);
        assertThat(res, not(equalTo(0)));
    }


}