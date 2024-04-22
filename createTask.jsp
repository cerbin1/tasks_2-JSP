<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

    <head>
        <title>Create task</title>
        <link rel="stylesheet" href="bootstrap.min.css">
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                var addSubtaskButton = document.getElementById('addSubtask');
                addSubtaskButton.addEventListener('click', function () {
                    var input = document.createElement('input');
                    input.type = 'text';
                    input.classList.add('form-control');
                    input.style.textAlign = 'center';
                    input.name = 'subtasks[]';
                    input.placeholder = 'Subtask name';
                    var subtasksContainer = document.getElementById('subtasks');
                    subtasksContainer.appendChild(input);
                });
            });
        </script>
    </head>

    <body>
        <jsp:include page="navbar.jsp" />
        <div class="container">
            <form action="/tasks_2-JSP/createTask" method="post" enctype="multipart/form-data">
                <div class="form-group row">
                    <label for="name" class="col-sm-2 col-form-label">Name</label>
                    <div class="col-sm-10">
                        <input required type="text" class="form-control" id="name" name="name" />
                    </div>
                </div>
                <div class="form-group row">
                    <label for="deadline" class="col-sm-2 col-form-label">Deadline</label>
                    <div class="col-sm-10">
                        <input required class="form-control" id="deadline" name="deadline" type="datetime-local" />
                    </div>
                </div>

                <div class="form-group row">
                    <label for="user" class="col-sm-2 col-form-label">Assignee</label>
                    <div class="col-sm-10">
                        <select class="form-select" id="user" name="user">
                            <c:forEach var="user" items="${users}">
                                <option value="${user.id}">${user.name} ${user.surname}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-group row">
                    <label for="priority" class="col-sm-2 col-form-label">Priority</label>
                    <div class="col-sm-10">
                        <select class="form-select" id="priority" name="priority">
                            <c:forEach var="priority" items="${priorities}">
                                <option value="${priority.id}">${priority.value}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <h1>Labels</h1>
                <h1>Category</h1>
                <div class="d-flex align-items-center justify-content-center">
                    <div class="form-group col-md-3">
                        <select class="form-select" name="category">
                            <c:forEach var="category" items="${categories}">
                                <option value="${category}">${category}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <h1>Subtasks</h1>
                <div class="d-flex align-items-center justify-content-center">
                    <div id="subtasks" class="form-group col-md-3">
                    </div>
                </div>
                <button id="addSubtask" type="button" class="btn btn-success">Add subtask</button>
                <h1>Files upload</h1>
                <input type="file" multiple name="files" />
                <div class="form-group row">
                    <div class="form-control">
                        <button type="submit" class="btn btn-primary">Create Task</button>
                    </div>
                </div>
            </form>
        </div>
    </body>

</html>