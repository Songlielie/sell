<#assign cancel = springMacroRequestContext.contextPath+"/seller/order/">
<html>

<#include "../common/header.ftl" >

<body>
<div id="wrapper" class="toggled">
    <#include "../common/nav.ftl">
    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>详情号</th>
                        <th>订单号</th>
                        <th>产品号</th>
                        <th>产品名</th>
                        <th>产品价格</th>
                        <th>产品数量</th>
                        <th>产品图片</th>
                        <th>返回</th>

                    </tr>
                    </thead>
                    <tbody>
                    <#list orderDetailList as orderDetailList>
                        <tr>
                            <td>${orderDetailList.getDetailId()}</td>
                            <td>${orderDetailList.getOrderId()}</td>
                            <td>${orderDetailList.getProductId()}</td>
                            <td>${orderDetailList.getProductName()}</td>
                            <td>${orderDetailList.getProductPrice()}</td>
                            <td>${orderDetailList.getProductQuantity()}</td>
                            <td>${orderDetailList.getProductIcon()}</td>
                            <td><a href="list">返回</a></td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <#if orderDTO.getOrderStatusEnum().getMsg() == orderStatusNew>
                    <a type="button" class="btn btn-primary btn-default" href="${cancel}cancel?orderId=${orderId}">取消订单</a>
                </#if>
            </div>
            <div class="col-md-12 column">
                <#if orderDTO.getOrderPayStatusEnum().getMsg() == orderNoPay>
                    <a type="button" class="btn btn-primary btn-default" href="${cancel}finish?orderId=${orderId}">完成订单</a>
                </#if>
            </div>
        </div>
    </div>
</div>
<script>
    alert(${orderStatusNew})
</script>
</body>
</html>
