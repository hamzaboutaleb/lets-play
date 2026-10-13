package play.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import play.dto.product.ProductResponse;
import play.dto.product.CreateProductRequest;
import play.dto.product.UpdateProductRequest;
import play.exception.AccessDeniedException;
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
        Product product = Product.builder()
            .name(request.getName())
            .description(request.getDescription())
            .price(request.getPrice())
            .userId(userId)
            .build();

        Product saved = productRepository.save(product);
        return toResponse(saved);
    }

    public ProductResponse updateProduct(String id, UpdateProductRequest request, String userId, boolean isAdmin) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));

        if (!isAdmin && !product.getUserId().equals(userId)) {
            throw new AccessDeniedException("Not authorized to update this product");
        }

        if (request.getName() != null) {
            product.setName(request.getName());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }

        Product saved = productRepository.save(product);
        return toResponse(saved);
    }

    public void deleteProduct(String id, String userId, boolean isAdmin) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));

        if (!isAdmin && !product.getUserId().equals(userId)) {
            throw new AccessDeniedException("Not authorized to delete this product");
        }

        productRepository.deleteById(id);
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