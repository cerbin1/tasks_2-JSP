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
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Name</th>
                    <th scope="col">Task name</th>
                    <th scope="col">Create date</th>
                    <th scope="col">Is read</th>
                    <th scope="col">Read date</th>
                    <th scope="col">User assigned</th>
                    <th scope="col">Link</th>
                    <th scope="col">Mark as read</th>
                    <th scope="col">Remove</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${empty notifications}">
                    <span>No results</span>
                </c:if>
                <c:if test="${not empty notifications}">
                    <c:forEach var="notification" items="${notifications}">
                        <tr>
                            <th scope="row">${notification.id}</th>
                            <td>${notification.name}</td>
                            <td>${notification.taskName}</td>
                            <td>
                                <c:if test="${not empty notification.createDate}">
                                    ${notification.createDate}
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${notification.read}">
                                    Yes
                                </c:if>
                                <c:if test="${not notification.read}">
                                    No
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${not empty notification.readDate}">
                                    ${notification.readDate}
                                </c:if>
                            </td>
                            <td>${task.userNameAssigned}</td>
                            <td><a href="details?taskId=${notification.taskId}">Go to task</a></td>
                            <td>Mark as read</td>
                            <td>Remove</td>
                        </tr>
                    </c:forEach>
                </c:if>
            </tbody>
        </table>
    </body>

</html>