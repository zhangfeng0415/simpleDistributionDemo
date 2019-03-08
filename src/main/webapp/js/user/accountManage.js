/**
 * Created by Taylover on 2018/1/22.
 */

$(document).ready(function () {
    $('#change').click(function () {
        $.ajax({
            type:'post',
            url:'../user/password/reset',
            data:{"oldPassword":$('#oldPassword').val(),"newPassword":$('#newPassword').val(),"reapeatPassword":$('#reapeatPassword').val()},
            success:function (data) {
                if(data.code==0){
                    alert(data.message);
                    $('#pwdModify').modal('hide');
                    $('#oldPassword').val("");
                    $('#newPassword').val("");
                    $('#reapeatPassword').val("");
                }
                else {
                    alert(data.message)
                    $('#oldPassword').val("");
                    $('#newPassword').val("");
                    $('#reapeatPassword').val("");
                }
            }
        })
    })

    $('#exit').click(function () {
        if(confirm("是否要退出登录?")){
            $.ajax({
                type:'get',
                url:'../loginout',
                success:function (data) {
                    if(data.code==0){
                        alert(data.message);
                        window.location.href = "../index.html"
                    }
                    else {
                        alert(data.message);
                    }
                }
            })
        }
    })

});