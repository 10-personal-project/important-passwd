package pers.awesomeme.importantpasswd.s30_process;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import org.springframework.stereotype.Service;
import pers.awesomeme.commoncode.ApiResp;
import pers.awesomeme.importantpasswd.s20_entity.Question;

import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class QuestionService
{
    /**
     * 获取加密密码
     * @param question 前端数据，密钥.
     * @return 加密密码
     */
    public ApiResp<String> getPasswd(Question question)
    {
        // 检查参数
        Assert.notNull(question, "必须提供参数");
        Assert.notBlank(question.getNickname(), "必须提供参数");
        Assert.notBlank(question.getDeskmateName(), "必须提供参数");
        Assert.notBlank(question.getPeopleName(), "必须提供参数");
        Assert.notBlank(question.getPeopleTsName(), "必须提供参数");

        // 读取密文
        byte[] encryptContent;
        try(InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("importantPasswdSecretText");)
        {
            encryptContent = inputStream.readAllBytes();
        }
        catch (Exception e)
        {
            return ApiResp.fail(ApiResp.StatusCode.FAIL, "读取密文文件出错【{}】", ExceptionUtil.stacktraceToOneLineString(e));
        }

        // 获取密钥
        byte[] key = this.getKey(StrUtil.format("{}{}{}{}", question.getNickname(), question.getDeskmateName(), question.getPeopleName(), question.getPeopleTsName()));

        // 解密数据
        try
        {
            AES aes = SecureUtil.aes(key);
            String content = aes.decryptStr(encryptContent);
            return ApiResp.successData(content);
        }
        catch (Exception e)
        {
            return ApiResp.fail(ApiResp.StatusCode.FAIL, "回答不正确");
        }
    }

    /**
     * 获取密钥
     * @param keyStr 用来生成密钥字符串，可以把它等同于密钥.
     * @return 密钥
     */
    public byte[] getKey(String keyStr)
    {
        MessageDigest dig;
        try
        {
            dig = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e.getMessage());
        }
        byte[] key = dig.digest(keyStr.getBytes(CharsetUtil.CHARSET_UTF_8));
        SecretKeySpec secKey = new SecretKeySpec(key, SymmetricAlgorithm.AES.getValue());
        return secKey.getEncoded();
    }

    /**
     * 生成密文文档 <br/>
     * 本地调用备用，不要删除.
     */
    public void generateEncryptContent()
    {
        // 三个文件路径
        String plaintextPasswordFilePath = "/Users/awesome/my/download/00.暂时不要删除/重要密码明文_用完删除.txt";
        String secretKeyFilePath = "/Users/awesome/my/download/00.暂时不要删除/密钥_用完删除.txt";
        String destFilePath = "/Users/awesome/my/download/00.暂时不要删除/importantPasswdSecretText";

        // 获取密钥钥
        byte[] secretKey = this.getKey(FileUtil.readString(secretKeyFilePath, CharsetUtil.UTF_8));

        // 加密密码明文
        AES aes = SecureUtil.aes(secretKey);
        byte[] encryptData = aes.encrypt(FileUtil.readBytes(plaintextPasswordFilePath));
        FileUtil.writeBytes(encryptData, destFilePath);
    }
}
