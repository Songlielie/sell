<html>
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">

    <#--边栏sidebar-->
    <#include "../common/nav.ftl">

    <div id="page-content-wrapper">
        <div class="container ">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>订单id</th>
                        <th>姓名</th>
                        <th>手机号</th>
                        <th>地址</th>
                        <th>金额</th>
                        <th>订单状态</th>
                        <th>支付状态</th>
                        <th>创建时间</th>
                        <th colspan="2">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list orderDTOPage.content as orderDTO>
                        <tr>
                            <td>${orderDTO.getOrderId()}</td>
                            <td>${orderDTO.getBuyerName()}</td>
                            <td>${orderDTO.getBuyerPhone()}</td>
                            <td>${orderDTO.getBuyerAddress()}</td>
                            <td>${orderDTO.getOrderAmount()}</td>
                            <td>${orderDTO.getOrderStatusEnum().msg}</td>
                            <td>${orderDTO.getOrderPayStatusEnum().msg}</td>
                            <td>${orderDTO.getCreateTime()}</td>
                            <td><a href="detail?orderId=${orderDTO.getOrderId()}">详情</a></td>
                            <td>
                                <#if orderDTO.getOrderStatusEnum().msg == resultEnum>
                                    <a href="cancel?orderId=${orderDTO.getOrderId()}">取消</a>
                                </#if>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>

            <#--        分页-->
            <div class="col-md-12 column">
                <ul class="pagination pull-right">
                    <li>
                        <#if currentPage lte 1>
                    <li class="disabled"><a href="#">上一页</a></li>
                    <#else>
                        <li><a href="list?page=${currentPage-1}&size=${pageSize}">上一页</a></li>
                    </#if>
                    </li>
                    <li>
                        <#list 1..orderDTOPage.getTotalPages() as index>
                        <#if currentPage ==index>
                    <li class="disabled"><a>${index}</a></li>
                <#else>
                    <li><a href="list?page=${index}&size=${pageSize}">${index}</a></li>
                    </#if>
                    </#list>
                    </li>
                    <li>
                        <#if currentPage gte orderDTOPage.getTotalPages()>
                    <li class="disabled"><a>下一页</a></li>
                <#else>
                    <li><a href="list?page=${currentPage+1}&size=${pageSize}">下一页</a></li>
                    </#if>
                    </li>
                </ul>
            </div>
        </div>
        </div>
    </div>
</div>
<script>
    alert(${resultEnum})

</script>
</body>
</html>