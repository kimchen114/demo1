<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/js/jquery.form/jquery.form.js?v=2017080701"></script>
<script type="text/javascript" src="${ctx}/js/mustache/mustache.min.js?v=2017080701"></script>
<script type="text/javascript" src="${ctx}/customer/js/app.js?v=2017092701"></script>
<script type="text/javascript" src="${ctx}/customer/js/page.js?v=2017092701"></script>
<script type="text/javascript" src="${ctx}/pages/news/vipOrderRecord/js/vipOrder.js?v=2017092701"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/css/common.css?v=2017080701">


<!--一级标题-->
<div class="landtitle">
    <h3 class="col-xs-5" >VIP购买记录（共<span id="totalNum"></span>条信息）</h3>
    <a class="btn btn-primary btn-sm"
    href="#" onclick="exported()">导出</a>
</div>
<div class="mt10 pl20 pr20">
    <div class="mb10 clearfix">
        <div class="form-inline form-inline-sm">
            <div class="searchVo display-ib">
                <div class="form-group form-group-sm">
                    <label class="control-label">企业名称:</label>
                    <input type="text" class="form-control" param="qyname" id="userAccount"
                           placeholder="输入企业名称">
                </div>
                <div class="form-group form-group-sm">
                    <label class="control-label">用户名:</label>
                    <input type="text" class="form-control" param="name" id="companyNameVal"
                           placeholder="输入用户名">
                </div>
                <div class="form-group form-group-sm">
                    <label class="control-label">销售人员:</label>
                    <select class="form-control input-medium" param="sell" id="sell">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="form-group form-group-sm">
                    <label class="control-label">账期:</label>
                    <input type="text" id="period"
                           class="form-control timeInput" param="period"
                           onFocus="WdatePicker({dateFmt:'yyyy-MM',isShowClear:true,readOnly:true})"
                           data-type="datetime" placeholder="请选择时间">
                </div>

                <div class="form-group form-group-sm">
                    <label class="control-label">支付时间:</label> <input type="text" id="startTime"
                                                                      class="form-control timeInput"
                                                                      param="paystart"
                                                                      onFocus="WdatePicker({maxDate:'%y-{%M+1}-%d',dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})"
                                                                      data-type="datetime" placeholder="请选择时间">
                </div>
                <div class="form-group form-group-sm">
                    <label class="control-label ml-4">到</label> <input type="text" id="endTime"
                                                                       class="form-control timeInput"
                                                                       param="payend"
                                                                       onFocus="WdatePicker({maxDate:'%y-{%M+1}-%d',dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})"
                                                                       data-type="datetime" placeholder="请选择时间">
                </div>

                <div class="form-group form-group-sm ml10">
                    <label class="control-label">支付状态:</label>

                    <select class="form-control input-medium" param="status" id="status">
                        <option value="">全部</option>
                        <option  value="0">未支付</option>
                        <option  value="1">支付成功</option>
                    </select>
                </div>

                <div class="form-group form-group-sm ml10">
                    <label class="control-label">支付方式:</label>

                    <select class="form-control input-medium" param="type" id="type">
                        <option value="">全部</option>
                        <option value="1">支付宝</option>
                        <option value="2">转账</option>
                    </select>
                </div>
                <a role="button" class="btn btn-success btn-primary">查询</a>
            </div>
        </div>
    </div>
    <!--表格数据-->
    <table class="table table-hover">
        <thead>
        <tr>
            <%--<th width="3%"><input onclick="checkAll(this);" type="checkbox"></th>--%>
            <th class="1" width="12%">企业名称</th>
            <th class="2" width="8%">用户名</th>
            <th class="3" width="8%">注册时间</th>
            <th class="4" width="8%">购买账期</th>
            <th class="5" width="8%">购买人数</th>
            <th class="6" width="8%">金额</th>
            <th class="7" width="6%">支付状态</th>
            <th class="8" width="8%">支付方式</th>
            <th class="9" width="8%">支付时间</th>
            <th class="10" width="8%">开票状态</th>
            <th class="11" width="8%">销售人员</th>
            <th class="12" width="8%">流水号</th>
            <th  width="10%">操作</th>
            <th  width="3%"><a href="#" onclick="hidden1()">+</a></th>
        </tr>
        </thead>
        <tbody id="container"></tbody>
    </table>
    <!--分页-->
    <div id="page"></div>
</div>

<textarea id="template" class="template">
	{{#obj}}
			<tr>
                <td class="1">{{qyName}}</td>
                <td class="2">{{userName}}</td>
                <td class="3">{{registTime}}</td>
                <td class="4">{{accountPeriodDis}}</td>
                <td class="5">{{personCount}}</td>
                <td class="6">{{moneyDis}}</td>
                <td class="7">{{statusDis}}</td>
                <td class="8">{{typeDis}}</td>
                <td class="9">{{payTime}}</td>
                <td class="10">{{billDis}}</td>
                <td class="11">{{sellName}}</td>
                <td class="12">{{serialNumber}}</td>
                <td>
                    <a class="" href="javascript:update({{id}},{{companyId}})" title="编辑">编辑</a>
                </td>

            </tr>
         {{/obj}}
</textarea>
