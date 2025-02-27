package com.devwell.dscatalog.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


//serializabe padrao java para que o objetio seja convertido em bites
//passar objeto em redes
@Entity
@Table(name = "tb_category")
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
   @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant createdAt;
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updatedAt;

    @ManyToMany(mappedBy = "categories")
    private Set<Product> products = new HashSet<>();



    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    public Category() {

    }

    public Set<Product> getProducts() {
        return products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    //toda vez que executar um save no jpa ele já adicoina o instant now
    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
    }

    //toda vez que executar um update seta o instant now
    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }


    //comparar se um objeto é igual a outro
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(name, category.name);
    }

    //comparação
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
