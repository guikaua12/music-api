package me.approximations.music.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor(force=true)
@Getter
@Setter
@EqualsAndHashCode(of="id")
@Entity
@Table(name="albums")
public class Album {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private final Long id;
    private String name;

    @ManyToOne
    private User artist;

    @JsonIgnore
    @OneToMany(mappedBy="album")
    private final Set<Song> songs = new HashSet<>();

    public void addSong(Song song) {
        songs.add(song);
    }
}
