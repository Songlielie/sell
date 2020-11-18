<head xmlns:https="http://www.w3.org/1999/xhtml">
    <meta charset="UTF-8">
    <title>卖家订单取消失败</title>
    <link href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="alert alert-dismissable alert-danger">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4>
                    错误！
                </h4> <strong>${msg}</strong> <a href="${url}" class="alert-link">3秒后自动跳转</a>
            </div>
        </div>
    </div>
</div>
<script>
    setTimeout('location.href="${url}"', 2000);
</script>
</body>
