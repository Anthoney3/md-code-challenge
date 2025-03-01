# Additions Made to Project

## Swagger OpenAPI 3.0
<div style="font-size: 14pt">
<p>
    I've added spring doc swagger to this project to make endpoint visualization and documentation much easier.<br>
    If you wish to view the application's swagger page when running the service, please visit <a href="http://localhost:8080/swagger-ui.html">http://localhost:8080/swagger-ui.html</a> while the application is running to view the swagger page. The page will show you all available endpoints including any auto generated documentation based on the spring project.
</p>
</div>

## Lombok Annotation Processing

<div style="font-size: 14pt">
<p>
    In addition to adding swagger to the project, I also went ahead and added lombok which is an annotation processing library that makes model creation and bean injection methods easier / cleaner.<br>
    The library allows for you to put annotations such as @Getter and @Setter in your model classes to prevent the need to create large classes full of boilerplate code. Lastly, lombok allows you to simply put @Slf4j at the top of any spring class to establish a logger without the need to put in a static logger; this is because lombok will generate that code for you.
</p>
</div>