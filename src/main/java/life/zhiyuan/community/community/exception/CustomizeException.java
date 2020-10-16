package life.zhiyuan.community.community.exception;

/**
 * Created by zhuzhiwen by 2020/10/16 19:32
 */
public class CustomizeException extends RuntimeException{
    private String message;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }
    public CustomizeException(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
