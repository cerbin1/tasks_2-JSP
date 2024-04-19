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
                    <th scope="col">Deadline</th>
                    <th scope="col">Assignee</th>
                    <th scope="col">Priority</th>
                    <th scope="col">Is Completed</th>
                    <th scope="col">Complete date</th>
                    <th scope="col">Edit</th>
                    <th scope="col">Remove</th>
                </tr>
            </thead>
            <tbody>

                <c:if test="${empty tasks}">
                    taskList.append("<span>No results</span>");
                </c:if>
                <c:if test="${not empty tasks}">
                    <c:forEach var="task" items="${tasks}">
                        <tr>
                            <th scope="row">${task.id}</th>
                            <td>${task.name}</td>
                            <td>${task.deadline.toString()}</td>
                            <td>${task.assignee}</td>
                            <td>${task.priority}</td>
                            <td>
                                <c:if test="${task.completed}">
                                    Yes
                                </c:if>
                                <c:if test="${not task.completed}">
                                    No
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${not empty task.completeDate}">
                                    ${task.completeDate}
                                </c:if>
                            </td>
                            <td><a href="editTask?taskId=${task.id}">Edit</a></td>
                            <td>Remove</td>
                        </tr>
                    </c:forEach>
                </c:if>
            </tbody>
        </table>
        <h1>Filters</h1>
    </body>

</html>