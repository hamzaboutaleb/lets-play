package play.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import play.dto.product.ProductResponse;
import play.dto.product.CreateProductRequest;
import play.dto.product.UpdateProductRequest;
import play.exception.ProductNotFoundException;
import play.model.Product;
import play.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
            .map(this::toResponse);
    }

    public ProductResponse getProductById(String id) {
        return productRepository.findById(id)
            .map(this::toResponse)
            .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public ProductResponse createProduct(CreateProductRequest request, String userId) {
        return null;
    }

    public ProductResponse updateProduct(String id, UpdateProductRequest request) {
        return null;
    }

    public void deleteProduct(String id) {
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getUserId(),
            product.getCreatedAt(),
            product.getUpdatedAt()
        );
    }
}