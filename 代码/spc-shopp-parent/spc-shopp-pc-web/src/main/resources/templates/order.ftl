<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>订单查询</title>
</head>
<body>

<div id="login_container">
		<table style="border: 1px solid blue;">
				<tr>
						<td>商户订单号</td>
						<td>订单名称</td>
						<td>付款金额</td>
						<td>商品描述</td>
						<td>支付宝交易号</td>
						<td>退款金额</td>
						<td>退款原因</td>
						<td>退款请求号</td>
				</tr>
				<#list orderList as orderList>
				<tr>
						<td>${orderList.widoutTradeNo}</td>
						<td>${orderList.widsubject}</td>
						<td>${orderList.widtotalAmount}</td>
						<td>
							<#if orderList.widbody ??>
								${orderList.widbody}
							</#if>
						</td>
						<td>
							<#if orderList.widtqtradeNo ??>
								${orderList.widtqtradeNo}
							</#if>
						</td>
						<td>
							<#if orderList.widtrrefundAmount ??>
								${orderList.widtrrefundAmount}
							</#if>
						</td>
						<td>
							<#if orderList.widtrrefundReason ??>
								${orderList.widtrrefundReason}
							</#if>
						</td>
						<td>
							<#if orderList.widtroutRequestNo ??>
								${orderList.widtroutRequestNo}
							</#if>
						</td>
				</tr>
				</#list>
		</table>
</div>


</body>
</html>    