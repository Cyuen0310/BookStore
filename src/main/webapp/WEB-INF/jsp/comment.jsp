<!DOCTYPE html>
<html>
<head>
    <title>Add Comment</title>
</head>
<body>
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Log out" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<h2>Comments of the book</h2>
<c:if test="${not empty comments}">
    <ol>
        <c:forEach var="comment" items="${comments}">
            <li>${comment.text}</li>
        </c:forEach>
    </ol>
</c:if>
<c:if test="${empty comments}">
    <p>No comments yet.</p>
</c:if>

<h2>Create a comment</h2>
<form:form method="POST" enctype="multipart/form-data" modelAttribute="CForm">
    <form:label path="comment">Comment</form:label><br/>
    <form:input type="text" path="comment"/><br/><br/>
    <input type="submit" value="Submit"/>
</form:form>

<a href="<c:url value="/book" />" class="btn btn-primary">Return to list book</a>
</body>
</html>