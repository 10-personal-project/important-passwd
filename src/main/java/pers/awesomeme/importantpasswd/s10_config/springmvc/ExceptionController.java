package pers.awesomeme.importantpasswd.s10_config.springmvc;

import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.awesomeme.commoncode.ApiResp;

@Slf4j
@ControllerAdvice
public class ExceptionController
{
    /**
     * 全局异常捕获
     * @param e 异常
     * @return 异常转换为的返回给前端的数据
     */
    @ResponseBody
    @ExceptionHandler({Exception.class})
    public ApiResp<String> cacheException(Exception e)
    {
        log.info("全局捕获异常【{}】", ExceptionUtil.stacktraceToOneLineString(e));
        return ApiResp.fail(ApiResp.StatusCode.GLOBAL_EXCEPTION, e.getMessage());
    }
}
