$(document).ready(function () {
    let reg = /windows/i;
    if (reg.test(navigator.userAgent)) {
        $('#forBackup').prop('disabled', true);
        $('#forBackup').hide();
        $('#bk-modal').hide();
        $('#bk-modal').prop('disabled',true);
    }
    $('#table').bootstrapTable({
        url: '../admin/projectBox/select',
        pagination: true,
        pageNumber: 1, //初始化加载第一页，默认第一页
        pageSize: 5, //每页的记录行数（*）
        pageList: [5, 10, 15],//可供选择的每页行数
        paginationLoop: true,
        sortOrder: "desc",
        sidePagination: 'server',//采用服务器端分页
        responseHandler: function (data) {
            if (0 != data.code) {
                return null;
            }
            return {
                total: data.body.totalElements,
                rows: data.body.content
            };
        },
        queryParamsType: function (params) {
            console.log(params);
            return {
                pageSize: params.limit,   //页面大小
                pageNumber: params.pageNumber,  //页码
            }
        },
        columns: [
            {
                field: 'projectName',//域值
                title: '项目名称',//标题
                align: 'center',
                valign: 'middle',
                visible: true,
                width: '5%'
            },
            {
                field: 'boxNumber',//域值
                title: '档号',//标题
                align: 'center',
                valign: 'middle',
                visible: true,
                width: '5%'
            },
            {
                field: 'totalNumber',
                title: '文件总数',
                align: 'center',
                valign: 'middle',
                visible: true,
                width: '5%'
            },
            {
                title: '操作',
                align: 'center',
                valign: 'middle',
                events: operateEvents,
                visible: true,
                width: '5%',
                formatter: operateFormatter
            }]
    });
})

$('#forBackup').click(function () {
    let filepath = $('#path').val();
    let file = $('#backup-table').bootstrapTable('getSelections');
    if (filepath) {
        if (file.length==1){
            projectBackup(filepath,file[0].projectName,0);
        }
        else if (file.length==2){
            projectBackup(filepath,file[0].projectName,1);
            projectBackup(filepath,file[1].projectName,2);
        }
        else if (file.length<1){
            alert("请选择备份档案！");
        }
        else {
            for(let i = 0;i<file.length;i++){
                if (i==0){
                    projectBackup(filepath,file[0].projectName,1);
                }
                else if (i==file.length-1){
                    projectBackup(filepath,file[i].projectName,2,i+1)
                }
                else {
                    projectBackup(filepath,file[i].projectName,3)
                }
            }
        }
    }
    else {
        alert("请填写备份路径！")
    }
});

var projectStr = "项目：";

function projectBackup(filepath,filename,type,cursor){
    if (type==0){
        //只有一个文件
        $.ajax({
            type: 'post',
            url: '../admin/projectDocument/backups',
            data: {'destPath': filepath,'projectName':filename},
            beforeSend: function () {
                ShowDiv();
            },
            complete: function () {
                HiddenDiv();
            },
            success: function (data) {
                if (data.code == 0) {
                    alert("项目："+filename+" "+data.message);
                    $('#backup').modal('hide');
                    $('#path').val('')
                }
                else {
                    alert(data.message);
                }
            }
        })
    }
    else if (type==1){
        projectStr += filename +"、";
        //起始文件
        $.ajax({
            type: 'post',
            url: '../admin/projectDocument/backups',
            data: {'destPath': filepath,'projectName':filename},
            beforeSend: function () {
                ShowDiv();
            },
            success: function (data) {
                if (data.code != 0) {
                    alert("项目 "+filename+" 备份出现错误："+data.message);
                }
            }
        })
    }
    else if (type==2){
        projectStr += filename +"\n\n";
        //结束文件
        $.ajax({
            type: 'post',
            url: '../admin/projectDocument/backups',
            data: {'destPath': filepath,'projectName':filename},
            complete: function () {
                HiddenDiv();
            },
            success: function (data) {
                if (data.code == 0) {
                    alert(projectStr  +"共计"+cursor+"份项目"+ data.message  );
                    projectStr = "项目：";
                    $('#backup').modal('hide');
                    $('#path').val('')
                }
                else {
                    alert("项目 "+filename+" 备份出现错误："+data.message);
                }
            }
        })
    }
    else if (type==3){
        //中间文件
        projectStr += filename +"、";
        $.ajax({
            type: 'post',
            url: '../admin/projectDocument/backups',
            data: {'destPath': filepath,'projectName':filename},
            success: function (data) {
                if (data.code != 0) {
                    alert("项目 "+filename+" 备份出现错误："+data.message);
                }
            }
        })
    }

}

$('#bk-modal').click(function () {
    $('#backup-table').bootstrapTable('destroy');
    $('#backup').modal({backdrop: 'static', keyboard: false});
    $('#backup-table').bootstrapTable({
        url:'../admin/projectBox/selectProjectNameList',
        pagination: true,
        pageNumber: 1, //初始化加载第一页，默认第一页
        pageSize: 5, //每页的记录行数（*）
        pageList: [5, 10, 15],//可供选择的每页行数
        sidePagination: 'client',
        columns:[
            {
                checkbox:true,
                width:'1%',
                align: 'center',
                valign: 'middle',
                visible: true
            },
            {
                field: 'projectName',//域值
                title: '项目名称',//标题
                align: 'center',
                valign: 'middle',
                visible: true,
                width: '5%'
            },
            {
                field: 'boxNumber',//域值
                title: '档号',//标题
                align: 'center',
                valign: 'middle',
                visible: true,
                width: '5%'
            },
            {
                field: 'totalNumber',
                title: '文件总数',
                align: 'center',
                valign: 'middle',
                visible: true,
                width: '5%'
            },
        ]

    })
})

$('#backup-search').click(function () {
    let name = encodeURIComponent($('#backup-name').val());
    $('#backup-table').bootstrapTable('destroy');
    $('#backup-table').bootstrapTable({
        url:'../admin/projectBox/selectProjectNameListByName?projectName=' + name,
        pagination: true,
        pageNumber: 1, //初始化加载第一页，默认第一页
        pageSize: 5, //每页的记录行数（*）
        pageList: [5, 10, 15],//可供选择的每页行数
        sidePagination: 'client',
        columns:[
            {
                checkbox:true,
                width:'1%',
                align: 'center',
                valign: 'middle',
                visible: true
            },
            {
                field: 'projectName',//域值
                title: '项目名称',//标题
                align: 'center',
                valign: 'middle',
                visible: true,
                width: '5%'
            },
            {
                field: 'boxNumber',//域值
                title: '档号',//标题
                align: 'center',
                valign: 'middle',
                visible: true,
                width: '5%'
            },
            {
                field: 'totalNumber',
                title: '文件总数',
                align: 'center',
                valign: 'middle',
                visible: true,
                width: '5%'
            }]
    })
})

//显示加载数据
function ShowDiv() {
    $("#loading").show();
    $('button').prop('disabled', true)
}

//隐藏加载数据
function HiddenDiv() {
    $("#loading").hide();
    $('button').prop('disabled', false)
}

$('#addBox').click(function () {
    var projectName = $('#projectName').val();
    var boxNumber = $('#boxNum').val();
    if (projectName !== "" && boxNumber !== "") {
        $.ajax({
            type: 'post',
            url: '../admin/projectBox/add',
            data: {'projectName': projectName, 'boxNumber': boxNumber, 'totalNumber': $('#totalFileNum').val()},
            success: function (data) {
                if (data.code == 0) {
                    alert(data.message);
                    $('#table').bootstrapTable('refresh');
                    $('#add').modal('hide');
                    $('#boxNum').val('');
                    $('#totalFileNum').val('');
                }
                else {
                    alert(data.message);
                }
            }
        })
    }
    else {
        alert("请补全信息！");
    }
})


$('#search').click(function () {
    var con = $("#query").val();
    var str = "";
    if (con == "1") {
        str += "projectName="
    }
    else if (con == "2") {
        str += "boxNumber="
    }
    $('#table').bootstrapTable('destroy')
    $('#table').bootstrapTable({
        url: '../admin/projectBox/select?' + str + encodeURIComponent($('#con').val()),
        pagination: true,
        pageNumber: 1, //初始化加载第一页，默认第一页
        pageSize: 5, //每页的记录行数（*）
        pageList: [5, 10, 15],//可供选择的每页行数
        paginationLoop: true,
        sortOrder: "desc",
        sidePagination: 'server',//采用服务器端分页
        responseHandler: function (data) {
            if (0 != data.code) {
                return null;
            }
            return {
                total: data.body.totalElements,
                rows: data.body.content
            };
        },
        queryParamsType: function (params) {
            console.log(params);
            return {
                pageSize: params.limit,   //页面大小
                pageNumber: params.pageNumber,  //页码
            }
        },
        columns: [
            {
                field: 'projectName',//域值
                title: '项目名称',//标题
                align: 'center',
                valign: 'middle',
                visible: true,
                width: '5%'
            },
            {
                field: 'boxNumber',//域值
                title: '档号',//标题
                align: 'center',
                valign: 'middle',
                visible: true,
                width: '5%'
            },
            {
                field: 'totalNumber',
                title: '文件总数',
                align: 'center',
                valign: 'middle',
                visible: true,
                width: '5%'
            },
            {
                title: '操作',
                align: 'center',
                valign: 'middle',
                events: operateEvents,
                visible: true,
                width: '5%',
                formatter: operateFormatter
            }]
    })
})

$('#modify').click(function () {
    $.ajax({
        type: 'post',
        url: '../admin/projectBox/update',
        data: {
            'id': $('#boxId').val(),
            'projectName': $('#projectName-change').val(),
            'boxNumber': $('#boxNum-change').val(),
            'totalNumber': $('#totalFileNum-change').val()
        },
        success: function (data) {
            if (data.code == 0) {
                alert(data.message);
                $('#table').bootstrapTable('refresh');
                $('#change').modal('hide');
                $('#projectName-change').val('');
                $('#boxNum-change').val('');
                $('#totalFileNum-change').val('');
            }
            else {
                alert(data.message);
            }
        }
    })
})


function operateFormatter(value, row, index) {
    return [
        '<a class="change"  href="javascript:void(0)" >',
        '修改',
        '</a>|',
        '<a class="remove" href="javascript:void(0);"  >',
        '删除',
        '</a><br>',
        '<a class="browse"  href="javascript:void(0)" >',
        '查看档案',
        '</a>|',
        '<a href="../admin/projectDocument/export?projectBoxId=' + row.id + '" >',
        '导出Excel',
        '</a>'
    ].join('');
}

window.operateEvents = {
    'click .change': function (e, value, row, index) {
        $('#change').modal();
        $('#boxId').val(row.id);
        $('#projectName-change').val(row.projectName);
        $('#boxNum-change').val(row.boxNumber);
        $('#totalFileNum-change').val(row.totalNumber);
    },
    'click .remove': function (e, value, row, index) {
        if (confirm("您确定要删除该工程档案盒吗？\n该盒子下的所有文件将被删除！")) {
            $.ajax({
                type: "DELETE",
                url: "../admin/projectBox/delete/" + row.id,
                success: function (data) {
                    $("#table").bootstrapTable("refresh");
                }
            });
        }
    },
    'click .browse': function (e, value, row, index) {
        window.location.href = 'projectManage.html?boxId=' + row.id + "&boxNum=" + encodeURIComponent(row.boxNumber) + '&pName=' + encodeURIComponent(row.projectName);
    }
}

$('#exit').click(function () {
    if (confirm("是否要退出登录?")) {
        $.ajax({
            type: 'get',
            url: '../loginout',
            success: function (data) {
                if (data.code == 0) {
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
