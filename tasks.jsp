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

        <c:if test="${empty tasks}">
            <span>No results</span>
        </c:if>
        <c:if test="${not empty tasks}">
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
                            <td>
                                <form action="/tasks_2-JSP/removeTask?taskId=${task.id}" method="post">
                                    <button type="submit" class="btn btn-danger">Remove</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <h1>Filters</h1>
        <div class="form-group row">
            <form action="/tasks_2-JSP/searchByName" method="get">
                <input type="text" class="form-control" name="name" />
                <button type="submit" class="btn btn-primary">Search by name</button>
            </form>
        </div>
        <div class="form-group row">
            <form action="/tasks_2-JSP/searchByCategory" method="get">
                <select class="form-select" name="category">
                    <c:forEach var="category" items="${categories}">
                        <option value="${category}">${category}</option>
                    </c:forEach>
                </select>
                <button type="submit" class="btn btn-primary">Search by category</button>
            </form>
        </div>

    </body>

</html>