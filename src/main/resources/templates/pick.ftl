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
    <style>
        @charset "utf-8";
        /* CSS Document */
        html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p, dd, dl, dt, li, ol, ul, input, select, button, textarea, tr, td {
            padding: 0;
            margin: 0;
            border: none;
        }

        input, button, select, textarea, a, img {
            outline: none;
        }

        /*去掉超链接或按钮点击时出现的虚线框黄色边框*/
        ::-moz-focus-inner {
            border: 0px;
        }

        /*火狐的私有属性去掉点击时边框*/
        body, html {
            width: 100%;
            font-family: "Microsoft YaHei", "Arial", "SimSun";
        }

        ul, ul li, ol li, li {
            list-style: none;
        }

        a, img, input, textarea {
            border: none;
        }

        a {
            text-decoration: none;
        }

        table {
            border-collapse: collapse;
            border-spacing: 0;
        }

        .clearfix:after {
            visibility: hidden;
            display: block;
            font-size: 0;
            content: ".";
            clear: both;
            height: 0;
        }

        * html .clearfix {
            zoom: 1;
        }

        *:first-child + html .clearfix {
            zoom: 1;
        }

        .fl {
            float: left;
        }

        .fr {
            float: right;
        }

        .none {
            display: none;
        }

        .inrow {
            font-size: 0;
        [;
            font-size: 12px;
        ];
            *font-size: 0;
            font-family: arial;
        [;
            letter-spacing: -3px;
        ];
            *letter-spacing: normal;
            *word-spacing: -1px;
        }

        .inrow > li, .inrow span {
            display: inline-block;
            *display: inline;
            *zoom: 1;
            font-size: 14px;
            letter-spacing: normal;
            word-spacing: normal;
        }

        .dataNums {
            position: absolute;
            top: 50%;
            display: block;
            width: 100%;
            height: 75px;
            margin-top: -37px;
            text-align: center;
        }

        .dataNums .dataOne {
            width: 61px;
            height: 75px;
            margin: 0px 3px;
            text-align: center;
            background: url(../img/num-bg.png) no-repeat;
        }

        .dataNums .dataBoc {
            position: relative;
            width: 100%;
            height: 100%;
            overflow: hidden;
        }

        .dataNums .dataBoc .tt {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
        }

        .dataNums .tt span {
            width: 100%;
            height: 100%;
            font: bold 54px/75px "Arial";
            color: #ddf0ff;
        }
    </style>
</head>
<body>

<div class="row" style="margin:unset;padding-top: 30px">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <!-- /.panel-heading -->
            <div class="panel-body">
                <!-- Tab panes -->
                <div class="tab-content" style="width:auto;min-height: 500px;text-align: center">
                    <div class="tab-pane fade in active">
                        <br style="padding-top: 20px">
                        <div class="col-lg-6">
                            <div class="panel panel-green" id="currentInfo2">
                                <div class="panel-heading">
                                    基本信息
                                </div>
                                <div class="panel-body">
                                    <h4>姓名：${user.userName!""}</h4>
                                    <br>
                                    <h4>部门：${user.deptName!""}</h4>
                                    <br>
                                            <#if !user.pickNumber??>
                                                <h4>原号码：${user.phoneNumber!""}</h4>
                                    <br>
                                                <h4 id="pickNumberDisplay" style="display:none;">选定号码：${user.pickNumber!""}</h4>
                                            <#else>
                                                <h4>原号码：${user.phoneNumber!""}</h4>

                                    <br>
                                                <h4>选定号码：${user.pickNumber!""}</h4>
                                            </#if>
                                </div>
                                <div class="panel-footer">
                                    更新时间：${.now}
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-6 noprint" id="currentInfo">
                            <div class="panel panel-red">
                                <div class="panel-heading">
                                    注意事项
                                </div>
                                <div class="panel-body" style="text-align:left;padding-left: 5%;">
                                    <h4>1.您有三次机会，每次有三个号码提供选择。</h4>
                                    <br>
                                    <h4>2.一旦选定一个号码将不可更改。</h4>
                                    <br>
                                    <h4>3.当三次机会仍然未选定号码时，系统将选定最后一个号码。</h4>
                                    <br>
                                </div>
                                <div class="panel-footer">
                                    更新时间：${.now}
                                </div>
                            </div>
                            <!-- /.col-lg-4 -->
                        </div>
                        <div class="col-lg-12 noprint" id="currentInfo">
                            <div class="panel panel-primary" id="pickNumberPannel">
                                <div class="panel-heading">
                                    选择号码
                                </div>
                                <div class="panel-body" style="text-align:left;padding-left: 5%;">

                                </div>
                                        <#if !user.pickNumber??>
                                            <#if user.pickTimes==0>
                                        <button id="start_btn" style="margin-bottom: 10px; type=" button
                                                " onclick="startRandom()" class="btn btn-danger btn-lg">
                                            开始
                                        </button>
                                            </#if>
                                            <button id="timeCount"
                                                    style="border: none;font-size:30px;font-weight: 900;margin-bottom: 10px; type="
                                                    button
                                                " class="btn btn-outline btn-danger">
                                        </button>
                                        <p style="padding: 20px;font-size:30px;font-weight: 900;color: #7caeda">双击选定</p>
                                        <p>
                                            <#if user.pickList??>
                                                <#list  user.pickList as u>
                                            <button id="b_${u.name}" ondblclick="certainPick(this)"
                                                    style="font-size: 50px;font-weight: 900;" type="button"
                                                    class="btn btn-outline btn-primary btn-lg">${u.name}</button>
                                                </#list>
                                            </#if>
                                        </p>
                                        <br>
                                        <#--<button  style="margin-bottom: 10px; type="button" onclick="pickRandom()" class="btn btn-danger btn-lg">-->
                                        <#--不喜欢-->
                                        <#--</button>-->
                                        <#else>
                                        <p style="padding: 20px;font-size:30px;font-weight: 900;color: #7caeda" onclick="location='${springMacroRequestContext.contextPath}/login'">
                                            已经选定号码了,点击退出</p>
                                        </#if>
                                <div class="panel-body">
                                    <!-- Button trigger modal -->
                                    <button id="dialogTip" class="btn btn-primary btn-lg" data-toggle="modal"
                                            data-target="#myModal" style="display: none">
                                    </button>
                                    <!-- Modal -->
                                    <div class="modal fade" id="myModal" tabindex="-1" role="dialog"
                                         aria-labelledby="myModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal"
                                                           onclick="location.reload();" aria-hidden="true">&times;
                                                    </button>
                                                    <h4 class="modal-title" id="myModalLabel">选定成功</h4>
                                                </div>
                                                <div id="myNumberPrintContent" class="modal-body">
                                                    <p>请您拍下本次选定的号码，以防遗忘。</p>
                                                    <p id="myNumber"
                                                       style="color:7caeda;font-size: 50px;font-weight: 900 "></p>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-default"
                                                            onclick="location='${springMacroRequestContext.contextPath}/login'" data-dismiss="modal">退出
                                                    </button>
                                                    <button type="button" class="btn btn-primary"
                                                            onclick="printPick();">打印
                                                    </button>
                                                </div>
                                            </div>
                                            <!-- /.modal-content -->
                                        </div>
                                        <!-- /.modal-dialog -->
                                    </div>
                                    <!-- /.modal -->
                                </div>
                                <div class="panel-footer">
                                    更新时间：${.now}
                                </div>
                            </div>
                            <!-- /.col-lg-4 -->
                        </div>
                    </div>
                </div>
                <!-- /.panel-body -->
            </div>
            <!-- /.panel -->
        </div>
    </div>
</div>
<!-- /#wrapper -->
<!-- jQuery -->
<script src="${springMacroRequestContext.contextPath}/sb/vendor/jquery/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="${springMacroRequestContext.contextPath}/sb/vendor/bootstrap/js/bootstrap.min.js"></script>
<!-- Metis Menu Plugin JavaScript -->
<script src="${springMacroRequestContext.contextPath}/sb/vendor/metisMenu/metisMenu.min.js"></script>

<!-- Custom Theme JavaScript -->
<script src="${springMacroRequestContext.contextPath}/sb/dist/js/sb-admin-2.js"></script>
<script src="${springMacroRequestContext.contextPath}/js/num.js"></script>
<script>
    function certainPick(obj) {
        clearInterval(timeInt);
        clearTimeout(time1);
        clearTimeout(time2);
        clearTimeout(time3);
        var pickNumber = $(obj).html();
        $.ajax({
            url: "${springMacroRequestContext.contextPath}/lottery/certainPick",
            type: "post",
            data: {
                userName: '${user.userName}',
                deptName: '${user.deptName}',
                pickNumber: pickNumber

            },
            success: function (data) {
                if (data.flg == 'Y') {
                    debugger
                    $('#myNumber').html(data.user.pickNumber);
                    $('#pickNumberDisplay').show().html('选定号码：'+data.user.pickNumber);
                    $('#dialogTip').click();
                    clearTimeout(time1);
                    clearTimeout(time2);
                    clearTimeout(time3);
                } else {
                    alert("操作失败！")
                }
            }
        });

    }

    var time1, time2, time3, timeInt;
    var i = 10;
    var j = 0;

    function setTimeCount() {
        --i;
        $('#timeCount').html("第"+(j+1)+"次选号倒计时"+i);
        if (i == 0) {
            i = 10;
            j++;
        }
        if (j == 3) {
            clearInterval(timeInt);
        }
    }

    function startRandom() {
        $('#start_btn').hide();
        pickRandom();
        timeInt = setInterval(setTimeCount, 1000);
        time1 = setTimeout(pickRandom1, 10000);
    }

    function pickRandom1() {
        pickRandom();
        time2 = setTimeout("pickRandom2()", 10000);

    }

    function pickRandom2() {
        pickRandom();
        time3 = setTimeout("certainLastNumber()", 10000);

    }

    function certainLastNumber() {
        $('#pickNumberPannel p button:last').dblclick();
    }

    function pickRandom() {
        $.ajax({
            url: "${springMacroRequestContext.contextPath}/lottery/pickRandom",
            type: "post",
            async: false,
            data: {
                userName: '${user.userName}',
                deptName: '${user.deptName}'

            },
            success: function (data) {
                $('#pickNumberPannel').html($(data).find('#pickNumberPannel').html());

                $('#currentInfo2').html($(data).find('#currentInfo2').html());
            }
        });
    }
    function printPick()
    {
        $('.noprint').hide();
        window.print();
        $('.noprint').show();
    }
</script>

</body>
</html>