package dev.akmal.commercial.service;

import dev.akmal.commercial.model.Category;
import dev.akmal.commercial.model.Product;
import dev.akmal.commercial.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    public void addProduct(Product product) {
        productRepository.save(product);
    }


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public void removeProductById(Long id) {
        productRepository.deleteById(id);
    }
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    public List<Product> getAllProductsByCategoryId(int id) {
        return productRepository.findAllByCategory_Id(id);


    }
}
