package life.zhiyuan.community.community.dto;

import java.util.List;

/**
 * Created by zhuzhiwen by 2020/10/20 20:31
 */
public class TagDTO {
    private String categoryName;
    private List<String> tags;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
