package com.devwell.dscatalog.services;

import com.devwell.dscatalog.dto.CategoryDTO;
import com.devwell.dscatalog.entities.Category;
import com.devwell.dscatalog.repositories.CategoryRepository;
import com.devwell.dscatalog.services.exceptions.DatabaseException;
import com.devwell.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Transactional(readOnly = true)
//    tag acima inicia uma transação e nao trava o banco de dados durante a consulta
    public List<CategoryDTO> findAll() {
        List<Category> list = categoryRepository.findAll();
        return list.stream().map(CategoryDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        //option é para evitar trabalhar com valor nullo, nunca vai ser um objeto nullo
        Optional<Category> obj = categoryRepository.findById(id);
        Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO create(CategoryDTO dto) {
        Category entity = new Category();
        entity.setName(dto.getName());
        entity = categoryRepository.save(entity);
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
//        getOne nao precisa consultar e depois atualizar no campo, deixa mais enxuto o put
//        instancia temporariamente o dado para quando for chamado o save
        try {
        Category entity = categoryRepository.getOne(id);
        entity.setName(dto.getName());
        entity = categoryRepository.save(entity);
        return new CategoryDTO(entity);
    } catch (EntityNotFoundException e) {
        throw new ResourceNotFoundException("Id Not Found" + id);}
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id Not Found " + id);
        }
        try {
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade e referencial");
        }



    }



}
