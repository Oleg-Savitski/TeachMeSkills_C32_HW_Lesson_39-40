<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit User</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500&display=swap" rel="stylesheet">
    <style>
        body { background: #f0f2f5; font-family: 'Roboto', sans-serif; }
        .edit-container { max-width: 800px; margin: 2rem auto; padding: 2rem;
            background: white; border-radius: 12px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }
        .form-header { text-align: center; margin-bottom: 2rem; color: #2c3e50;
            border-bottom: 2px solid #3498db; padding-bottom: 1rem; }
        .is-invalid { border-color: #dc3545 !important; }
        .invalid-feedback { display: block; color: #dc3545; font-size: 0.875em; }
    </style>
</head>
<body>
<div class="edit-container">
    <h2 class="form-header">Edit User Profile</h2>

    <c:if test="${not empty success}">
        <div class="alert alert-success mb-4">${success}</div>
    </c:if>

    <form:form method="post" action="/register/users/update" modelAttribute="userEditDto">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <form:hidden path="id" />

    <div class="form-row">
        <div class="form-group col-md-6">
            <label>First Name:</label>
            <form:input path="firstname"
                        class="form-control ${not empty firstnameError ? 'is-invalid' : ''}"
                        placeholder="Enter first name"/>
            <form:errors path="firstname" cssClass="invalid-feedback"/>
        </div>

        <div class="form-group col-md-6">
            <label>Last Name:</label>
            <form:input path="secondName"
                        class="form-control ${not empty secondNameError ? 'is-invalid' : ''}"
                        placeholder="Enter last name"/>
            <form:errors path="secondName" cssClass="invalid-feedback"/>
        </div>
    </div>

    <div class="form-row">
        <div class="form-group col-md-4">
            <label>Age:</label>
            <form:input path="age" type="number"
                        class="form-control ${not empty ageError ? 'is-invalid' : ''}"
                        placeholder="Age"/>
            <form:errors path="age" cssClass="invalid-feedback"/>
        </div>

        <div class="form-group col-md-4">
            <label>Gender:</label>
            <form:select path="sex"
                         class="form-control ${not empty sexError ? 'is-invalid' : ''}">
                <form:option value="" disabled="true">Select gender</form:option>
                <form:option value="male">Male</form:option>
                <form:option value="female">Female</form:option>
            </form:select>
            <form:errors path="sex" cssClass="invalid-feedback"/>
        </div>

        <div class="form-group col-md-4">
            <label>Phone:</label>
            <form:input path="telephoneNumber"
                        class="form-control ${not empty telephoneNumberError ? 'is-invalid' : ''}"
                        placeholder=""/>
            <form:errors path="telephoneNumber" cssClass="invalid-feedback"/>
        </div>
    </div>

    <div class="form-group">
        <label>Email:</label>
        <form:input path="email" type="email"
                    class="form-control ${not empty emailError ? 'is-invalid' : ''}"
                    placeholder="example@domain.com"/>
        <form:errors path="email" cssClass="invalid-feedback"/>
    </div>

    <button type="submit" class="btn btn-primary mr-2">
        <i class="fas fa-save"></i> Save Changes
    </button>
    <a href="/register/users" class="btn btn-secondary">
        <i class="fas fa-times"></i> Cancel
    </a>
</div>
</form:form>
</div>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>