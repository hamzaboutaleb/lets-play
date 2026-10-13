package play.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import play.dto.product.ProductResponse;
import play.dto.product.CreateProductRequest;
import play.dto.product.UpdateProductRequest;

@Service
@RequiredArgsConstructor
public class ProductService {

    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return Page.empty(pageable);
    }

    public ProductResponse getProductById(String id) {
        return null;
    }

    public ProductResponse createProduct(CreateProductRequest request, String userId) {
        return null;
    }

    public ProductResponse updateProduct(String id, UpdateProductRequest request) {
        return null;
    }

    public void deleteProduct(String id) {
    }
}