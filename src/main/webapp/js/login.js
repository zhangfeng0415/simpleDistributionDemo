$(document).ready(function () {
    $("#login").click(function () {
        $.ajax({
            type: "post",
            url: "login",
            data:{"number":$('#username').val(),"password":$('#pwd').val()},
            success: function (data) {
                if(data.code==0){
                    if(data.body=="admin") {
                        window.location.href = './admin/officialBox.html';
                    }
                    else if(data.body=="user")
                    {
                        window.location.href = './user/officialBox.html';
                    }
                }
                else alert(data.message);
            }
        });
    })
})

