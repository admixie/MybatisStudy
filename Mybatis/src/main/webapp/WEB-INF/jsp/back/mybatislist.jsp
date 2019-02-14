<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>列表页面</title>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div style="padding: 20px;">
    <form class="form-inline" role="form" action="MybatisListServlet" method="post">
        <div class="form-group">
            <input type="text" class="form-control" id="keyword" name="keyword"
                   placeholder="请输入名称" value="${keyword}">
        </div>
        <div class="form-group">
            <input type="text" class="form-control" id="description" name="description"
                   placeholder="请输入说明" value="${description}">
        </div>
        <button type="submit" class="btn btn-default">查询</button>
    </form>
    <table class="table">
        <caption>关键字列表（使用mybatis框架)</caption>
        <thead>
        <tr>
            <th>选择</th>
            <th>关键字</th>
            <th>说明</th>
            <th>回复内容</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${ messagelist }" var="message" varStatus="status">
            <c:if test="${status.index%2==0}">
            <tr class="active"> </c:if>
            <c:if test="${status.index%2!=0}">
            <tr class="success"></c:if>
            <td>
                <input type="checkbox">
            </td>
            <td>${message.getCOMMAND()}</td>
            <td>${message.getDESCRIPTION()}</td>
            <td>${message.getCONTENT()}</td>
            </tr>
            <c:if test="${status.index%2!=0}">
            </c:if>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
