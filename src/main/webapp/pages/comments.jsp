<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<json:object>
    <json:property name="code" value="${code}"/>
    <json:property name="msg" value="${msg}"/>
    <json:object name="data"/>
    <json:array name="data">
        <c:forEach items="${data}" var="data" >
            <json:object >
                <json:property name="${data.key}" value="${data.value}"/>
            </json:object>
        </c:forEach>
    </json:array>
</json:object>
