package life.zhiyuan.community.community.enums;

/**
 * Created by zhuzhiwen by 2020/10/21 15:52
 * 判断回复的时问题还是评论
 */
public enum NotificationTypeEnum {
    REPLAY_QUESTION(1, "回复了问题"),
    REPLAY_COMMENT(2, "回复了评论");
    private int type;
    private String name;


    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }


    NotificationTypeEnum(int status, String name) {
        this.type = status;
        this.name = name;
    }

    public static String nameOfType(int type) {
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if (notificationTypeEnum.getType() == type) {
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }
}
