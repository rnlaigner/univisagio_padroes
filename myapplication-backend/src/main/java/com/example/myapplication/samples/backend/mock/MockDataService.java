package com.example.myapplication.samples.backend.mock;

import java.util.List;

import com.example.myapplication.samples.backend.DataService;
import com.example.myapplication.samples.backend.data.Category;
import com.example.myapplication.samples.backend.data.Product;
import com.example.myapplication.samples.backend.data.User;

/**
 * Mock data model. This implementation has very simplistic locking and does not
 * notify users of modifications.
 */
public class MockDataService extends DataService {

    private static MockDataService INSTANCE;

    private List<User> users;
    private int nextUserId = 0;
    
    private List<Product> products;
    private List<Category> categories;
    private int nextProductId = 0;

    private MockDataService() {
    	users = MockDataGenerator.createUsers();
        categories = MockDataGenerator.createCategories();
        products = MockDataGenerator.createProducts(categories);
        nextProductId = products.size() + 1;
    }

    public synchronized static DataService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MockDataService();
        }
        return INSTANCE;
    }

    @Override
    public synchronized List<Product> getAllProducts() {
        return products;
    }

    @Override
    public synchronized List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public synchronized void updateProduct(Product p) {
        if (p.getId() < 0) {
            // New product
            p.setId(nextProductId++);
            products.add(p);
            return;
        }
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == p.getId()) {
                products.set(i, p);
                return;
            }
        }

        throw new IllegalArgumentException("No product with id " + p.getId()
                + " found");
    }

    @Override
    public synchronized Product getProductById(int productId) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == productId) {
                return products.get(i);
            }
        }
        return null;
    }

    @Override
    public synchronized void deleteProduct(int productId) {
        Product p = getProductById(productId);
        if (p == null) {
            throw new IllegalArgumentException("Product with id " + productId
                    + " not found");
        }
        products.remove(p);
    }
    
    public boolean userExists(String username){
    	for(User user: users){
    		if (user.getName().equals(username)){
    			return true;
    		}
    	}
    	return false;
    }
    
    public void addUser(String username, String password){
    	 User newUser = new User();
    	 newUser.setId(nextUserId++);
    	 newUser.setName(username);
    	 newUser.setPassword(password);
         users.add(newUser);
         return;
    }
    
}
