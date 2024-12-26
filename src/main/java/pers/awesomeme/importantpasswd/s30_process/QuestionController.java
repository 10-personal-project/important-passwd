package pers.awesomeme.importantpasswd.s30_process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pers.awesomeme.commoncode.ApiResp;
import pers.awesomeme.importantpasswd.s20_entity.Question;

@RestController
public class QuestionController
{
    @Autowired
    private QuestionService service;

    @PostMapping("/getPasswd")
    public ApiResp<String> getPasswd(@RequestBody Question question)
    {
        return service.getPasswd(question);
    }
}
