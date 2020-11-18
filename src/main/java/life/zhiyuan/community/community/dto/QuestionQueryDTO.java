package life.zhiyuan.community.community.dto;

import lombok.Data;

/**
 * Created by zhuzhiwen by 2020/10/22 21:40
 */
@Data
public class QuestionQueryDTO {
    private String search;
    private String sort;
    private Long time;
    private String tag;
    private Integer page;
    private Integer size;
}
