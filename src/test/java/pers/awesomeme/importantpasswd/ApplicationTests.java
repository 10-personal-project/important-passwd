package pers.awesomeme.importantpasswd;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.awesomeme.importantpasswd.s30_process.QuestionService;

@SpringBootTest
class ApplicationTests
{
    @Autowired
    private QuestionService questionService;

    @Test
    void test1()
    {
        questionService.generateEncryptContent();
    }
}
