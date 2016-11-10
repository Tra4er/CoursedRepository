<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head lang="en">
    <link rel="stylesheet" th:href="@{/css/bootstrap.css}"/>
    <meta charset="UTF-8"/>
    <title>Аутентифікація користувача</title>
</head>
<body>
<div class="container">

    <div class="row">
        <div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">

            <form role="form" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <h2>Необхідно авторизуватись</h2>

                <div class="form-group">
                    <input type="email" name="email" id="emailField" class="form-control input-lg" placeholder="Email"/>
                </div>


                <div class="form-group">
                    <input type="password" name="password" id="passwordField" class="form-control input-lg"
                           placeholder="Пароль"/>
                </div>


                <div class="row">
                    <div class="col-xs-6 col-md-6">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="submit" value="Увійти"
                                                          class="btn btn-primary btn-block btn-lg"/></div>
                    <div class="col-xs-6 col-md-6"><a href="/registration" class="btn btn-success btn-block btn-lg">Перейти
                        до
                        реєстрації</a></div>
                </div>
            </form>
        </div>
    </div>
</div>
<#if error.isPresent()>
<p>The email or password you have entered is invalid, try again.</p>
</#if>
</body>
</html>