package com.devwell.dscatalog.services;


import com.devwell.dscatalog.dto.*;
import com.devwell.dscatalog.entities.Category;
import com.devwell.dscatalog.entities.Role;
import com.devwell.dscatalog.entities.User;
import com.devwell.dscatalog.repositories.CategoryRepository;
import com.devwell.dscatalog.repositories.RoleRepository;
import com.devwell.dscatalog.repositories.UserRepository;
import com.devwell.dscatalog.repositories.UserRepository;
import com.devwell.dscatalog.services.exceptions.DatabaseException;
import com.devwell.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    //**LISTA COMPLETA COM PAGINAÇÃO
    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageable) {
        Page<User> list = userRepository.findAll(pageable);
        return list.map(UserDTO::new);
    }

    //**POR ID
    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Optional<User> obj = userRepository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
        return new UserDTO(entity);
    }

    //**INSERT
    @Transactional
    public UserDTO create(UserInsertDTO dto) {
        User entity = new User();
        copyDtoToEntity(dto, entity);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity = userRepository.save(entity);
        return new UserDTO(entity);
    }




    //**UPDATE
    @Transactional
    public UserDTO update(Long id, UserUpdateDTO dto) {
        try {
            User entity = userRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
        entity = userRepository.save(entity);
        return new UserDTO(entity);
    } catch (EntityNotFoundException e) {
        throw new ResourceNotFoundException("Id Not Found" + id);}
    }

    //**DELETE
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id Not Found " + id);
        }
        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade e referencial");
        }
    }




    private void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setFirst_name(dto.getFirstName());
        entity.setLast_name(dto.getLastName());
        entity.setEmail(dto.getEmail());

        entity.getRoles().clear();
        for (RoleDTO roleDTO : dto.getRoles()) {
            Role role = roleRepository.getOne(roleDTO.getId());
            entity.getRoles().add(role);
        }

    }





}
