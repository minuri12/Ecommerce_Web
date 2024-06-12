package com.mycompany.nexo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.mycompany.mycart.entities.Category;
import com.mycompany.nexo.helper.FactoryProvider;

public class CategoryDao {

    private SessionFactory factory;

    // Constructor to initialize the SessionFactory
    public CategoryDao(SessionFactory factory) {
        this.factory = factory;
    }

    // Method to save a Category object to the database
    public int saveCategory(Category cat) {
        Session session = this.factory.openSession(); // Open a session
        Transaction tx = session.beginTransaction(); // Begin a transaction

        int catId = (int) session.save(cat); // Save the category and get the generated ID
        tx.commit(); // Commit the transaction

        session.close(); // Close the session
        return catId; // Return the generated category ID
    }
}
