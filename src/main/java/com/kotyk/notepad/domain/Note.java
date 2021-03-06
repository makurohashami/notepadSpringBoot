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
@ToString
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
    private Date endTime;
    private Boolean isDone;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @ToString.Exclude
    private Author noteAuthor;
    private Boolean isDeleted;

}
