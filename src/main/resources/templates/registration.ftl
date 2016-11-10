<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head lang="en">
    <link rel="stylesheet" th:href="@{/css/bootstrap.css}"/>
    <script th:src="@{/js/scripts.js}" src="/js/scripts.js"></script>
    <meta charset="UTF-8"/>
    <title>Реєстрація користувача</title>
</head>
<body>
<div class="container">

    <div class="row">
        <div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">

            <form role="form" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <h2>Створіть акаунт</h2>

                <div class="form-group">
                    <input type="email" name="email" id="emailField" class="form-control input-lg" placeholder="Email"/>
                </div>

                <div class="row">
                    <div class="col-xs-6 col-sm-6 col-md-6">
                        <div class="form-group">
                            <input type="password" name="password" id="passwordField" class="form-control input-lg"
                                   placeholder="Пароль"/>
                        </div>
                    </div>
                    <div class="col-xs-6 col-sm-6 col-md-6">
                        <div class="form-group">
                            <input type="password" name="confirmPassword" id="confirmPasswordField"
                                   class="form-control input-lg" placeholder="Підтвердіть пароль"/>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-6 col-md-6">
                        <input type="submit" value="Зареєструватись"
                                                          class="btn btn-primary btn-block btn-lg"/></div>
                    <div class="col-xs-6 col-md-6"><a href="/login" class="btn btn-success btn-block btn-lg">Перейти до
                        аутентифікації</a></div>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>