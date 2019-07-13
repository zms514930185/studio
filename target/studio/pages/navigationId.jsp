<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>导航</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/studio/backstage/navigation/${navigation.id}" method="get">
    名称：<input type="text" name="name" style="width:70px">
    状态：<input type="text" name="status" style="width:100px">
    <%--序号：<input type="text" name="id" style="width:50px">
    创建时间：<input type="text" name="createAt" style="width:100px">
    修改时间：<input type="text" name="updateAt" style="width:100px">
    编辑人：<input type="text" name="createBy" style="width:100px">--%>
    <input type="submit" value="查询">
</form>
<form action="${pageContext.request.contextPath}/studio/backstage/navigation/${navigation.id}" method="post">
    名称：<input type="text" name="name" style="width:70px">
    <%--状态：<input type="text" name="status" style="width:100px">
    序号：<input type="text" name="id" style="width:50px">
    创建时间：<input type="text" name="createAt" style="width:100px">
    修改时间：<input type="text" name="updateAt" style="width:100px">
    编辑人：<input type="text" name="createBy" style="width:100px">--%>
    <input type="submit" value="增加">
</form>
</table>
<%--border：设置边框粗细，默认为0，就是没有，值越大框越粗，不过应该没必要。
cellspacing：单元格间距，默认是1，设置为0，不然显示出来单元格间有距离，好难看。
cellpadding：单元格边距，默认是1，如果觉得太挤了可以设置,--%>
<table border="1" cellspacing="0" cellpadding="3">
    <tr>
        <th>序号</th>
        <%-- <th>等级</th>--%>
        <th>父导航</th>
        <th>名称</th>
        <th>状态</th>
        <th>创建时间</th>
        <th>修改时间</th>
        <th>修改人</th>
        <th>编辑</th>
        <th>二级标题</th>
        <th>上下架</th>
        <th>删除</th>
    </tr>
    <c:forEach items="${selectNavigation}" var="navigation">
        <tr>
            <form action="${pageContext.request.contextPath}/studio/backstage/navigation/${navigation.id}" method="post">
                <input type="hidden" name="_method" value="PUT">
                <input type="hidden" name="id" value="${navigation.id}">
                <td>${navigation.id}</td>
                <td>${fatherName}</td>

                <td><input type="text" name="name" value="${navigation.name}"></td>
                    <%--判断当前状态值，设置一个对应字符串--%>
                <c:if test="${navigation.status==10}">
                    <td>下架</td>
                </c:if>
                <c:if test="${navigation.status==20}">
                    <td>上架</td>
                </c:if>
                <input type="hidden" name="status" value="${navigation.status}">
                <td>${navigation.createAt}</td>
                <td>${navigation.updateAt}</td>
                <td>${navigation.updateBy}</td>
                <td><input type="submit" value="修改"></td>
            </form>
            <td>
            </td>
            <td>
                <form action="${pageContext.request.contextPath}/studio/backstage/navigation/${navigation.id}" method="post">
                    <input type="hidden" name="_method" value="PUT">
                    <input type="hidden" name="id" value="${navigation.id}">
                        <%--判断状态值后更改状态值使按钮完成上下架操作--%>
                    <c:if test="${navigation.status==10}">
                        <input type="hidden" name="status" value=20>
                        <input type="submit" value="上架">
                    </c:if>
                    <c:if test="${navigation.status==20}">
                        <input type="hidden" name="status" value=10>
                        <input type="submit" value="下架">
                    </c:if>
                </form>
            </td>
            <td>
                <form action="${pageContext.request.contextPath}/studio/backstage/navigation/${navigation.id}" method="post">
                    <input type="hidden" name="_method" value="DELETE">
                    <input type="hidden" name="id" value="${navigation.id}">
                    <input type="submit" value="删除">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
