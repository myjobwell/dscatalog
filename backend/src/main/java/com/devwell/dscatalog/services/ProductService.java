package com.devwell.dscatalog.services;


import com.devwell.dscatalog.dto.ProductDTO;

import com.devwell.dscatalog.entities.Product;

import com.devwell.dscatalog.repositories.ProductRepository;
import com.devwell.dscatalog.services.exceptions.DatabaseException;
import com.devwell.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Transactional(readOnly = true)
//    tag acima inicia uma transação e nao trava o banco de dados durante a consulta
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
        Page<Product> list = productRepository.findAll(pageRequest);
        return list.map(ProductDTO::new);
    }

    //**POR ID
    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> obj = productRepository.findById(id);
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
        return new ProductDTO(entity, entity.getCategories());
    }

    //**INSERT
    @Transactional
    public ProductDTO create(ProductDTO dto) {
        Product entity = new Product();
//        entity.setName(dto.getName());
        entity = productRepository.save(entity);
        return new ProductDTO(entity);
    }


    //**UPDATE
    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
//        getOne nao precisa consultar e depois atualizar no campo, deixa mais enxuto o put
//        instancia temporariamente o dado para quando for chamado o save
        try {
            Product entity = productRepository.getOne(id);
//        entity.setName(dto.getName());
        entity = productRepository.save(entity);
        return new ProductDTO(entity);
    } catch (EntityNotFoundException e) {
        throw new ResourceNotFoundException("Id Not Found" + id);}
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id Not Found " + id);
        }
        try {
            productRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade e referencial");
        }



    }



}
