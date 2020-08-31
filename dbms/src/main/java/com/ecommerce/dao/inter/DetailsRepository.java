package com.ecommerce.dao.inter;

import com.ecommerce.entity.Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailsRepository extends JpaRepository<Details, String> {

    @Query(value = "SELECT * FROM details WHERE name = 'technocamp'", nativeQuery = true)
    Details getDetails();
}
