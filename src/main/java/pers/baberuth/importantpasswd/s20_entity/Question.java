package pers.baberuth.importantpasswd.s20_entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Question
{
    /**
     * 问题1答案
     */
    private String nickname;

    /**
     * 问题2答案
     */
    private String deskmateName;

    /**
     * 问题3答案
     */
    private String peopleName;

    /**
     * 问题4答案
     */
    private String peopleTsName;
}
