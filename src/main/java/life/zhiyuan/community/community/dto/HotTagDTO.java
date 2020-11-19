package life.zhiyuan.community.community.dto;

import lombok.Data;

/**
 * Created by zhuzhiwen by 2020/11/19 8:42
 */
@Data
public class HotTagDTO implements Comparable{
    private String name;
    private Integer priority;

    @Override
    public int compareTo(Object o) {
        return this.getPriority() - ((HotTagDTO) o).getPriority();
    }
}
