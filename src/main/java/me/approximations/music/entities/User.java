package me.approximations.music.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import me.approximations.music.entities.enums.AccountType;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor(force=true)
@Getter
@Setter
@EqualsAndHashCode(of="id")
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private final Long id;
    private String email;
    private String name;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @JsonIgnore
    @OneToMany(mappedBy="artist")
    private final Set<Album> albums = new HashSet<>();
}
