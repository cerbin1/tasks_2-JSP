<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

    <head>
        <title>Login</title>
        <link rel="stylesheet" href="bootstrap.min.css">
        <link rel="stylesheet" href="style.css">
    </head>

    <body>
        <nav class="navbar navbar-expand-md navbar-light bg-light">
            <a class="navbar-brand" href="#">Navbar</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item active">
                        <a class="nav-link" href="">Main Page</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/tasks_2-JSP/tasks">List tasks</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/tasks_2-JSP/createTask">Create task</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/tasks_2-JSP/myTasks">My Tasks</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/tasks_2-JSP/notifications">Notifications</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/tasks_2-JSP/adminPanel">User List</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/tasks_2-JSP/stats">Stats</a>
                    </li>
                    <li class="nav-item">
                        <a class=" btn btn-warning" href="/tasks_2-JSP/logout">Logout</a>
                    </li>
                </ul>
            </div>
        </nav>

    </body>

</html>