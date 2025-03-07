<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome to Our Project Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f0f8ff;
            color: #333;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: Arial, sans-serif;
        }
        .welcome-container {
            background-color: rgba(255, 255, 255, 0.9);
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        h1 {
            color: #007bff;
            font-size: 2.5rem;
            margin-bottom: 20px;
        }
        .lead {
            font-size: 1.5rem;
            margin-bottom: 20px;
        }
        ul {
            list-style-type: none;
            padding: 0;
        }
        li {
            font-size: 1.5rem;
            margin: 10px 0;
        }
        .btn-primary {
            background-color: #007bff;
            border: none;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
        .btn-secondary {
            background-color: #6c757d;
            border: none;
            margin-left: 10px;
        }
        .btn-secondary:hover {
            background-color: #5a6268;
        }
        .button-group {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="welcome-container">
        <h1>Welcome to Our Project Page!</h1>
        <p class="lead">We are glad to see you here. This project is created to demonstrate the capabilities of Spring and JSP.</p>
        <hr class="my-4">
        <p>Here you will find:</p>
        <ul>
            <li>Interesting facts about our project</li>
            <li>Interactive features and functionalities</li>
            <li>Support and documentation</li>
        </ul>
        <p class="lead">We hope you enjoy your stay!</p>

        <div class="button-group">
            <a class="btn btn-primary btn-lg" href="/auth/login" role="button">Login for Registered Users</a>
            <a class="btn btn-secondary btn-lg" href="/security/registration" role="button">Register for New Users</a>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>