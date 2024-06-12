package com.mycompany.mycart.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.mycompany.mycart.entities.Category;
import com.mycompany.nexo.dao.CategoryDao;
import com.mycompany.nexo.helper.FactoryProvider;

@WebServlet("/ProductOparetionServlet")
public class ProductOparetionServlet extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            String op = request.getParameter("operation");
            if (op != null && op.trim().equals("addcategory")) {
                String catTitle = request.getParameter("catTitle");
                String catDisc = request.getParameter("catDisc");

                Category category = new Category();
                category.setCategoryTitle(catTitle);
                category.setCategoryDescription(catDisc);

                CategoryDao categoryDao = new CategoryDao(FactoryProvider.getFactory());
                int catId = categoryDao.saveCategory(category);

               out.println("Category Saved!");
                response.sendRedirect("admin.jsp");
            } else if (op != null && op.trim().equals("addproduct")) {
                // Your code for adding a product
            } else {
                // Handle invalid operation
                out.println("Invalid operation");
            }
        } 
    } 

    // Override doGet() method to handle GET requests
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
}
