<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            background-color: #e9f5fb;
        }
        .registration-container {
            max-width: 400px;
            margin: auto;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
        }
        .btn-primary:hover {
            background-color: #0056b3;
            border-color: #0056b3;
        }
        label {
            font-style: italic;
            color: #333;
        }
    </style>
    <script>
        function validateAge() {
            const ageInput = document.getElementById('age');
            const age = parseInt(ageInput.value);
            if (age < 12) {
                alert("Registration is not allowed for users under 12 years old.");
                return false;
            }
            return true;
        }

        function validatePassword() {
            const passwordInput = document.getElementById('password');
            const password = passwordInput.value;
            if (password.length < 8) {
                alert("Password must be at least 8 characters long.");
                return false;
            }
            return true;
        }

        function validateForm() {
            return validateAge() && validatePassword();
        }
    </script>
</head>
<body>
<div class="registration-container mt-5">
    <h2 class="text-center">Registration</h2>
    <form:form action="/security/registration" method="POST" modelAttribute="registrationRequestDto" class="mt-4" onsubmit="return validateForm();">
        <div class="form-group">
            <label for="firstname">First name:</label>
            <form:input path="firstname" id="firstname" class="form-control" required="required"/>
            <form:errors path="firstname" cssClass="text-danger"/>
        </div>

        <div class="form-group">
            <label for="secondName">Second name:</label>
            <form:input path="secondName" id="secondName" class="form-control" required="required"/>
            <form:errors path="secondName" cssClass="text-danger"/>
        </div>

        <div class="form-group">
            <label for="age">Age:</label>
            <form:input path="age" id="age" type="number" class="form-control" required="required"/>
            <form:errors path="age" cssClass="text-danger"/>
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <form:input path="email" id="email" class="form-control" required="required"/>
            <form:errors path="email" cssClass="text-danger"/>
        </div>

        <div class="form-group">
            <label>Sex:</label><br>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" id="male" name="sex" value="male" required>
                <label class="form-check-label" for="male">Male</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" id="female" name="sex" value="female" required>
                <label class="form-check-label" for="female">Female</label>
            </div>
            <form:errors path="sex" cssClass="text-danger"/>
        </div>

        <div class="form-group">
            <label for="telephoneNumber">Telephone number:</label>
            <div class="input-group">
                <span class="input-group-text">+ 375</span>
                <form:select path="operator" class="form-select" required="required">
                    <option value="29">29</option>
                    <option value="44">44</option>
                    <option value="25">25</option>
                    <option value="33">33</option>
                </form:select>
                <form:input path="telephoneNumber" class="form-control" required="required"/>
            </div>
            <form:errors path="telephoneNumber" cssClass="text-danger"/>
        </div>

        <div class="form-group">
            <label for="login">Login:</label>
            <form:input path="login" id="login" class="form-control" required="required"/>
            <form:errors path="login" cssClass="text-danger"/>
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <form:input path="password" id="password" type="password" class="form-control" required="required"/>
            <form:errors path="password" cssClass="text-danger"/>
        </div>

        <div class="form-group text-center">
            <button type="submit" class="btn btn-primary">Register</button>
        </div>
    </form:form>
</div>
</body>
</html>