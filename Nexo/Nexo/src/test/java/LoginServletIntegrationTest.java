package com.learn.mycart.servlets;

import com.mycompany.mycart.entities.User;
import com.mycompany.nexo.helper.FactoryProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class LoginServletIntegrationTest {

    private LoginServlet loginServlet;
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void setUp() throws Exception {
       
        loginServlet = new LoginServlet();

       
        sessionFactory = mock(SessionFactory.class);
        session = mock(Session.class);
        transaction = mock(Transaction.class);

        
        mockStatic(FactoryProvider.class);
        when(FactoryProvider.getFactory()).thenReturn(sessionFactory);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
    }

    @Test
    public void testLogin_Successful() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);

        when(request.getParameter("userEmail")).thenReturn("testuser@example.com");
        when(request.getParameter("userPassword")).thenReturn("password");
        when(request.getSession()).thenReturn(httpSession);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        
        User user = new User();
        user.setEmail("testuser@example.com");
        user.setPassword("password"); 
        when(session.createQuery("from User where email=:email and password=:password", User.class)
                .setParameter("email", "testuser@example.com")
                .setParameter("password", "password")
                .uniqueResult()).thenReturn(user);

        loginServlet.doPost(request, response);

        verify(httpSession).setAttribute(eq("user"), eq(user));
        verify(response).sendRedirect("welcome.jsp");

        String result = stringWriter.toString();
        assertTrue(result.contains("Redirecting to welcome page..."));
    }

    @Test
    public void testLogin_InvalidCredentials() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);

        when(request.getParameter("userEmail")).thenReturn("testuser@example.com");
        when(request.getParameter("userPassword")).thenReturn("wrongpassword");
        when(request.getSession()).thenReturn(httpSession);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        
        when(session.createQuery("from User where email=:email and password=:password", User.class)
                .setParameter("email", "testuser@example.com")
                .setParameter("password", "wrongpassword")
                .uniqueResult()).thenReturn(null);

        loginServlet.doPost(request, response);

        verify(httpSession).setAttribute(eq("message"), eq("Invalid email or password."));
        verify(response).sendRedirect("index.jsp");

        String result = stringWriter.toString();
        assertTrue(result.contains("Invalid email or password."));
    }

    @Test
    public void testLogin_MissingEmail() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);

        when(request.getParameter("userEmail")).thenReturn("");
        when(request.getParameter("userPassword")).thenReturn("password");
        when(request.getSession()).thenReturn(httpSession);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        loginServlet.doPost(request, response);

        verify(httpSession).setAttribute(eq("message"), eq("Email is required."));
        verify(response).sendRedirect("index.jsp");

        String result = stringWriter.toString();
        assertTrue(result.contains("Email is required."));
    }

    @Test
    public void testLogin_MissingPassword() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);

        when(request.getParameter("userEmail")).thenReturn("testuser@example.com");
        when(request.getParameter("userPassword")).thenReturn("");
        when(request.getSession()).thenReturn(httpSession);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        loginServlet.doPost(request, response);

        verify(httpSession).setAttribute(eq("message"), eq("Password is required."));
        verify(response).sendRedirect("index.jsp");

        String result = stringWriter.toString();
        assertTrue(result.contains("Password is required."));
    }

    @After
    public void tearDown() {
       
        Mockito.framework().clearInvocations();
    }
}
