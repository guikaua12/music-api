package me.approximations.music.entities;

import jakarta.persistence.*;
import lombok.*;
import me.approximations.music.utils.CloudflareObjectUrlResolverUtils;

@AllArgsConstructor
@NoArgsConstructor(force=true)
@Getter
@Setter
@EqualsAndHashCode(of="id")
@Entity
@Table(name="songs")
public class Song {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private final Long id;
    private String name;
    @Column(name="image_url")
    private String imageUrl;
    @Column(name="music_url")
    private String filename;

    @ManyToOne
    private Album album;

    @Transient
    private String songUrl;

    @PostLoad
    public void postLoad() {
        updateSongUrl();
    }

    public void updateSongUrl() {
        this.songUrl = CloudflareObjectUrlResolverUtils.getObjectUrl(filename);
    }
}
