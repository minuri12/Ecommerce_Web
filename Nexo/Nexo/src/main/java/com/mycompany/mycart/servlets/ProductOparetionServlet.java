package com.mycompany.mycart.servlets;

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.mycompany.mycart.entities.Category;
import com.mycompany.mycart.entities.Product;
import com.mycompany.nexo.dao.CategoryDao;
import com.mycompany.nexo.dao.ProductDao;
import com.mycompany.nexo.helper.FactoryProvider;
import javax.servlet.http.Part;

@MultipartConfig
@WebServlet("/ProductOparetionServlet")
public class ProductOparetionServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            String op = request.getParameter("operation");

            if (op != null && op.trim().equals("addcategory")) {
                String catTitle = request.getParameter("catTitle");
                String catDesc = request.getParameter("catDesc");

                Category category = new Category();
                category.setCategoryTitle(catTitle);
                category.setCategoryDescription(catDesc);

                CategoryDao categoryDao = new CategoryDao(FactoryProvider.getFactory());
                int catId = categoryDao.saveCategory(category);

                out.println("Category Saved!");
                HttpSession session = request.getSession();
                session.setAttribute("message", "Category added successfully");
                response.sendRedirect("admin.jsp");
                return;

            } else if (op != null && op.trim().equals("addproduct")) {
                String pName = request.getParameter("pName");
                String pDesc = request.getParameter("pDesc");
                Part part = request.getPart("pPhoto");
                double pPrice = Double.parseDouble(request.getParameter("pPrice"));
                int pDiscount = Integer.parseInt(request.getParameter("pDiscount"));
                int categoryId = Integer.parseInt(request.getParameter("categoryId"));
                int pQuantity = Integer.parseInt(request.getParameter("pQuantity"));

                Product p = new Product();
                p.setPName(pName);
                p.setPDesc(pDesc);
                p.setPPrice(pPrice);
                p.setPDiscount(pDiscount);
                p.setPQuantity(pQuantity);

                CategoryDao cdao = new CategoryDao(FactoryProvider.getFactory());
                Category category = cdao.getCategoryById(categoryId);
                p.setCategory(category);

                // Save the file to the server
                String path = request.getServletContext().getRealPath("") + File.separator + "img" + File.separator + "products";
                String fileName = part.getSubmittedFileName();

                // Ensure the directory exists
                File directory = new File(path);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Save the file
                try (InputStream is = part.getInputStream();
                     FileOutputStream fos = new FileOutputStream(path + File.separator + fileName)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                    System.out.println("File saved successfully: " + fileName);
                    p.setPPhoto(fileName); // Set the file name in the Product entity
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("File saving failed: " + e.getMessage());
                }

                ProductDao pdao = new ProductDao(FactoryProvider.getFactory());
                pdao.saveProduct(p);

                HttpSession session = request.getSession();
                session.setAttribute("message", "Product added successfully");
                response.sendRedirect("admin.jsp");
                return;

            } else if (op != null && op.trim().equals("updateproduct")) {
                int productId = Integer.parseInt(request.getParameter("productId"));
                String pName = request.getParameter("pName");
                String pDesc = request.getParameter("pDesc");
                double pPrice = Double.parseDouble(request.getParameter("pPrice"));
                int pDiscount = Integer.parseInt(request.getParameter("pDiscount"));
                int categoryId = Integer.parseInt(request.getParameter("categoryId"));
                int pQuantity = Integer.parseInt(request.getParameter("pQuantity"));
                Part part = request.getPart("pPhoto");

                ProductDao pdao = new ProductDao(FactoryProvider.getFactory());
                Product product = pdao.getProductById(productId);

                if (product != null) {
                    product.setPName(pName);
                    product.setPDesc(pDesc);
                    product.setPPrice(pPrice);
                    product.setPDiscount(pDiscount);
                    product.setPQuantity(pQuantity);

                    if (part != null && part.getSize() > 0) {
                        // Save the file to the server
                        String path = request.getServletContext().getRealPath("") + File.separator + "img" + File.separator + "products";
                        String fileName = part.getSubmittedFileName();

                        // Ensure the directory exists
                        File directory = new File(path);
                        if (!directory.exists()) {
                            directory.mkdirs();
                        }

                        // Save the file
                        try (InputStream is = part.getInputStream();
                             FileOutputStream fos = new FileOutputStream(path + File.separator + fileName)) {
                            byte[] buffer = new byte[1024];
                            int bytesRead;
                            while ((bytesRead = is.read(buffer)) != -1) {
                                fos.write(buffer, 0, bytesRead);
                            }
                            System.out.println("File saved successfully: " + fileName);
                            product.setPPhoto(fileName); // Set the file name in the Product entity
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("File saving failed: " + e.getMessage());
                        }
                    }

                    CategoryDao cdao = new CategoryDao(FactoryProvider.getFactory());
                    Category category = cdao.getCategoryById(categoryId);
                    product.setCategory(category);

                    pdao.updateProduct(product);

                    HttpSession session = request.getSession();
                    session.setAttribute("message", "Product updated successfully");
                    response.sendRedirect("admin.jsp");
                } else {
                    out.println("Product not found!");
                }
                return;

            } else if (op != null && op.trim().equals("updatecategory")) {

                int catId = Integer.parseInt(request.getParameter("categoryId"));
                String catTitle = request.getParameter("catTitle");
                String catDesc = request.getParameter("catDesc");
                CategoryDao categoryDao = new CategoryDao(FactoryProvider.getFactory());
                Category category = categoryDao.getCategoryById(catId);

                if (category != null) {
                    
                    category.setCategoryTitle(catTitle);
                    category.setCategoryDescription(catDesc);

                    categoryDao.updateCategory(category);

                    HttpSession session = request.getSession();
                    session.setAttribute("message", "Category updated successfully");
                    response.sendRedirect("admin.jsp");
                } else {
                    System.out.println("Category not found with ID: " + catId);
                    HttpSession session = request.getSession();
                    session.setAttribute("message", "Category not found!");
                    response.sendRedirect("admin.jsp");
                }
                return;
            } else {
                out.println("Invalid operation");
            }
        } catch (Exception e) {
            e.printStackTrace();
            HttpSession session = request.getSession();
            session.setAttribute("message", "An error occurred: " + e.getMessage());
            response.sendRedirect("admin.jsp");
        }


    }

    // Process POST requests
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    // Process GET requests
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
//