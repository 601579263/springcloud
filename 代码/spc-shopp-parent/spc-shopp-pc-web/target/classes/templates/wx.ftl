<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>第三方登录---微信登录</title>
</head>
<body>

<div id="login_container">
这是装二维码的容器div
</div>

<script src="http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js"></script>
<script type="text/javascript">
//id和scope不用改、appid和redirect_uri改为自己的
  var obj = new WxLogin({
                           id:"login_container", 
                           appid: "***************", 
                           scope: "snsapi_login", 
            redirect_uri:"http%3A%2F%2Fwww.baidu.cn",
                           state: "",
                           style: "black",
                           href: ""
                         });

</script>
</body>
</html>    