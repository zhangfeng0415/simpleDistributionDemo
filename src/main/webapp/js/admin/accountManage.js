/**
 * Created by Taylover on 2018/1/22.
 */

$(document).ready(function () {
    $('#change').click(function () {
        $.ajax({
            type:'post',
            url:'../admin/password/reset',
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

    $('#table').bootstrapTable({
        url: "../admin/getUser",
        pagination: false,
        pageNumber: 1, //初始化加载第一页，默认第一页
        pageSize: 5, //每页的记录行数（*）
        pageList: [5, 10, 15],//可供选择的每页行数
        paginationLoop: true,
        sidePagination: 'server',//采用服务器端分页
        queryParamType: "",
        responseHandler: function (data) {
            if (0 != data.code) {
                return null;
            }
            return {
                total: data.body.length,
                rows: data.body
            };
        },
        columns: [
            {
                field: 'number',//域值
                title: '用户账号',//标题
                align: 'center',
                valign: 'middle',
                visible: true,
                width : '5%'
            },
            {
                field: 'name',//域值
                title: '用户姓名',//标题
                align: 'center',
                valign: 'middle',
                visible: true,
                width : '5%'
            },
            {
                title:'操作',
                align: 'center',
                valign: 'middle',
                events:operateEvents,
                visible: true,
                width : '5%',
                formatter: operateFormatter
            }

            ]
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

function operateFormatter(value, row, index) {
    if(row.enable===true){
        return ['<a class="stop"  href="javascript:void(0)" title="stop">',
            '停用',
            '</a>|',
            '<a class="delete"  href="javascript:void(0)" title="delete">',
            '删除',
            '</a>'].join('');
    }
    else if (row.enable===false){
        return ['<a class="start"  href="javascript:void(0)" title="stop">',
            '启用',
            '</a>|',
            '<a class="delete"  href="javascript:void(0)" title="delete">',
            '删除',
            '</a>'].join('');
    }
}


window.operateEvents={
    'click .start':function (e,value,row,index){
        $.ajax({
            type:'put',
            url:'../admin/user/start/' + row.id,
            success:function (data) {
                if(data.code==0){
                    alert(data.message)
                    $('#table').bootstrapTable('refresh')
                }
            }
        })
    },
    'click .stop':function (e,value,row,index){
        $.ajax({
            type:'put',
            url:'../admin/user/stop/' + row.id,
            success:function (data) {
                if(data.code==0){
                    alert(data.message)
                    $('#table').bootstrapTable('refresh')
                }
            }
        })
    },
    'click .delete':function (e,value,row,index){
        if(confirm("确定要删除用户:"+row.name+"吗?")){
        $.ajax({
            type:'put',
            url:'../admin/user/delete/' + row.id,
            success:function (data) {
                if(data.code==0){
                    alert(data.message)
                    $('#table').bootstrapTable('refresh')
                }
            }
        })
        }
    }
}