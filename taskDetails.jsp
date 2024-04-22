<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

    <head>
        <title>Task details</title>
        <link rel="stylesheet" href="bootstrap.min.css">
    </head>

    <body>
        <jsp:include page="navbar.jsp" />
        <div class='container'>
            <div class="row">
                <label class="col-sm-4 col-form-label fw-bold">Name</label>
                <p class="col-sm-8">${task.name}</p>
            </div>
            <div class="row">
                <label class="col-sm-4 col-form-label fw-bold">Deadline</label>
                <p class="col-sm-8">${task.deadline}</p>
            </div>

            <div class="row">
                <label class="col-sm-4 col-form-label fw-bold">Assignee</label>
                <p class="col-sm-8">${task.assignee}</p>
            </div>

            <div class="row">
                <label class="col-sm-4 col-form-label fw-bold">Priority</label>
                <p class="col-sm-8">${task.priority}</p>
            </div>
            <h1>Labels</h1>
            <c:if test="${empty labels}">
                <span>No labels added.</span>
            </c:if>
            <c:if test="${not empty labels}">
                <c:forEach var="label" items="${labels}">
                    <div class="d-flex align-items-center justify-content-center">
                        <div class="input-group mb-1">
                            <span class="form-control">${label.name}</span>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <h1>Category</h1>
            <div class="row">
                <label class="col-sm-4 col-form-label fw-bold">Category</label>
                <p class="col-sm-8">${task.category}</p>
            </div>
            <h1>Subtasks</h1>
            <c:if test="${empty subtasks}">
                <span>No subtasks yet.</span>
            </c:if>
            <c:if test="${not empty subtasks}">
                <c:forEach var="subtask" items="${subtasks}">
                    <div class="d-flex align-items-center justify-content-center">
                        <div class="col-md-3">
                            <div class="input-group">
                                <div id="${subtask.sequence}" class="col-md-9" style="text-align: center">
                                    ${subtask.name}
                                </div>
                                <button type="button" class="btn btn-success col-md-3"
                                    onclick="document.getElementById('${subtask.getSequence()}').style.textDecoration = 'line-through'">Done</button>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <h1>Chat</h1>
            <c:if test="${empty chatMessages}">
                <span>No messages yet.</span>
            </c:if>
            <c:if test="${not empty chatMessages}">
                <c:forEach var="message" items="${chatMessages}">
                    <div class="d-flex ${message.senderId eq sessionScope.userId? 'flex-row' : 'flex-row-reverse'}">
                        <div>
                            <b>${message.senderName}:</b>
                            <p>${message.content}</p>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <form action="/tasks_2-JSP/createChatMessage?taskId=${task.id}" method="post">
                <input type="text" class="form-control" name="messageContent" placeholder='Message content' />
                <button type="submit" class="btn btn-primary">Send Message</button>
            </form>
            <h1>Files</h1>

            <c:if test="${empty files}">
                <span>No files.</span>
            </c:if>
            <c:if test="${not empty files}">
                <c:forEach var="file" items="${files}">
                    <div>
                        <a href="/tasks_2-JSP/download?filename=${file.name}&filetype=${file.type}">
                            ${file.name}
                        </a>
                    </div>
                </c:forEach>
            </c:if>
            <h1>Worklogs</h1>
            <form action="/tasks_2-JSP/markAsCompleted?taskId=${task.id}" method="post" style="display: inline">
                <button type="submit" class="btn btn-success">Mark as completed</button>
            </form>
        </div>

</html>