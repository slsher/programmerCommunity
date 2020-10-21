package life.zhiyuan.community.community.enums;

/**
 * Created by zhuzhiwen by 2020/10/21 16:00
 */
public enum  NotificationStatusEnum {
    UNREAD(0),READ(1)
    ;
    private int status;

    NotificationStatusEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
