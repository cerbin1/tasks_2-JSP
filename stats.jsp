<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

        <head>
                <title>Statistics</title>
                <link rel="stylesheet" href="bootstrap.min.css">
                <script src="bootstrap.bundle.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                <script
                        src="https://cdn.jsdelivr.net/npm/chartjs-adapter-date-fns/dist/chartjs-adapter-date-fns.bundle.min.js"></script>
        </head>

        <script>
        window.onload = (event) => {
                const contextForChart1 = document.getElementById('numberOfTasksChart');
                if (window.numberOfTasksChart && typeof window.numberOfTasksChart.destroy === 'function') {
                        window.numberOfTasksChart.destroy();
                }
                window.numberOfTasksChart = new Chart(contextForChart1, {
                        type: 'bar',
                        data: {
                                labels: ${dateValues},
                                datasets: [{
                                        label: '# of tasks',
                                        data: ${taskCounts},
                                        borderWidth: 1
                                }]
                        },
                        options: {
                                scales: {
                                        x: { type: 'time', time: { tooltipFormat: 'yyyy-MM-dd', unit: 'day', }, title: { display: true, text: 'Date' } },
                                        y: { beginAtZero: true, title: { display: true, text: 'Count' } }
                                }
                        }
                });
                const contextForChart2 = document.getElementById('minutesLoggedByUsersChart');
                if (window.minutesLoggedByUsersChart && typeof window.minutesLoggedByUsersChart.destroy === 'function') {
                        window.minutesLoggedByUsersChart.destroy();
                }
                window.minutesLoggedByUsersChart = new Chart(contextForChart2, {
                        type: 'bar',
                        data: {
                                labels: ${usernames},
                                datasets: [{
                                        label: '# of minutes',
                                        data: ${timeLoggedCounts},
                                        borderWidth: 1
                                }]
                        },
                        options: {
                                scales: {
                                        x: { title: { display: true, text: 'Username' } },
                                        y: { beginAtZero: true, title: { display: true, text: 'Minutes logged' } }
                                }
                        }
                });
        }
</script>
<body>
        <jsp:include page="navbar.jsp" />
        <h2>General statistics</h2>
        <div className="card">
                <div className="card-body">
                        Number of users:
                        <span className="fw-bold px-1"> ${statistics.numberOfUsers}</span>
                </div>
        </div>
        <div className="card">
                <div className="card-body">
                        Number of tasks created:
                        <span className="fw-bold px-1">${statistics.numberOfCreatedTasks}</span>
                </div>
        </div>
        <div className="card">
                <div className="card-body">
                        Number of tasks completed:
                        <span className="fw-bold px-1">${statistics.numberOfCompletedTasks}</span>
                </div>
        </div>
        <div className="card">
                <div className="card-body">
                        Number of subtasks:
                        <span className="fw-bold px-1">${statistics.numberOfSubtasks}</span>
                </div>
        </div>
        <div className="card">
                <div className="card-body">
                        Number of notifications:
                        <span className="fw-bold px-1">${statistics.numberOfNotifications}</span>
                </div>
        </div>
        <h2>Number of tasks per day by deadline</h2>
        <canvas id="numberOfTasksChart"></canvas>
        <h2>Number of minutes logged by user</h2>
        <canvas id="minutesLoggedByUsersChart"></canvas>
</body>

</html>