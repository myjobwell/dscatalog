package com.devwell.dscatalog.repositories;

import com.devwell.dscatalog.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //consigo restringir os dados que serao enviados para o cliente e ainda filtrar e proteger os dados sensiveis

}
