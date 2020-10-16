package life.zhiyuan.community.community.exception;

/**
 * Created by zhuzhiwen by 2020/10/16 19:57
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND("您找的问题已经不存在啦，可以尝试在换一个试试(￢‸￢) ?");
    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
