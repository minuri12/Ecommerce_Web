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
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class RegisterServletIntegrationTest {

    private RegisterServlet registerServlet;
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void setUp() throws Exception {
        
        registerServlet = new RegisterServlet();

        
        sessionFactory = mock(SessionFactory.class);
        session = mock(Session.class);
        transaction = mock(Transaction.class);

        
        mockStatic(FactoryProvider.class);
        when(FactoryProvider.getFactory()).thenReturn(sessionFactory);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
    }

    @Test
    public void testDoPost_SuccessfulRegistration() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);

        when(request.getParameter("userName")).thenReturn("TestUser");
        when(request.getParameter("userEmail")).thenReturn("testuser@example.com");
        when(request.getParameter("userPassword")).thenReturn("password");
        when(request.getParameter("userPhone")).thenReturn("1234567890");
        when(request.getParameter("userAddress")).thenReturn("123 Test Street");
        when(request.getSession()).thenReturn(httpSession);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        
        when(session.save(any(User.class))).thenReturn(1);
        doNothing().when(transaction).commit();

        registerServlet.doPost(request, response);

        verify(session).beginTransaction();
        verify(session).save(any(User.class));
        verify(transaction).commit();
        verify(session).close();

        verify(httpSession).setAttribute(eq("message"), eq("Registration Successfully!!"));
        verify(response).sendRedirect("user_register.jsp");

        String result = stringWriter.toString();
        assertTrue(result.contains("Registration Successfully!!"));
    }

    @Test
    public void testDoPost_InvalidEmail() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);

        when(request.getParameter("userName")).thenReturn("TestUser");
        when(request.getParameter("userEmail")).thenReturn("invalidemail");
        when(request.getParameter("userPassword")).thenReturn("password");
        when(request.getParameter("userPhone")).thenReturn("1234567890");
        when(request.getParameter("userAddress")).thenReturn("123 Test Street");
        when(request.getSession()).thenReturn(httpSession);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        registerServlet.doPost(request, response);

        verify(session, never()).beginTransaction();
        verify(session, never()).save(any(User.class));
        verify(transaction, never()).commit();
        verify(session, never()).close();

        verify(httpSession).setAttribute(eq("message"), eq("Invalid email address."));
        verify(response).sendRedirect("user_register.jsp");

        String result = stringWriter.toString();
        assertTrue(result.contains("Invalid email address."));
    }

    

    @After
    public void tearDown() {
        
        Mockito.framework().clearInvocations();
    }
}
