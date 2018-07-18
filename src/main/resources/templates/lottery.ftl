<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>幸运摇号</title>
    <link rel="stylesheet" type="text/css"
          href="${springMacroRequestContext.contextPath}/bootstrap-3.3.7-dist/css/bootstrap.css">
    <style>
        body {
            position: relative;
            height: 1080px;
        }

        .parent {
            width: 100%;
            min-height: 1080px;
            height: 100%;
            /* 以下属性垂直居中 */
            position: absolute;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
            margin: auto;
            background: url('${springMacroRequestContext.contextPath}/img/luckbg.png') no-repeat center center;
            background-size: 100% 100%;
        }

        .odometer {
            font-size: 50px;
        }
    </style>

    <style>
        * {
            padding: 0;
            margin: 0;
        }

        html, body {
            font-family: "Microsoft YaHei", sans-serif;
            color: #333;
            font-size: 14px;
        }

        li {
            list-style: none;
        }

        a {
            text-decoration: none;
            color: #666;
        }

        /**************/
        .box {
            width: 800px;
            height: 800px;
            border: 1px solid #dedede;
            margin: 40px auto;
            background: #f9f9f9;
        }

        .all {
            width: 600px;
            height: auto;
            margin: 60px auto;
            overflow: hidden;
        }

        .top-img {
            width: 600px;
            height: 500px;
            font-size: 0;
            position: relative;
        }

        .activeimg {
            overflow: hidden;
            height: 500px;
            position: relative;
        }

        .top-img .activeimg img {
            width: 600px;
            height: 500px;
            background-repeat: no-repeat;
        }

        .top-img .left {
            width: 80px;
            height: 500px;
            position: absolute;
            left: 0;
            top: 0;
            text-align: center;
            line-height: 500px;
            cursor: pointer;
        }

        .top-img .right {
            width: 80px;
            height: 500px;
            position: absolute;
            right: 0px;
            top: 0;
            text-align: center;
            line-height: 500px;
            cursor: pointer;
        }

        .top-img .left:hover, .top-img .right:hover {

        }

        .bot-img {
            height: 90px;
            width: 600px;
            margin-top: 15px
        }

        .bot-img ul {
            width: 100%;
            height: 100px;
        }

        .bot-img ul li {
            width: 90px;
            margin-right: 10px;
            float: left;
            border: 1px solid #fff;
            cursor: pointer;
            font-size: 0;
        }

        .bot-img ul li.active {
            border: 1px solid #ff6600;
        }

        .bot-img ul li img {
            width: 100%;
        }

        .bot-img ul li:last-child {
            margin-right: 0;
        }

        .buttons {
            position: relative;
            color: rgba(255, 255, 255, 1);
            text-decoration: none;
            background-color: rgba(219, 87, 5, 1);
            font-family: 'Yanone Kaffeesatz';
            font-weight: 700;
            font-size: 25px;
            display: block;
            padding: 4px;
            -webkit-border-radius: 8px;
            -moz-border-radius: 8px;
            border-radius: 8px;
            -webkit-box-shadow: 0px 9px 0px rgba(219, 31, 5, 1), 0px 9px 25px rgba(0, 0, 0, .7);
            -moz-box-shadow: 0px 9px 0px rgba(219, 31, 5, 1), 0px 9px 25px rgba(0, 0, 0, .7);
            box-shadow: 0px 9px 0px rgba(219, 31, 5, 1), 0px 9px 25px rgba(0, 0, 0, .7);
            margin: 25px auto;
            width: 160px;
            text-align: center;

            -webkit-transition: all .1s ease;
            -moz-transition: all .1s ease;
            -ms-transition: all .1s ease;
            -o-transition: all .1s ease;
            transition: all .1s ease;
        }

        .buttonsPrint {
            position: relative;
            color: rgba(255, 255, 255, 1);
            text-decoration: none;
            background-color: rgba(219, 87, 5, 1);
            font-family: 'Yanone Kaffeesatz';
            font-weight: 700;
            font-size: 25px;
            display: block;
            padding: 4px;
            -webkit-border-radius: 8px;
            -moz-border-radius: 8px;
            border-radius: 8px;
            -webkit-box-shadow: 0px 9px 0px rgba(219, 31, 5, 1), 0px 9px 25px rgba(0, 0, 0, .7);
            -moz-box-shadow: 0px 9px 0px rgba(219, 31, 5, 1), 0px 9px 25px rgba(0, 0, 0, .7);
            box-shadow: 0px 9px 0px rgba(219, 31, 5, 1), 0px 9px 25px rgba(0, 0, 0, .7);
            margin: 0px auto;
            width: 160px;
            text-align: center;

            -webkit-transition: all .1s ease;
            -moz-transition: all .1s ease;
            -ms-transition: all .1s ease;
            -o-transition: all .1s ease;
            transition: all .1s ease;
        }

        a.button2:active {
            -webkit-box-shadow: 0px 3px 0px rgba(219, 31, 5, 1), 0px 3px 6px rgba(0, 0, 0, .9);
            -moz-box-shadow: 0px 3px 0px rgba(219, 31, 5, 1), 0px 3px 6px rgba(0, 0, 0, .9);
            box-shadow: 0px 3px 0px rgba(219, 31, 5, 1), 0px 3px 6px rgba(0, 0, 0, .9);
            position: relative;
            text-decoration: none;
            color: rgba(200, 200, 200, 1);
            top: 6px;
        }

        a.buttonPrint:active {
            -webkit-box-shadow: 0px 3px 0px rgba(219, 31, 5, 1), 0px 3px 6px rgba(0, 0, 0, .9);
            -moz-box-shadow: 0px 3px 0px rgba(219, 31, 5, 1), 0px 3px 6px rgba(0, 0, 0, .9);
            box-shadow: 0px 3px 0px rgba(219, 31, 5, 1), 0px 3px 6px rgba(0, 0, 0, .9);
            position: relative;
            text-decoration: none;
            color: rgba(200, 200, 200, 1);
            top: 6px;
        }

        .active {
            -webkit-box-shadow: 0px 3px 0px rgba(219, 31, 5, 1), 0px 3px 6px rgba(0, 0, 0, .9);
            -moz-box-shadow: 0px 3px 0px rgba(219, 31, 5, 1), 0px 3px 6px rgba(0, 0, 0, .9);
            box-shadow: 0px 3px 0px rgba(219, 31, 5, 1), 0px 3px 6px rgba(0, 0, 0, .9);
            position: relative;
            color: rgba(200, 200, 200, 1);
            top: 6px;
            margin: 10px auto;
            width: 160px;
            text-align: center;
            border-radius: 8px;
            font-family: 'Yanone Kaffeesatz';
            font-weight: 700;
            font-size: 25px;
            display: block;
            padding: 4px;
            background-color: rgba(219, 87, 5, 1);
            text-decoration: none;
        }
    </style>
    <script>
        window.odometerOptions = {
            format: '(ddd).dd'
        };
    </script>
    <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/odometer/css/odometer-theme-car.css"/>
    <script type="text/javascript"
            src="${springMacroRequestContext.contextPath}/js/jqgrid/jquery-1.11.1.min.js"></script>
    <script src="${springMacroRequestContext.contextPath}/odometer/js/odometer.js"></script>
    <script src="${springMacroRequestContext.contextPath}/js/scroll.js"></script>
    <script type="text/javascript"
            src="${springMacroRequestContext.contextPath}/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
</head>
<body>
<style>
    ul, li, dl, ol {
        list-style: none;
    }

    .lotteryContent li {
        height: 40;
    }
</style>

<div class="parent">
    <div style="width:100%; height: 100%; font-size: 30px;">
        <div style="text-align: center;font-family: 'Microsoft YaHei UI';font-size: 35px;color: #dbdbdb;font-weight:bolder">
            <p style="cursor: pointer;pointer-events: auto;" onclick="$('#instruction').fadeIn('slow');">号码分发说明</p>
        </div>
        <div style="width: 40%;height:500px;background-color: #dcdcdc;position: absolute;top: 0;bottom: 0;left: 5%;
            margin-top:12%; background:rgba(150,0,0,0.5); border-radius: 15px;">
            <div style="height:11%;border-radius: 15px 15px 0px 0px;text-align: center;font-size:30px;font-family: 微软雅黑;  background:rgba(150,0,0,0.5);">
                <span style="color:#E5E3E3 ">人员信息</span><span onclick="getDisOrderData()"
                                                              style="cursor:pointer;color:#E5E3E3;float: right;padding-right: 15px ">随机</span>
            </div>
            <div id="info"
                 style="text-align: center;width:100%;height: 89%;font-size:25px;font-family: 'Microsoft YaHei UI';color:white">
                <div class="luckTitle" style="width: 100%;">
                    <ul style="color: #dddddd;width: 100%;padding: 5px;margin:unset;text-align: center;font-size:22px;font-family: 'Microsoft YaHei UI';color:white">
                        <li>
                            <span style="width:10%;display: inline-block;">序号</span>
                            <span style="width:14%;display: inline-block;">姓名</span>
                            <span style="width:35%;display: inline-block;">部门</span>
                            <span style="width:35%;display: inline-block;">号码</span>

                        </li>
                    </ul>
                </div>
                <div class="luckContent" style="overflow:hidden;height:90%">
                    <ul id="lotteryContent"
                        style="color: #dddddd;width: 100%;padding: unset;margin:unset;pxtext-align: center;font-size:22px;font-family: 'Microsoft YaHei UI';color:white">
                    <#if list??>
                    <#list list as user>
                        <#if user_index%2==1>
                            <li>
                        <#else>
                            <li style="background-color: rgba(169, 183, 198, 0.2)">
                        </#if>
                                <span style="width:10%;display: inline-block;height: 37px;white-space:nowrap;">${user.index}</span>
                                <span style="width:14%;display: inline-block;white-space:nowrap;overflow: hidden;">${user.userName}</span>
                                <span style="width:35%;display: inline-block;white-space:nowrap;overflow: hidden;">${user.deptName}</span>

                            <#if user.prePickFlag??&& user.prePickFlag=="Y">
                                    <span style="width:35%;display: inline-block;white-space:nowrap;overflow: hidden;color: #999999;">${user.phoneNumber!""}</span>
                            <#else>
                                    <span style="width:35%;display: inline-block;white-space:nowrap;overflow: hidden;">${user.phoneNumber!""}</span>
                            </#if>
                            </li>
                    </#list>
                    </#if>
                    </ul>
                </div>

            </div>
        </div>
        <div style="width: 40%;height:500px;background-color: #dcdcdc;position: absolute;top: 0;bottom: 0;right: 5%;
            margin-top:12%; background:rgba(150,0,0,0.5); border-radius: 15px;">
            <div style="height:40px;border-radius: 15px 15px 0px 0px;text-align: center;font-size:30px;font-family: 'Microsoft YaHei UI';  background:rgba(150,0,0,0.5);">
                <span style="color:#E5E3E3 ">当前随机号码信息</span></div>
            <div style="height: 30%">
                <p id="description"
                   style="padding-top:7%;color: white;font-size: 22px;text-align: center;margin: unset;height: 24px;">
                </p></span>
                <p id="statistics"
                   style="padding-top:7%;color: white;font-size: 22px;text-align: center;margin: unset;height: 24px;">
                    已选/总数：0/${award?size}</p></span></div>
            <div id="number"
                 style="text-align: center;width:100%;height: 59%;font-size:25px;font-family: 'Microsoft YaHei UI';color:white;">
                <div class="odometer">88888888888</div>
            </div>
        </div>
        <div style="position: absolute; top: 70%; left: 43%; width: 200px; height: 50px;">
            <#if doneStatus=='Y'>
            <a href="javascript:void(0);" style="display:none;text-decoration: none" class="button2 buttons" ondblclick="goRandom()">开始</a>
            <a id="printResult" href="javascript:void(0);" style="text-decoration: none"
               class="buttonsPrint" onclick="location='${springMacroRequestContext.contextPath}/lottery/print'">打印结果</a>
        <#else>
                <a href="javascript:void(0);" style="text-decoration: none" class="button2 buttons" ondblclick="goRandom()">开始</a>
                <a id="printResult" href="javascript:void(0);" style="display: none;text-decoration: none"
                   class="buttonsPrint" onclick="location='${springMacroRequestContext.contextPath}/lottery/print'">打印结果</a>
        </#if>
        </div>
        <div class="leftmove" style="text-align: center;color: #9d9d9d; position:relative; top: 31%;">
            <span style="font-size: 50px;"><<</span>
        </div>
    </div>
</div>
</div>
<script>
    function getDisOrderData(Obj) {
        if (randomFlg) {

            $.ajax({
                url: "${springMacroRequestContext.contextPath}/lottery/disorder",
                async: false,
                success: function (data) {
                    $("#info").html($(data).find("#info").html());
                }
            });
        }
    }

    $(function () {
        $('.bot-img ul li').click(function () {
            var _this = $(this);
            _this.addClass('active').siblings('li').removeClass('active');
            var int = _this.index();
            $('.activeimg').animate({left: int * -600}, "slow");
        });
        var list = $('.bot-img ul li').length;
        $('.activeimg').css({
            width: list * 600,
        });
        $('.right').click(function () {
            next(list)

        })
        $('.left').click(function () {
            prev(list)
        });

        //×Ô¶¯²¥·Å 2Ãë²¥·ÅÒ»´Î ÎÞÏÞÑ­»·
        var timer = '';
        var num = 0;
        timer = setInterval(function () { //´ò¿ª¶¨Ê±Æ÷
            num++;
            if (num > parseFloat(list) - 1) {
                num = 0;
                $('.activeimg').animate({left: num * -600}, "slow");
            } else {
                $('.activeimg').animate({left: num * -600}, "slow");
            }
        }, 1000000);
    })
    var index = 0;

    //ÏÂÒ»ÕÅ
    function next(list) {
        if (index < list - 1) {
            index++;
            $('.activeimg').animate({left: index * -600}, "slow");
            $('.bot-img ul li').eq(index).addClass('active').siblings('li').removeClass('active')
        } else {
            index = 0;
            $('.activeimg').animate({left: index * -522}, "slow");
            $('.bot-img ul li').eq(index).addClass('active').siblings('li').removeClass('active')
        }
    }

    //        ÉÏÒ»ÕÅ
    function prev(list) {
        index--;
        if (index < 0) {
            index = list - 1;
            $('.activeimg').animate({left: index * -600}, "slow");
            $('.bot-img ul li').eq(index).addClass('active').siblings('li').removeClass('active')
        } else {
            $('.activeimg').animate({left: index * -600}, "slow");
            $('.bot-img ul li').eq(index).addClass('active').siblings('li').removeClass('active')
        }
    }

    $(function () {
        $('.button2').dblclick(function () {
            $(this).addClass('active').removeClass('buttons2').unbind('click');
        });
        var i = 0;
        var timer = setInterval(function () {
            if (i == 0) {
                $('.leftmove').animate({'padding-left': '0%'}, 1000);
                i = 1;
            } else {
                $('.leftmove').animate({'padding-left': '3%'}, 1000);
                i = 0;
            }

        }, 1000);
    });
    var randomFlg = true;
    var i = 0;
    var timeFunc;
    var intervalId
    var data;

    function goRandom() {
        if (randomFlg) {
            randomFlg = false;
            $.ajax({
                type: "GET",
                url: "${springMacroRequestContext.contextPath}/lottery/goRandom",
                data: {userName: "admin", userNumber: "admin"},
                dataType: "json",
                success: function (json) {
                    data = json
                    var lidom = "";
                    for (i = 0; i < data.length; i++) {
                        if (i % 2 == 0) {
                            lidom += '<li style="background-color: rgba(169, 183, 198, 0.2)">';
                        }
                        else {
                            lidom += '<li>';
                        }
                        if(data[i].prePickFlag=='Y'){

                        lidom += '<span style="width:10%;display: inline-block;height: 37px;white-space:nowrap;">' + data[i].index + '</span><span style="width:14%;display: inline-block;white-space:nowrap;overflow: hidden;">' + data[i].userName + '</span> <span style="width:35%;display: inline-block;white-space:nowrap;overflow: hidden;">' + data[i].deptName + '</span><span id="li_' + i + '" style="width:35%;display: inline-block;white-space:nowrap;overflow: hidden;color: #999999">'+data[i].phoneNumber+'</span>';
                        }else{

                        lidom += '<span style="width:10%;display: inline-block;height: 37px;white-space:nowrap;">' + data[i].index + '</span><span style="width:14%;display: inline-block;white-space:nowrap;overflow: hidden;">' + data[i].userName + '</span> <span style="width:35%;display: inline-block;white-space:nowrap;overflow: hidden;">' + data[i].deptName + '</span><span id="li_' + i + '" style="width:35%;display: inline-block;white-space:nowrap;overflow: hidden;"></span>';
                        }

                        lidom += '</li>';
                    }
                    $('#lotteryContent').html(lidom);

                    var flg = false;
                    var i = 0;
                    var len = data.length;
                    intervalId = setInterval(showRandomPhoneNumber, 2000);

                }
            });
        }

    }

    //随机函数体
    function showRandomPhoneNumber() {
        if (i == 0) {
            $('.odometer').html(data[i].phoneNumber);
            $('#li_' + i).html(data[i].phoneNumber);


            //走马灯
            timeFunc = setTimeout(function () {
                $("div.luckContent").myScroll({
                    speed: 2000,
                    rowHeight: 45
                });
            }, 5500);
        } else if (i < data.length) {

            if (i != data.length - 1)
                $('.odometer').html(data[i + 1].phoneNumber);

            $('#li_' + i).html(data[i].phoneNumber);
            if (i == 10) {

                clearInterval(intervalId);
                clearInterval(intId[0]);
                intervalId = setInterval(showRandomPhoneNumber, 200);
                timeFunc = setTimeout(function () {
                    $("div.luckContent").myScroll({
                        speed: 200,
                        rowHeight: 45
                    });
                }, 0);
            }
            if(i==(data.length-10)){
                clearInterval(intervalId);
                clearInterval(intId[0]);
                intervalId = setInterval(showRandomPhoneNumber, 2000);
                timeFunc = setTimeout(function () {
                    $("div.luckContent").myScroll({
                        speed: 2000,
                        rowHeight: 45
                    });
                }, 0);
            }

        } else {
            clearInterval(intervalId);
            clearTimeout(intId[0]);
        }
        if (i == data.length - 1) {
            $('#printResult').show();
        }
        $('#statistics').html("已选/总数：" + (i + 1) + "/${award?size}");
        ++i;
    }
</script>
<div id="instruction" style="text-align: center;padding-top: 8%;display: none">
    <!-- 存放大图的容器-->
    <div class="all">
        <div class="top-img">
            <div class="activeimg" onclick="$('#instruction').fadeOut('slow');">
                <img src="${springMacroRequestContext.contextPath}/img/1.jpg">
                <img src="${springMacroRequestContext.contextPath}/img/2.jpg">
                <img src="${springMacroRequestContext.contextPath}/img/3.jpg">
                <img src="${springMacroRequestContext.contextPath}/img/4.jpg">
                <img src="${springMacroRequestContext.contextPath}/img/5.jpg">
            </div>
            <div class="bot-img" style="display: none">
                <ul>
                    <li class="active"><img src="${springMacroRequestContext.contextPath}/img/1.jpg"></li>
                    <li><img src="${springMacroRequestContext.contextPath}/img/2.jpg"></li>
                    <li><img src="${springMacroRequestContext.contextPath}/img/3.jpg"></li>
                    <li><img src="${springMacroRequestContext.contextPath}/img/4.jpg"></li>
                    <li><img src="${springMacroRequestContext.contextPath}/img/5.jpg"></li>
                </ul>
            </div>
            <div class="left"><img src="${springMacroRequestContext.contextPath}/img/left.png"></div>
            <div class="right"><img src="${springMacroRequestContext.contextPath}/img/right.png"></div>
        </div>
    </div>

</div>

</body>
</html>