<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>幸运摇号</title>
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
            background: url('${springMacroRequestContext.contextPath}/img/luckbg.png');
            background-size: 100%;
        }
        .odometer {
            font-size: 50px;
        }
    </style>
    <script>
        window.odometerOptions = {
            format: '(ddd).dd'
        };
    </script>
    <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/odometer/css/odometer-theme-car.css" />
    <script src="${springMacroRequestContext.contextPath}/odometer/js/odometer.js"></script>
    <script type="text/javascript"
            src="${springMacroRequestContext.contextPath}/js/jqgrid/jquery-1.11.1.min.js"></script>
    <script>
        $(function () {
          //  setInterval("sawp()","200");
        });
        function sawp(){
            var persions=new Array("李振宇  J3000183","卢瑞苗  31001062","李佩珊  J3501248","洪泽斌  J3501328","涂超群  J3501435","曾宝宜  J3501419","李雪松  J3000173","聂赫男  J3501248");


                $('#a').html(persions[getRandom1(0,persions.length)]);
            $('#b').html(persions[getRandom1(0,persions.length)]);
            $('#c').html(persions[getRandom1(0,persions.length)]);
            $('#d').html(persions[getRandom1(0,persions.length)]);
            $('#e').html(persions[getRandom1(0,persions.length)]);

        }
        function getRandom1(start, end) {
            var length = end - start;
            var num = parseInt(Math.random() * (length) + start);
            return num;
        }
    </script>
</head>
<body>
<div class="parent">
    <div>
        <div style="width: 40%;height:350px;background-color: #dcdcdc;position: absolute;top: 0;bottom: 0;left: 5%;
            margin-top:5%; background:rgba(255,255,255,0.4); border-radius: 15px;">
            <div style="height:11%;border-radius: 15px 15px 0px 0px;text-align: center;font-size:30px;font-family: 'Microsoft YaHei UI';  background:rgba(150,0,0,0.5);"><span style="color:#E5E3E3 ">人员信息</span></div>
            <div id="info" style="text-align: center;width:100%;height: 89%;font-size:25px;font-family: 'Microsoft YaHei UI';color:yellow">
                <p id="a">李振宇  J3000183 </p>
                <p id="b">卢瑞苗  31001062 </p>
                <p id="c">李佩珊  J3501248 </p>
                <p id="d">李振宇  J3000183 </p>
                <p id="e">洪泽斌  J3501328 </p>
            </div>
        </div>
        <div style="width: 40%;height:350px;background-color: #dcdcdc;position: absolute;top: 0;bottom: 0;right: 5%;
            margin-top:5%; background:rgba(255,255,255,0.4); border-radius: 15px;">
            <div style="height:40px;border-radius: 15px 15px 0px 0px;text-align: center;font-size:30px;font-family: 'Microsoft YaHei UI';  background:rgba(150,0,0,0.5);"><span style="color:#E5E3E3 ">号码信息</span></div>
        <div id="number" style="text-align: center;width:100%;height: 89%;font-size:25px;font-family: 'Microsoft YaHei UI';color:yellow">
            <div class="odometer">88888888888</div>
        </div>
        </div>

    </div>
</div>
</div>
<script>
    setTimeout(function(){
        $('.odometer').html(13631407625);
    }, 1000);
</script>
</body>
</html>