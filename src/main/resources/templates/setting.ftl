<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>配置页面</title>

    <link rel="stylesheet" type="text/css"
          href="${springMacroRequestContext.contextPath}/bootstrap-3.3.7-dist/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${springMacroRequestContext.contextPath}/css/jqgrid/ui.jqgrid.css">
    <link rel="stylesheet" type="text/css"
          href="${springMacroRequestContext.contextPath}/css/jqgrid/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" type="text/css"
          href="${springMacroRequestContext.contextPath}/css/jqgrid/ui.jqgrid-bootstrap-ui.css">
    <script type="text/javascript"
            src="${springMacroRequestContext.contextPath}/js/jqgrid/jquery-1.11.1.min.js"></script>
    <script type="text/javascript"
            src="${springMacroRequestContext.contextPath}/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
    <script type="text/javascript"
            src="${springMacroRequestContext.contextPath}/js/jqgrid/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="${springMacroRequestContext.contextPath}/js/jqgrid/jquery.jqGrid.js"></script>
    <style>
        .parent {
            width: 100%;
            height: 100%;
            /* 以下属性垂直居中 */
            position: absolute;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
            margin: auto;
            background: url('${springMacroRequestContext.contextPath}/img/luckbg.png') no-repeat;
            background-size: 100%;
        }
    </style>
    <script type="text/javascript">
        // $(function () {
        //     $.jgrid.defaults.styleUI = "Bootstrap";
        //     $("#list").jqGrid({
        //         width: 800,
        //         height: 300,
        //         url: 'setting/users',//请求数据的地址
        //         datatype: "json",
        //         colNames: ['Id', '姓名', '部门'],
        //         //jqgrid主要通过下面的索引信息与后台传过来的值对应
        //         colModel: [
        //             {name: 'userId', index: 'userId', width: 55},
        //             {name: 'userName', index: 'userName', width: 90},
        //             {name: 'deptName', index: 'deptName', width: 100}
        //         ],
        //         rowNum: 10,
        //         rowList: [10, 20, 30],
        //         pager: '#pager',
        //         sortname: 'id',
        //         recordpos: 'right',
        //         viewrecords: true
        //     });
        //     jQuery("#list").jqGrid('navGrid', '#pager', {edit: false, add: false, del: false});
        // });
    </script>
</head>
<body>
<br>
<div>
    <div class="form-group col-md-12 has-feedback" id="file">
        <form id="importUserFrom" action="${springMacroRequestContext.contextPath}/lottery/setting/importUsers"
              enctype="multipart/form-data" method="post">
            <div class="col-md-4 input-group">
                <input id="lefile1" type="file" style="display:none" name="userFile"
                       onchange="$('#photoCover').val($('#lefile1').val().substring($('#lefile1').val().lastIndexOf('\\')+1,$('#lefile1').val().length))">
                <span class="input-group-addon" onclick="location='${springMacroRequestContext.contextPath}/lottery/setting/downloadTemplate?templateType=user'"
                      style="cursor: pointer; background-color: #e7e7e7"><i class="glyphicon glyphicon-paste"></i>&nbsp;下载用户模板</span>
                <span class="input-group-addon" onclick="$('#lefile1').click();" title="请选择按格式录入的用户模板文件"
                      style="cursor: pointer; background-color: #e7e7e7"><i class="glyphicon glyphicon-folder-open"></i>&nbsp;浏览</span>
                <input id="photoCover" class="form-control" type="text" readonly="true">
                <span class="glyphicon glyphicon-save form-control-feedback" title="执行导入"
                      style="cursor: pointer;pointer-events: auto;" onclick="$('#importUserFrom').submit()"></span>
            </div>
        </form>
    </div>
    <div class="form-group col-md-12 has-feedback" id="file">
        <form id="importNumberFrom" action="${springMacroRequestContext.contextPath}/lottery/setting/importNumbers"
              enctype="multipart/form-data" method="post">
            <div class="col-md-4 input-group">
                <input id="lefile2" type="file" style="display:none" name="numberFile"
                       onchange="$('#photoCover2').val($('#lefile2').val().substring($('#lefile2').val().lastIndexOf('\\')+1,$('#lefile2').val().length))">
                <span class="input-group-addon" onclick="location='${springMacroRequestContext.contextPath}/lottery/setting/downloadTemplate?templateType=number'"
                      style="cursor: pointer; background-color: #e7e7e7"><i class="glyphicon glyphicon-paste"></i>&nbsp;下载号码模板</span>
                <span class="input-group-addon" onclick="$('#lefile2').click();" title="请选择按格式录入的号码模板文件"
                      style="cursor: pointer; background-color: #e7e7e7"><i class="glyphicon glyphicon-folder-open"></i>&nbsp;浏览</span>
                <input id="photoCover2" class="form-control" type="text" readonly="true">
                <span class="glyphicon glyphicon-save form-control-feedback" title="执行导入"
                      style="cursor: pointer;pointer-events: auto;" onclick="$('#importNumberFrom').submit()"></span>
            </div>
        </form>
    </div>
</div>
<div style="padding: 20px">
<a href="${springMacroRequestContext.contextPath}/lottery/setting/reset">重置</a>
</div>

<table id="list"></table>
<div id="pager"></div>
</body>
</html>