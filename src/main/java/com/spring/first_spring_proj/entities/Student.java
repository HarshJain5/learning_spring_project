    package com.spring.first_spring_proj.entities;

    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotBlank;
    import lombok.Getter;
    import lombok.Setter;

    @Entity
    @Table(name = "student")
    @Getter
    @Setter
    public class Student {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        @Column(name = "id",nullable = false)
        private long id;

        @Column(name = "first_name",nullable = false)
        @NotBlank
        private String firstName;

        @Column(name = "last_name",nullable = false)
        @NotBlank
        private String lastName;
    }
