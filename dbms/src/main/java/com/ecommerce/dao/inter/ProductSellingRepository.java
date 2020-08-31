package com.ecommerce.dao.inter;

import com.ecommerce.entity.ProductSelling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ProductSellingRepository extends JpaRepository<ProductSelling,Integer> {

    @Query(value = "SELECT ps.*, COUNT(p.product_id) AS count FROM product_sell AS ps INNER JOIN product AS p ON ps.product_id = p.product_id GROUP BY product_id ORDER BY count DESC", nativeQuery = true)
    ArrayList<ProductSelling> topSellingProducts();
}
