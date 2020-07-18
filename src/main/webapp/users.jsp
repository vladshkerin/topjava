<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html lang="ru">
<head>
    <title>User list</title>
    <style>
        .enabled {
            color: black;
        }

        .disabled {
            color: gray;
        }
    </style>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>Users</h2>
    <a href="users?action=create">Add User</a>
    <br/><br/>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Role</th>
            <th>Registered</th>
        </tr>
        </thead>
        <c:forEach items="${users}" var="user">
            <jsp:useBean id="user" type="ru.javawebinar.topjava.to.UserTo"/>
            <tr class="${user.enabled ? 'enabled' : 'disabled'}">
                <td>${user.name}</td>
                <td>${user.email}</td>
                <td>${user.roles}</td>
                <td>${fn:formatDate(user.registered)}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
