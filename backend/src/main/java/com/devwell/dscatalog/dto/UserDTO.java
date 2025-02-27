package com.devwell.dscatalog.dto;



import com.devwell.dscatalog.entities.User;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;


    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    Set<RoleDTO> roles = new HashSet<>();




    public UserDTO() {
    }

    public UserDTO(Long id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

    }

    public UserDTO(User entity) {
        id = entity.getId();
        firstName = entity.getFirst_name();
        lastName = entity.getLast_name();
        email = entity.getEmail();
        entity.getRoles().forEach(role -> roles.add(new RoleDTO(role)));


    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }
}
