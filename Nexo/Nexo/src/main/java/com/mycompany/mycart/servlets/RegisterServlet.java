package com.learn.mycart.servlets;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.io.PrintWriter;
import com.mycompany.mycart.entities.User;
import com.mycompany.nexo.helper.FactoryProvider;


@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String userName = request.getParameter("userName");
        String userEmail = request.getParameter("userEmail");
        String userPassword = request.getParameter("userPassword");
        String userPhone = request.getParameter("userPhone");
        String userAddress = request.getParameter("userAddress");

        User user = new User(userName, userEmail, userPassword, userPhone, "default.jpg", userAddress, "normal");

        Session hibernateSession = FactoryProvider.getFactory().openSession();
        Transaction tx = hibernateSession.beginTransaction();
        int userId = 0; // Initialize userId outside the try block

        try {
            userId = (int) hibernateSession.save(user);
            tx.commit();
            out.println("Registration Successfully!!");
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            out.println("Failed to save user. Please try again.");
        } finally {
            hibernateSession.close();
        }

        // Set message attribute in session
        HttpSession httpsession = request.getSession();
        httpsession.setAttribute("message", "Registration Successfully!!");
        
        // Redirect to user_register.jsp
        response.sendRedirect("user_register.jsp");
    }
}
