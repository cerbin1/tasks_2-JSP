<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

    <head>
        <title>Login</title>
        <link rel="stylesheet" href="bootstrap.min.css">
        <link rel="stylesheet" href="style.css">
    </head>

    <body>
        <jsp:include page="navbar.jsp" />

        <c:if test="${empty users}">
            <span>No results</span>
        </c:if>
        <c:if test="${not empty users}">
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Email</th>
                        <th scope="col">Login</th>
                        <th scope="col">Name</th>
                        <th scope="col">Surname</th>
                        <th scope="col">Is active</th>
                        <th scope="col">Messages count</th>
                        <th scope="col">Remove</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <th scope="row">${user.id}</th>
                            <td>${user.email}</td>
                            <td>${user.username}</td>
                            <td>${user.name}</td>
                            <td>${user.surname}</td>
                            <td>
                                <c:if test="${user.active}">
                                    Yes
                                </c:if>
                                <c:if test="${not user.active}">
                                    No
                                </c:if>
                            </td>
                            <td>0 TODO</td>
                            <td>
                                <form action="/tasks_2-JSP/removeUser?userId=${user.id}"
                                    method="post">
                                    <button class="btn btn-danger" type="submit">Remove</button>
                                </form>
                                </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </body>

</html>