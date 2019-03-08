/**
 * Created by Taylover on 2018/1/15.
 */

var boxId = -1;
var boxNum = -1;
var projectName = "";

$(document).ready(function () {



    var address = document.location.toString();
    var str = address.split('?')[1].split('&');
    boxId = str[0].split('=')[1];
    boxNum = decodeURIComponent(str[1].split('=')[1]);
    projectName = decodeURIComponent(str[2].split('=')[1]);

    $('#table').bootstrapTable({
        url: "../user/projectDocument/select?projectBoxId=" + boxId,
        pagination: true,
        pageNumber: 1, //初始化加载第一页，默认第一页
        pageSize: 5, //每页的记录行数（*）
        pageList: [5, 10, 15],//可供选择的每页行数
        paginationLoop: true,
        sortOrder: "asc",
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
        queryParams: function queryParams(params) {   //设置查询参数
            return  param = {
                sortName : "id",
                pageSize: params.limit,   //页面大小
                pageNumber: (params.offset / params.limit) + 1,  //页码
            };
        },
        columns: [
            {
                field: 'boxNumber',//域值
                title: '档号',//标题
                align: 'center',
                valign: 'middle',
                visible: true,
                width: '5%'
            },
            {
                field: 'number',//域值
                title: '文件编号',//标题
                align: 'center',
                valign: 'middle',
                visible: true,
                width: '5%'
            },
            {
                field: 'responsiblePerson',
                title: '责任人',
                align: 'center',
                valign: 'middle',
                visible: true,
                width: '5%'
            },
            {
                field: 'title',
                title: '文件题名',
                align: 'center',
                valign: 'middle',
                visible: true,
                width: '5%'
            },
            {
                field: 'keepTime',
                title: '保管期限',
                valign: 'middle',
                align: 'center',
                visible: true,
                width: '5%'
            },
            {
                field: 'documentTime',
                title: '文件日期',
                align: 'center',
                valign: 'middle',
                visible: true,
                width: '5%'
            },
            {
                field: 'pageNumber',
                title: '页次',
                align: 'center',
                valign: 'middle',
                visible: true,
                width: '5%'
            },
            {
                field: 'createUserName',
                title: '录入员',
                valign: 'middle',
                align: 'center',
                visible: true,
                width: '5%'
            },
            {
                title: '操作',
                align: 'center',
                valign: 'middle',
                visible: true,
                events: operateEvents,
                width: '10%',
                formatter: operateFormatter2
            }
        ]
    });


    $('#docSubmit').click(function () {
        var fileNum = $('#fileNum').val();
        var responsiblePerson = $('#responsiblePerson').val();
        var title = $('#title').val();
        var keepTime = $('#keepTime option:selected').text();
        var pageNumber = $('#pageNumber').val();
        var createUserName = $('#createUserName').val();
        var documentTime = $('#documentTime').val();
        if (documentTime !== "" && responsiblePerson !== "" && title !== "" && keepTime !== "" && createUserName !== "" ) {
            $.ajax({
                type: 'post',
                url: "../user/projectDocument/add",
                data: {
                    "projectBoxId": boxId,
                    "boxNumber": boxNum,
                    "number": fileNum,
                    "responsiblePerson": responsiblePerson,
                    "documentTime": documentTime,
                    "title": title,
                    "keepTime": keepTime,
                    "pageNumber": pageNumber,
                    "createUserName": createUserName
                },
                success: function (data) {
                    if (data.code == 0) {
                        $('#add').modal('hide');
                        $('#fileNum').val("");
                        $('#responsiblePerson').val("");
                        $('#title').val("");
                        $('#pageNumber').val("");
                        $('#createUserName').val("");
                        $('#documentTime').val("");
                        $('#table').bootstrapTable('refresh')
                    }
                    else alert(data.message);
                }
            });
        }
        else {
            alert("请补全信息！");
        }

    })


    $('#docSubmit-change').click(function () {
        var fileNum = $('#fileNum-change').val();
        var responsiblePerson = $('#responsiblePerson-change').val();
        var title = $('#title-change').val();
        var keepTime = $('#keepTime-change option:selected').text();
        var pageNumber = $('#pageNumber-change').val();
        var createUserName = $('#createUserName-change').val();
        var documentTime = $('#documentTime-change').val();
        if (documentTime !== "" && responsiblePerson !== "" && title !== "" && keepTime !== "" && createUserName !== "" ) {
            $.ajax({
                type: 'post',
                url: "../user/projectDocument/update",
                data: {
                    "boxNumber": boxNum,
                    "id": $('#docId1').val(),
                    "number": fileNum,
                    "responsiblePerson": responsiblePerson,
                    "documentTime": documentTime,
                    "title": title,
                    "keepTime": keepTime,
                    "pageNumber": pageNumber,
                    "createUserName": createUserName
                },
                success: function (data) {
                    if (data.code == 0) {
                        $('#change').modal('hide');
                        $('#table').bootstrapTable('refresh')
                    }
                    else alert(data.message);
                }
            });

        }
        else {
            alert("请补全信息！");
        }


    })

    $('#search').click(function () {
        var con = $("#query").val();
        var str = "";
        if (con == "2") {
            str += "responsiblePerson="
        }
        else if (con == "3") {
            str += "title="
        }
        else if (con == "4") {
            str += "keepTime="
        }
        else if (con == "5") {
            str += "createUserName="
        }
        else if (con == "1") {
            str += "number="
        }
        str += encodeURIComponent($('#con').val())
        $('#table').bootstrapTable('destroy')
        $('#table').bootstrapTable({
            url: "../user/projectDocument/select?" + str + "&projectBoxId=" + boxId,
            pagination: true,
            pageNumber: 1, //初始化加载第一页，默认第一页
            pageSize: 5, //每页的记录行数（*）
            pageList: [5, 10, 15],//可供选择的每页行数
            paginationLoop: true,
            sortOrder: "asc",
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
            queryParams: function queryParams(params) {   //设置查询参数
                return  param = {
                    sortName : "id",
                    pageSize: params.limit,   //页面大小
                    pageNumber: (params.offset / params.limit) + 1,  //页码
                };
            },
            columns: [
                {
                    field: 'boxNumber',//域值
                    title: '档号',//标题
                    align: 'center',
                    valign: 'middle',
                    visible: true,
                    width: '5%'
                },
                {
                    field: 'number',//域值
                    title: '文件编号',//标题
                    align: 'center',
                    valign: 'middle',
                    visible: true,
                    width: '5%'
                },
                {
                    field: 'responsiblePerson',
                    title: '责任人',
                    align: 'center',
                    valign: 'middle',
                    visible: true,
                    width: '5%'
                },
                {
                    field: 'title',
                    title: '文件题名',
                    align: 'center',
                    valign: 'middle',
                    visible: true,
                    width: '5%'
                },
                {
                    field: 'keepTime',
                    title: '保管期限',
                    valign: 'middle',
                    align: 'center',
                    visible: true,
                    width: '5%'
                },
                {
                    field: 'documentTime',
                    title: '文件日期',
                    align: 'center',
                    valign: 'middle',
                    visible: true,
                    width: '5%'
                },
                {
                    field: 'pageNumber',
                    title: '页次',
                    align: 'center',
                    valign: 'middle',
                    visible: true,
                    width: '5%'
                },
                {
                    field: 'createUserName',
                    title: '录入员',
                    valign: 'middle',
                    align: 'center',
                    visible: true,
                    width: '5%'
                },
                {
                    title: '操作',
                    align: 'center',
                    valign: 'middle',
                    visible: true,
                    events: operateEvents,
                    width: '10%',
                    formatter: operateFormatter2
                }
            ]
        })
    })

    $('#search-whole').click(function () {

        var con = $("#query").val();
        var str = "";
        if (con == "2") {
            str += "responsiblePerson="
        }
        else if (con == "3") {
            str += "title="
        }
        else if (con == "4") {
            str += "keepTime="
        }
        else if (con == "5") {
            str += "createUserName="
        }
        else if (con == "1") {
            str += "number="
        }
        str += encodeURIComponent($('#con').val())
        $('#table').bootstrapTable('destroy')
        $('#table').bootstrapTable({
            url: "../user/projectDocument/selectProjectData?" + str ,
            pagination: true,
            pageNumber: 1, //初始化加载第一页，默认第一页
            pageSize: 5, //每页的记录行数（*）
            pageList: [5, 10, 15],//可供选择的每页行数
            paginationLoop: true,
            sortOrder: "asc",
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
            queryParams: function queryParams(params) {   //设置查询参数
                return  param = {
                    sortName : "id",
                    projectName : projectName,
                    pageSize: params.limit,   //页面大小
                    pageNumber: (params.offset / params.limit) + 1,  //页码
                };
            },
            columns: [
                {
                    field: 'boxNumber',//域值
                    title: '档号',//标题
                    align: 'center',
                    valign: 'middle',
                    visible: true,
                    width: '5%'
                },
                {
                    field: 'number',//域值
                    title: '文件编号',//标题
                    align: 'center',
                    valign: 'middle',
                    visible: true,
                    width: '5%'
                },
                {
                    field: 'responsiblePerson',
                    title: '责任人',
                    align: 'center',
                    valign: 'middle',
                    visible: true,
                    width: '5%'
                },
                {
                    field: 'title',
                    title: '文件题名',
                    align: 'center',
                    valign: 'middle',
                    visible: true,
                    width: '5%'
                },
                {
                    field: 'keepTime',
                    title: '保管期限',
                    valign: 'middle',
                    align: 'center',
                    visible: true,
                    width: '5%'
                },
                {
                    field: 'documentTime',
                    title: '文件日期',
                    align: 'center',
                    valign: 'middle',
                    visible: true,
                    width: '5%'
                },
                {
                    field: 'pageNumber',
                    title: '页次',
                    align: 'center',
                    valign: 'middle',
                    visible: true,
                    width: '5%'
                },
                {
                    field: 'createUserName',
                    title: '录入员',
                    valign: 'middle',
                    align: 'center',
                    visible: true,
                    width: '5%'
                },
                {
                    title: '操作',
                    align: 'center',
                    valign: 'middle',
                    visible: true,
                    events: operateEvents,
                    width: '10%',
                    formatter: operateFormatter2
                }
            ]
        })
    })

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

});

/*function operateFormatter1(value, row, index) {
    var currentDate = new Date(row.documentTime);
    return GMTToStr(currentDate)
}*/
function operateFormatter1(value, row, index){
    return value.toString().substring(40);
}


function operateFormatter2(value, row, index) {
    return [
        '<a class="change"  href="javascript:void(0)" title="Change">',
        '修改',
        '</a>|',
        '<a class="remove" href="javascript:void(0);"  title="Remove">',
        '删除',
        '</a><br>',
        '<a class="addPic" href="javascript:void(0)" title="Check">',
        '添加图片',
        '</a><br>',
        '<a class="selectImg" href="javascript:void(0)" title="select">',
        '图片目录',
        '</a>'
    ].join('');
}


function operateFormatter3(value, row, index) {
    return '<img style="max-height:200px;max-width:200px" src="../user/projectContent/getImage/' + row.id + '"/>'
}

function operateFormatter4(value, row, index) {
    return "<a class='removeImg' href='javascript:void(0)'style='color: red'>删除</a>"
}

/*function GMTToStr(time) {
    let date = new Date(time)
    let Str = date.getFullYear() + '-' +
        ((date.getMonth() + 1) < 10 ? ("0" + (date.getMonth() + 1)) : (date.getMonth() + 1)) + '-' +
        (date.getDate() < 10 ? ("0" + date.getDate()) : (date.getDate())) + ' ' +
        (date.getHours() < 10 ? ("0" + date.getHours()) : (date.getHours())) + ':' +
        (date.getMinutes() < 10 ? ("0" + date.getMinutes()) : (date.getMinutes())) + ':' +
        (date.getSeconds() < 10 ? ("0" + date.getSeconds()) : (date.getSeconds()))
    return Str
}*/

window.operateEvents = {
    'click .selectImg': function (e, value, row, index) {
        $('#img').modal();
        $('#table2').bootstrapTable('destroy');
        $('#table2').bootstrapTable({
            url: "../user/projectContent/select?projectDocumentId=" + row.id,
            pagination: false,
            pageNumber: 1, //初始化加载第一页，默认第一页
            pageSize: 5, //每页的记录行数（*）
            pageList: [5, 10, 15],//可供选择的每页行数
            paginationLoop: true,
            sidePagination: 'server',//采用服务器端分页
            queryParamType: "",
            //设置每行样式
            /*rowStyle:function rowStyle(row, index) {
                return {
                    css: {"font-size": "15px",
                        "color":"#959595"
                    }
                };
            },*/
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
                    field: 'contentAddress',//域值
                    title: '图片名称',//标题
                    align: 'center',
                    valign: 'middle',
                    visible: true,
                    width: '5%',
                    formatter:operateFormatter1
                },
                {
                    field: 'contentAddress',
                    title: '图片',
                    align: 'center',
                    valign: 'middle',
                    visible: true,
                    width: '5%',
                    formatter: operateFormatter3
                },
                {
                    title: '操作',
                    align: 'center',
                    valign: 'middle',
                    visible: true,
                    events: operateEvents1,
                    width: '5%',
                    formatter: operateFormatter4
                }
            ]
        })
    },
    'click .change': function (e, value, row, index) {  //点修改之后弹出修改模态框，并往模态框中传值
        $("#change").modal();
        $("#fileNum-change").val(row.number);
        $("#responsiblePerson-change").val(row.responsiblePerson);
        $("#title-change").val(row.title);
        $("#pageNumber-change").val(row.pageNumber);
        $("#createUserName-change").val(row.createUserName);
        $("#documentTime-change").val(row.documentTime);
        $("#keepTime-change option[text=" + row.keepTime + "]").attr("selected", true);
        $("#docId1").val(row.id);
    },
    'click .remove': function (e, value, row, index) {

        if (confirm("您确定要删除此档案吗？\n该档案下的所有图片将被删除！")) {
            $.ajax({
                type: "DELETE",
                url: "../user/projectDocument/delete/" + row.id,
                success: function (data) {
                    $("#table").bootstrapTable("refresh");
                }
            });
        }
    },
    'click .addPic': function (e, value, row, index) {
        $("#uploadPic").modal();
        $("#docId2").val(row.id);
    }

}

window.operateEvents1 = {
    'click .removeImg': function (e, value, row, index) {
        if (confirm("您确定要删除这张图片吗？")) {
            $.ajax({
                type: "DELETE",
                url: "../user/projectContent/delete/" + row.id,
                success: function (data) {
                    $("#table2").bootstrapTable("refresh");
                }
            });
        }
    }
}


//利用html5 FormData() API,创建一个接收文件的对象，因为可以多次拖拽，这里采用单例模式创建对象Dragfiles
var Dragfiles = new FormData();

//添加拖拽事件
var dz = document.getElementById('content');
dz.ondragover = function (ev) {
    //阻止浏览器默认打开文件的操作
    ev.preventDefault();
    //拖入文件后边框颜色变红
    this.style.borderColor = 'red';
}

dz.ondragleave = function () {
    //恢复边框颜色
    this.style.borderColor = 'gray';
}
dz.ondrop = function (ev) {
    //恢复边框颜色
    this.style.borderColor = 'gray';
    //阻止浏览器默认打开文件的操作
    ev.preventDefault();
    var files = ev.dataTransfer.files;
    var len = files.length,
        i = 0, j = 0;
    var frag = document.createDocumentFragment(); //为了减少js修改dom树的频度，先创建一个fragment，然后在fragment里操作
    var tr, time, size;
    Dragfiles.forEach(function () {
        j++;

    })
    if (j = 0) {
        Dragfiles = new FormData();
    }
    while (i < len) {
        tr = document.createElement('tr');
        //获取文件大小
        size = Math.round(files[i].size * 100 / 1024) / 100 + 'KB';
        //获取格式化的修改时间
        time = files[i].lastModifiedDate.toLocaleDateString() + ' ' + files[i].lastModifiedDate.toTimeString().split(' ')[0];
        tr.innerHTML = '<td><a target="_blank" href="' + window.URL.createObjectURL(files[i]) + '">' + files[i].name + '</td><td>' + time + '</td><td>' + size + '</td><td>删除</td>';

        frag.appendChild(tr);
        //添加文件到newForm
        Dragfiles.append(files[i].name, files[i]);
        //console.log(it.next());
        i++;
    }
    this.childNodes[1].childNodes[1].appendChild(frag);
    //为什么是‘1'？文档里几乎每一样东西都是一个节点，甚至连空格和换行符都会被解释成节点。而且都包含在childNodes属性所返回的数组中.不同于jade模板
}

function blink() {
    document.getElementById('content').style.borderColor = 'gray';
}


// 用事件委托的方法为‘删除'添加点击事件，使用jquery中的on方法
$(".tbody").on('click', 'tr td:last-child', function () {
    //删除拖拽框已有的文件
    var key = $(this).prev().prev().prev().text();

    Dragfiles.delete(key);
    $(this).parent().remove();
});

//清空所有内容
function clearAll() {
    if (document.getElementsByTagName('tbody')[4].hasChildNodes() == false) {
        document.getElementById('content').style.borderColor = 'red';
        setTimeout(blink, 300);
        return false;
    }
    Dragfiles = new FormData();
    $('.tbody').empty();

}

//ajax上传文件
function upload() {
    if (document.getElementsByTagName('tbody')[4].hasChildNodes() == false) {
        document.getElementById('content').style.borderColor = 'red';
        setTimeout(blink, 200);
        return false;
    }
    Dragfiles.forEach(function (value, key) {  //循环上传
        var form = new FormData();
        form.append("id", $('#docId2').val());
        form.append("image", value)
        $.ajax({
            url: '../user/projectContent/uploadImage',
            type: 'POST',
            data: form,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            error: function (returndata) {
                alert('上传失败!') //可以替换为自己的方法
                return;
            }
        });
    })
    alert('上传成功!') //可以替换为自己的方法
    clearAll();
    $('#uploadPic').modal('hide')
}
