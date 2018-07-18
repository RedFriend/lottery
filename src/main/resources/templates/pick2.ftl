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
                                    <div class="panel panel-primary" id="currentInfo2">
                                        <div class="panel-heading">
                                            基本信息
                                        </div>
                                        <div class="panel-body">
                                            <h4>姓名：${user.userName!""}</h4>
                                            <br>
                                            <h4>部门：${user.deptName!""}</h4>
                                            <br>
                                            <h4>初次号码：${user.phoneNumber!""}</h4>
                                            <br>
                                            <h4>可用换号次数：${3-user.pickTimes}</h4>
                                            <br>
                                            <#--<br>-->
                                            <#--<h4>可分配号码数：${usableTotal!"500"}</h4>-->
                                        </div>
                                        <div class="panel-footer">
                                            更新时间：${.now}
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-6" id="currentInfo">
                                    <div class="panel panel-green">
                                        <div class="panel-heading">
                                            注意事项
                                        </div>
                                        <div class="panel-body" style="text-align:left;padding-left: 5%;">
                                            <h4>1.点击换号将放弃原号。</h4>
                                            <br>
                                            <h4>2.每次有三个可选号码。</h4>
                                            <br>
                                            <h4>3.确认选定号码不可更改</h4>
                                            <br>
                                            <h4>4.不选定任何号码，系统将默认选定最后一个号码</h4>
                                            <br>
                                        </div>
                                        <div class="panel-footer">
                                            更新时间：${.now}
                                        </div>
                                    </div>
                                    <!-- /.col-lg-4 -->
                                </div>
                                <div class="col-lg-8" style="padding-top:100px">
                                    <div id="dataNums"></div>

                                </div>
                                <div class="col-lg-8" style="padding-top:30px" id="goPick">
                                    <p>
                                        <br>
                                        <#if user.pickTimes lt 3>
                                        <button type="button" onclick="pickRandom()" class="btn btn-danger btn-lg">
                                            开始随机选号
                                        </button>
                                        <#else>
                                        <button type="button" class="btn btn-danger btn-lg" disabled>
                                            开始随机选号
                                        </button>
                                        </#if>
                                    </p>
                                </div>
                                <div class="col-lg-4" style="margin-top: -90px">
                                    <div class="chat-panel panel panel-default">
                                        <div class="panel-heading">
                                            <i class="fa fa-comments fa-fw"></i> 随机结果
                                            <div class="panel-body">
                                                <ul id="pickResult" class="chat">
<#if user.pickList??>
    <#list user.pickList as pick>

                                                    <li class="left clearfix" style="cursor: pointer;"
                                                        onclick="$('#likeNumber').val($(this).find('strong').html())">
                                                        <span class="chat-img pull-left">
                                                            <img src="${springMacroRequestContext.contextPath}/img/pick_${pick_index+1}.png"
                                                                 alt="User Avatar"
                                                                 class="img-circle">
                                                        </span>
                                                        <span style="font-size: 20px;text-align: center"><strong
                                                                class="primary-font">${pick.name}</strong></span>
                                                    </li>

    </#list>

</#if>

                                                </ul>
                                            </div>
                                            <!-- /.panel-body -->
                                            <div class="panel-footer">
                                                <div class="input-group">
                                                    <input id="likeNumber" type="text" class="form-control input-sm"
                                                           placeholder="请选定一个号码" readonly>
                                                    <span class="input-group-btn">
                                    <button class="btn btn-warning btn-sm" id="btn-chat">
                                        选定
                                    </button>
                                </span>
                                                </div>
                                            </div>
                                            <!-- /.panel-footer -->
                                        </div>
                                    </div>
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
        $(function () {
            $("#dataNums").rollNumDaq({
                deVal: 8888
            });
        });
        var li;

        function pickRandom() {
            $.ajax({
                url: "${springMacroRequestContext.contextPath}/lottery/pickRandom",
                type: "post",
                data: {
                    userName: '${user.userName}',
                    deptName: '${user.deptName}'
                },
                success: function (data) {
                    $('#currentInfo').html($(data).find("#currentInfo").html());
                    $('#currentInfo2').html($(data).find("#currentInfo2").html());
                    $('#goPick').html($(data).find("#goPick").html());

                    li = $(data).find("#pickResult li").eq(-3);
                    var number = $(li).find("strong").html().substring(7, 11);
                    $("#dataNums").rollNumDaq({
                        deVal: number
                    });

                    $('#pickResult').append(li);

                    setTimeout(function () {
                        debugger
                        $('#pickResult').append(li);
                        li = $(data).find("#pickResult li").eq(-2);
                        var number = $(li).find("strong").html().substring(7, 1);
                        $("#dataNums").rollNumDaq({
                            deVal: number
                        })
                        ;
                        setTimeout(function () {
                            debugger
                            $('#pickResult').append(li);
                            li = $(data).find("#pickResult li").eq(-1);
                            var number = $(li).find("strong").html().substring(6, 11);
                            $("#dataNums").rollNumDaq({
                                deVal: number
                            });
                            setTimeout(function () {
                                $('#pickResult').append(li);
                            }, 2000);
                        }, 2000)
                    }, 2000);
                }
            });

        }
    </script>

</body>
</html>