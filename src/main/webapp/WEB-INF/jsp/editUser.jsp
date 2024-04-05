<!DOCTYPE html>
<html>
<head>
    <title>Customer Support</title>
</head>
<body>
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Log out" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<h2>Edit User Information (User: ${user.username})</h2>
<form:form method="POST" modelAttribute="userForm">
    <form:label path="username">Username</form:label><br/>
    <form:input type="text" path="username" disabled="true"/><br/><br/>
    <form:label path="password">Password</form:label><br/>
    <form:input type="text" path="password" value="${fn:substringAfter(user.password, '{noop}')}"/><br/><br/>

    <input type="submit" value="Submit"/>
</form:form>
<a href="<c:url value="/user/list" />">Return to list users</a>
</body>
</html>