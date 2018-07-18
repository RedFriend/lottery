<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>结果打印页面</title>

    <link rel="stylesheet" type="text/css"
          href="${springMacroRequestContext.contextPath}/bootstrap-3.3.7-dist/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${springMacroRequestContext.contextPath}/css/jqgrid/ui.jqgrid.css">
    <link rel="stylesheet" type="text/css"
          href="${springMacroRequestContext.contextPath}/css/jqgrid/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" type="text/css"
          href="${springMacroRequestContext.contextPath}/css/jqgrid/ui.jqgrid-bootstrap-ui.css">
    <!-- MetisMenu CSS -->
    <link href="${springMacroRequestContext.contextPath}/css/metisMenu.min.css">
    <!-- DataTables CSS -->
    <link href="${springMacroRequestContext.contextPath}/css/dataTables.bootstrap.css">

    <script type="text/javascript"
            src="${springMacroRequestContext.contextPath}/js/jqgrid/jquery-1.11.1.min.js"></script>
    <script type="text/javascript"
            src="${springMacroRequestContext.contextPath}/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
    <script type="text/javascript"
            src="${springMacroRequestContext.contextPath}/js/jqgrid/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="${springMacroRequestContext.contextPath}/js/jqgrid/jquery.jqGrid.js"></script>
    <style>

    </style>
    <script type="text/javascript">
        function fetchDataByDept() {
            var deptName = $("#condition").val();
            debugger
            $.ajax({
                url: "${springMacroRequestContext.contextPath}/lottery/pickPrint?deptName=" + deptName,
                async: false,
                success: function (data) {
                    $("#infoContent").html($(data).find("#infoContent").html());
                }
            });
        }
    </script>
</head>
<body>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header" style="text-align: center">号码信息</h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <span style="width: 200px;height:30px;font-size: 16px">按部门过滤数据：</span>
                    <select id="condition" style="width: 200px;height:30px" onchange="fetchDataByDept()">
                        <option value="ALL" selected>全部</option>
                         <#if selectMap??>
                             <#list selectMap?keys as key>
　　
                                 <option value="${key}">${selectMap[key]}</option>
                             </#list>
                         </#if>
                    </select>
                    <span style="padding-left: 20px;cursor: pointer" onclick="window.print(); ">打印</span>
                    <span style="padding-left: 20px;cursor: pointer" onclick="location='${springMacroRequestContext.contextPath}/lottery/setting/downLeftNumberExcel' ">导出剩余号码</span>
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover"
                           id="dataTables-example">
                        <thead>
                        <tr>
                            <th>部门</th>
                            <th>人员</th>
                            <th>初始号码</th>
                            <th>选定号码</th>
                            <th>最后随机号码</th>
                        </tr>
                        </thead>
                        <tbody id="infoContent">
                         <#if list??>                                          <#list list as user>
                             <#if user_index%2==1>
                             <tr class="odd gradeX">
                             <#else>
                            <tr>
                             </#if>
                                 <td class="center">${user.deptName}</td>
                                 <td class="center">${user.userName}</td>
                                 <td class="center">${user.phoneNumber!""}</td>
                                 <td class="center">${user.pickNumber!""}</td>
                                 <td class="center">${user.lastRandomNumber!""}</td>
                            </tr>
                         </#list>

                         </#if>

                        </tbody>
                    </table>
                </div>
                <!-- /.panel-body -->
            </div>
            <!-- /.panel -->
        </div>
        <!-- /.col-lg-12 -->
    </div>
</div>


</body>
</html>