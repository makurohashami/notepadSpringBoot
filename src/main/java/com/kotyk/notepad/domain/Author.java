package com.kotyk.notepad.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;


@Builder
@Entity
@Table(name = "authors")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String username;
    private String name;
    private String email;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "noteAuthor")
    private Collection<Note> notes;
    private Boolean isDeleted;
}
