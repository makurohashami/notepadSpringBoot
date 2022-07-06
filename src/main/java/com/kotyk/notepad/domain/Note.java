package com.kotyk.notepad.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@Entity
@Table(name = "notes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String title;
    private String description;
    private NoteType type;
    private NoteStatus status;
    private Date timeOfCreation;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author noteAuthor;
    private Boolean isDeleted;

}
