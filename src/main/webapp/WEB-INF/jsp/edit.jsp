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

<h2>Edit book #${book.id}</h2>
<form:form method="POST" enctype="multipart/form-data"  modelAttribute="bookForm">
    <form:label path="name">Name</form:label><br/>
    <form:input type="text" path="name"/><br/><br/>
    <form:label path="author">Author</form:label><br/>
    <form:input type="text" path="author"/><br/><br/>
    <form:label path="number">Quantity</form:label><br/>
    <form:input type="number" path="number"/><br/><br/>
    <b>Attachments</b><br/>
    <input type="file" name="attachments" multiple="multiple"/><br/><br/>
    <input type="submit" value="Submit"/>
</form:form>
<a href="<c:url value="/book" />">Return to list books</a>
</body>
</html>