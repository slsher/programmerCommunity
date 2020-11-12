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
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            /**展开二级评论*/
            comments.addClass("in");
            // console.log(id)
            /**标记二级评论展开状态*/
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                // console.log(data);
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }))

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    })).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format("YYYY-MM-DD  h:mm:ss a")
                    }));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement)
                        .append(mediaBodyElement)

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement)

                    subCommentContainer.prepend(commentElement);
                });
                /**展开二级评论*/
                comments.addClass("in");
                // console.log(id)
                /**标记二级评论展开状态*/
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");

            });
        }
    }
}

$(document).ready(function (){
    $("#tag").focus(function (){
        $("#select-tag").css("display","block")
    })
    $("#select-tag").blur(function (){
        $("#select-tag").css("display","none")
    })
})

/**展示标签选择*/
function showSelectTag(){

}
/**发布标签选择*/
function selectTag(e) {
    var value=e.getAttribute("data-tag")
    var previous = $("#tag").val();
    /**判断是否存在相同的标签*/
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value)
        } else {
            $("#tag").val(value)
        }
    }
}

/** 后台控制管理器*/
/** 用户删除*/
function deleteUser(userId){
    var userModel=document.getElementById("userModel");
    userModel.innerHTML=`<div class="modal-dialog" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal"
                                                            aria-label="Close"><span aria-hidden="true">&times;</span>
                                                    </button>
                                                    <h4 class="modal-title" id="myModalLabel">删除问题</h4>
                                                </div>
                                                <div class="modal-body">
                                                    确定要删除这个问题吗？
                                                </div>
                                                <div class="modal-footer">
                                                    <a class="btn btn-danger" style="background: red"
                                                       href="/admin/users/`+ userId+` ">删除</a>
                                                    <a class="btn btn-default" style="background: white"
                                                       data-dismiss="modal">我点错了</a>
                                                </div>
                                            </div>
                                        </div>`
}
/** 问题删除*/
function deleteQuestion(questionId){
    // console.log(questionId)
    var questionModel=document.getElementById("questionModal");
    questionModel.innerHTML=`<div class="modal-dialog" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal"
                                                            aria-label="Close"><span aria-hidden="true">&times;</span>
                                                    </button>
                                                    <h4 class="modal-title" id="myModalLabel">删除问题</h4>
                                                </div>
                                                <div class="modal-body">
                                                    确定要删除这个问题吗？
                                                </div>
                                                <div class="modal-footer">
                                                    <a class="btn btn-danger" style="background: red"
                                                       href="/admin/questions/`+ questionId+` ">删除</a>
                                                    <a class="btn btn-default" style="background: white"
                                                       data-dismiss="modal">我点错了</a>
                                                </div>
                                            </div>
                                        </div>`
}
/**删除评论*/
function deleteComment(commentId){
    var commentModel=document.getElementById("commentModel");
    commentModel.innerHTML=`<div class="modal-dialog" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal"
                                                            aria-label="Close"><span aria-hidden="true">&times;</span>
                                                    </button>
                                                    <h4 class="modal-title" id="myModalLabel">删除问题</h4>
                                                </div>
                                                <div class="modal-body">
                                                    确定要删除这个问题吗？
                                                </div>
                                                <div class="modal-footer">
                                                    <a class="btn btn-danger" style="background: red"
                                                       href="/admin/comments/`+ commentId+` ">删除</a>
                                                    <a class="btn btn-default" style="background: white"
                                                       data-dismiss="modal">我点错了</a>
                                                </div>
                                            </div>
                                        </div>`
}
/**删除通知*/
function deleteNotification(notificationId){
    var notificationModel=document.getElementById("notificationModel");
    notificationModel.innerHTML=`<div class="modal-dialog" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal"
                                                            aria-label="Close"><span aria-hidden="true">&times;</span>
                                                    </button>
                                                    <h4 class="modal-title" id="myModalLabel">删除问题</h4>
                                                </div>
                                                <div class="modal-body">
                                                    确定要删除这个问题吗？
                                                </div>
                                                <div class="modal-footer">
                                                    <a class="btn btn-danger" style="background: red"
                                                       href="/admin/notification/`+ notificationId+` ">删除</a>
                                                    <a class="btn btn-default" style="background: white"
                                                       data-dismiss="modal">我点错了</a>
                                                </div>
                                            </div>
                                        </div>`
}




