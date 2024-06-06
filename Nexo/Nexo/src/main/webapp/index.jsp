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
    </head>
    <body>
        <h1>Hello World!</h1>
        
        <%-- Retrieving the SessionFactory from FactoryProvider --%>
        <% SessionFactory sessionFactory = FactoryProvider.getFactory(); %>
        
        <%-- Checking if the SessionFactory is not null --%>
        <% if (sessionFactory != null) { %>
            <p>SessionFactory retrieved successfully!</p>
        <% } else { %>
            <p>Error: Unable to retrieve SessionFactory!</p>
        <% } %>
        
    </body>
</html>
