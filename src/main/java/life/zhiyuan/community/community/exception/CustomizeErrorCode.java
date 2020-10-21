package life.zhiyuan.community.community.exception;

/**
 * Created by zhuzhiwen by 2020/10/16 19:57
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND(2001,"您找的问题已经不存在啦，可以尝试在换一个试试(￢‸￢) ?"),
    TARGET_PARAM_NOT_FOUND(2002,"没有发现你选中了哪个问题和回复进行回复╮（╯＿╰）╭"),
    NO_LOGIN(2003,"当前进行的操作需要登陆，请登陆后重试(°__°〃)"),
    SYS_ERROR(2004,"服务器冒烟了，请稍后再次尝试w(ﾟДﾟ)w"),
    TYPE_PARAM_NOT_WRONG(2005,"评论类型错误或不存在(⊙ˍ⊙)"),
    COMMENT_NOT_FOUND(2006,"回复的评论并不存在啦，可以尝试在换一个试试(￢‸￢) ?"),
    COMMENT_IS_EMPTY(2007,"根本没有输入东西╮(╯▽╰)╭"),
    READ_NOTIFICATION_FALL(2008,"兄弟您这是都别人的信息呢(￢‸￢) ?"),
    NOTIFICATION_NOT_FOUND(2009,"消息莫非不翼而飞了w(ﾟДﾟ)w")
    ;
    private String message;
    private Integer code;

    CustomizeErrorCode(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
