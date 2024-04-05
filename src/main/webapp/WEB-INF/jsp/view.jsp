<!DOCTYPE html>
<html>
<head>
    <title>Book information</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Log out" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>


<%--
@GetMapping("/view/{bookId}")
public String view(@PathVariable("bookId") long bookId, ModelMap model) throws BookNotFound {
Book book = bService.getBook(bookId);
model.addAttribute("bookId", bookId);
model.addAttribute("book", book);
model.addAttribute("comments", book.getComments());
return "view";
}
--%>


<div class="container">
    <h2>Book #${bookId}: <c:out value="${book.name}"/></h2>
    <i>Author - <c:out value="${book.author}"/></i><br/><br/>
    Availability - <c:out value="${book.number}"/><br/><br/>
    <c:if test="${!empty book.attachments}">
        Coverpage:
        <c:forEach items="${book.attachments}" var="attachment" varStatus="status">
            <c:if test="${!status.first}">, </c:if>
            <a href="<c:url value="/book/${bookId}/attachment/${attachment.id}" />">
                <c:out value="${attachment.name}"/></a>
        </c:forEach><br/><br/>
    </c:if>
    <a href="<c:url value="/book" />" class="btn btn-primary">Return to list book</a>
</div>

<h2>Comments of the book</h2>


<%--<c:if test="${not empty comments}">
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
</form:form>--%>


<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>