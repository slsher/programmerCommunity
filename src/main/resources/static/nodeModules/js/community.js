/**
 *Created by zhuzhiwen by 2020/10/17 23:14
 */

/*提交回复*/
function post() {
    var questionId = $("#question_id").val();
    let content = $("#comment_content").val();

    comment2target(questionId, 1, content);
    /*console.log(questionId);
    console.log(content)*/
}

/**封装方法*/
function comment2target(targetId, type, content) {

    if (!content) {
        alert("回复空内容不能为空╮(╯▽╰)╭")
        return
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=d0d3ade7e00a78d3000f&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
            // console.log(response)
        },
        dataType: "json"
    })
}

/**提交二级评论*/
function comment(e) {
    var commentId = e.getAttribute("data-id");
    let content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);
}

/**展开二级评论*/
function collapseComments(e) {
    // console.log(e)
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    /**获取一下二级评论展开状态*/
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        /**折叠二级评论*/
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        $.getJSON("/comment/" + id, function (data) {
            console.log(data);
            var commentBody = $("comment-body-" + id);
            var items = [];
            $.each(data.data, function (comment) {
                var c = $("<div/>", {
                    "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                    html: comment.content
                })
                items.push(c)
            });
            commentBody.append($("<div/>", {
                "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 collapse sub-comments",
                "id": "comment-" + id,
                html: items.join("")
            }))


            /**展开二级评论*/
            comments.addClass("in");
            // console.log(id)
            /**标记二级评论展开状态*/
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        });

    }
}