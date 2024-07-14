

package com.learn.mycart.servlets;

import static org.mockito.Mockito.*;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mycompany.mycart.entities.User;
import com.mycompany.nexo.helper.FactoryProvider;
import com.mycompany.mycart.servlets.RegisterServlet; 

public class RegisterServletTest {

    @InjectMocks
        private RegisterServlet registerServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private PrintWriter printWriter;

    @Mock
    private Session hibernateSession;

    @Mock
    private Transaction transaction;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(request.getParameter("userName")).thenReturn("TestUser");
        when(request.getParameter("userEmail")).thenReturn("test@example.com");
        when(request.getParameter("userPassword")).thenReturn("password");
        when(request.getParameter("userPhone")).thenReturn("1234567890");
        when(request.getParameter("userAddress")).thenReturn("Test Address");

        StringWriter stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
        when(request.getSession()).thenReturn(session);

        when(FactoryProvider.getFactory().openSession()).thenReturn(hibernateSession);
        when(hibernateSession.beginTransaction()).thenReturn(transaction);
    }

    @Test
    public void testUserRegistrationWithValidInput() throws Exception {
        doNothing().when(transaction).commit();
        when(hibernateSession.save(any(User.class))).thenReturn(1);

        registerServlet.doPost(request, response);

        verify(hibernateSession).save(any(User.class));
        verify(transaction).commit();
        verify(hibernateSession).close();
        verify(session).setAttribute(eq("message"), eq("Registration Successfully!!"));
        verify(response).sendRedirect(eq("user_register.jsp"));

        printWriter.flush();
        String responseOutput = printWriter.toString();
        assert(responseOutput.contains("Registration Successfully!!"));
    }
}
