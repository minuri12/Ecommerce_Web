<%-- 
    Document   : index
    Created on : Jun 4, 2024, 5:00:51 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.mycompany.nexo.helper.FactoryProvider"%>
<%@page import="org.hibernate.SessionFactory"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/style.css">
        <%@include file="components/common_css_js.jsp" %>
    </head>
    <body >
        <%@include file="components/navbar.jsp" %>
        
        <div class="Hero_container">
            <div class="main">NE<span>X</span>O</div>
            <p class="body_text">
                Welcome to Nexo, your one-stop online shop for all things amazing! 
                Discover an extensive range of high-quality products at unbeatable prices. 
                Whether you're looking for the latest tech gadgets, fashionable apparel, or home 
                essentials, Nexo has it all. Shop with confidence, enjoy fast shipping, and experience 
                exceptional customer service. Start your shopping journey with Nexo today – where convenience 
                meets satisfaction!
            </p>
           <a href="user_register.jsp"> <button class="main_button" hr>Register Now</button></a>                                                                                                                                
           
        </div>
      
    </body>
</html>
