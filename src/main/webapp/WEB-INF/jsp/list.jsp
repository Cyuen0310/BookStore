<!DOCTYPE html>
<html>
<head>
    <title>Book store</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .book-card {
            max-width: 200px;
            margin-bottom: 20px;
        }

        .book-card img {
            height: 200px;
            object-fit: cover;
            width: 100%;
            border-radius: 4px 4px 0 0;
        }

        .book-card .card-body {
            padding: 10px;
        }

        .book-card .card-title {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 10px;
        }

        .book-card .card-text a {
            color: #007bff;
        }

        .book-card .card-text a:hover {
            text-decoration: none;
        }

        .container {
            padding-top: 20px;
        }

        .no-books {
            font-style: italic;
        }

        .manage-accounts {
            margin-top: 20px;
            font-weight: bold;
        }
    </style>
</head>
<body>
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Log out" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<div class="container">
    <h2>Books</h2>
    <a href="<c:url value="/book/create" />" class="btn btn-primary">Create a book</a><br/><br/>
    <c:choose>
        <c:when test="${fn:length(bookDatabase) == 0}">
            <p class="no-books">There are no books in the system.</p>
        </c:when>
        <c:otherwise>
            <div class="card-deck">
                <c:forEach items="${bookDatabase}" var="entry">
                    <div class="card book-card">
                        <c:if test="${not empty entry.attachments}">
                            <img src="<c:url value="/book/${entry.id}/attachment/${entry.attachments.get(0).id}" />" alt="Book Cover"/>
                        </c:if>
                        <div class="card-body">
                            <h5 class="card-title">Book ${entry.id}: <c:out value="${entry.name}"/></h5>
                            <p class="card-text">
                                <a href="<c:url value="/book/view/${entry.id}" />">
                                    Details
                                </a>
                            </p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<security:authorize access="hasRole('ADMIN')">
    <a href="<c:url value="/user" />" class="manage-accounts">Manage User Accounts</a><br /><br />
</security:authorize>

<a href="<c:url value="/user/edit/${pageContext.request.userPrincipal.name}"/>" class="manage-accounts">Edit information</a><br /><br />


<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>