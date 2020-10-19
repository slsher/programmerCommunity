/**
 *Created by zhuzhiwen by 2020/10/17 23:14
 */

function post() {
    var questionId = $("#question_id").val();
    let content = $("#comment_content").val();

    if (!content){
        alert("回复空内容不能为空╮(╯▽╰)╭")
        return
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": questionId,
            "content": content,
            "type": 1
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=d0d3ade7e00a78d3000f&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                    }
                } else {
                    alert(response.message);
                }
            }
            // console.log(response)
        },
        dataType: "json"
    })
    /*console.log(questionId);
    console.log(content)*/
}