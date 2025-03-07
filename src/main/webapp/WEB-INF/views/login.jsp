<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .login-container {
            margin-top: 100px;
        }
        .login-card {
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>
<div class="container login-container">
    <div class="row justify-content-center">
        <div class="col-md-4">
            <div class="card login-card">
                <h4 class="card-title text-center">Login</h4>
                <form action="${pageContext.request.contextPath}/auth/login" method="post" onsubmit="return validateForm()">
                    <div class="form-group">
                        <label for="login">Login</label>
                        <input type="text" class="form-control" id="login" name="login" required value="${login != null ? login : ''}">
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary btn-block">Login</button>
                    </div>
                    <div id="error-message" class="alert alert-danger" style="display: ${error != null ? 'block' : 'none'};">${error}</div>
                </form>
                <div class="text-center">
                    <a href="#">Forgot Password?</a>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
    function validateForm() {
        const errorMessage = document.getElementById('error-message');
        const login = document.getElementById('login').value;
        const password = document.getElementById('password').value;

        errorMessage.style.display = "none";

        if (login === "" || password === "") {
            errorMessage.innerText = "Login and password cannot be empty.";
            errorMessage.style.display = "block";
            return false;
        }

        if (login.length < 3 || login.length > 20) {
            errorMessage.innerText = "Login must be between 3 and 20 characters.";
            errorMessage.style.display = "block";
            return false;
        }

        if (password.length < 6) {
            errorMessage.innerText = "Password must be at least 6 characters long.";
            errorMessage.style.display = "block";
            return false;
        }

        const loginRegex = /^[a-zA-Z0-9]+$/;
        if (!loginRegex.test(login)) {
            errorMessage.innerText = "Login can only contain letters and numbers.";
            errorMessage.style.display = "block";
            return false;
        }

        const passwordRegex = /^(?=.*[a-zA-Z])(?=.*\d).{6,}$/;
        if (!passwordRegex.test(password)) {
            errorMessage.innerText = "Password must contain at least one letter and one number.";
            errorMessage.style.display = "block";
            return false;
        }

        errorMessage.style.display = "none";
        return true;
    }
</script>
</body>
</html>