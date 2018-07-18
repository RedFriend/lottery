<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录页面</title>
    <!-- Bootstrap Core CSS -->
    <link href="${springMacroRequestContext.contextPath}/sb/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- MetisMenu CSS -->
    <link href="${springMacroRequestContext.contextPath}/sb/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="${springMacroRequestContext.contextPath}/sb/dist/css/sb-admin-2.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="${springMacroRequestContext.contextPath}/sb/vendor/font-awesome/css/font-awesome.min.css"
          rel="stylesheet" type="text/css">
    <!-- Morris Charts CSS -->
    <link href="${springMacroRequestContext.contextPath}/sb/vendor/morrisjs/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="${springMacroRequestContext.contextPath}/sb/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
</head>
<body>

<div class="container">
    <div class="row"><h1 style="text-align: center;padding-top: 10%">重新分发号码</h1></div>
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">请输入用户信息</h3>
                </div>
                <div class="panel-body">
                    <form role="form" method="post" action="${springMacroRequestContext.contextPath}/goLogin">
                        <fieldset>
                            <div class="form-group">
                                <select class="form-control" placeholder="部门" name="deptName" type="email" onchange="getDeptPerson(this)">
                                    <option value="">--请选择--</option>
                                 <#if deptMap??>
                                    <#list deptMap?keys as key>
                                         <option value="${key}">${deptMap[key]}</option>
                                    </#list>
                                </#if>
                                </select>
                            </div>
                            <div class="form-group">
                                <select id="userNameSelect" class="form-control" placeholder="用户名" name="userName" type="email">
                                </select>
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="密码" name="password" type="password">
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input name="remember" type="checkbox" value="Remember Me">自动登录
                                </label>
                            </div>
                            <#if true||doneStatus=="Y">
                                <input type="submit" value="登录" class="btn btn-lg btn-success btn-block">
                            <#else>
                             <input title="暂停使用，请先开始分配号码完后操作！" type="submit" value="登录" class="btn btn-lg btn-success btn-block" disabled>
                            </#if>


                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- jQuery -->
<script src="${springMacroRequestContext.contextPath}/js/jquery-1.9.1.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="${springMacroRequestContext.contextPath}/sb/vendor/bootstrap/js/bootstrap.min.js"></script>
<!-- Metis Menu Plugin JavaScript -->
<script src="${springMacroRequestContext.contextPath}/sb/vendor/metisMenu/metisMenu.min.js"></script>
<!-- Morris Charts JavaScript -->
<script src="${springMacroRequestContext.contextPath}/sb/vendor/raphael/raphael.min.js"></script>
<!-- Custom Theme JavaScript -->
<script src="${springMacroRequestContext.contextPath}/sb/dist/js/sb-admin-2.js"></script>
<script>
    function getDeptPerson(obj){
        debugger
        $.ajax({
            url:"${springMacroRequestContext.contextPath}/lottery/deptPerson",
            type:"post",
            data:{"deptName":obj.value},
            success:function(data){
                debugger
                var options="";
                for(var i=0;i<data.length;i++){
                    options+='<option value="'+data[i].userName+'">'+data[i].userName+'</option>';
                }
                $('#userNameSelect').html(options);
            }
        })
    }
</script>
</body>
</html>