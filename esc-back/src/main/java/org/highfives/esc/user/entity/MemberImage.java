package org.highfives.esc.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member_image")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberImage {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "original_file_name")
    private String originalFileName;
    @Column(name = "rename_file_name")
    private String renameFileName;
    @Column(name = "saved_url")
    private String savedUrl;
    @Column(name = "user_id")
    private int userId;

    @Builder
    public MemberImage(int id, String originalFileName, String renameFileName, String savedUrl, int userId) {
        this.id = id;
        this.originalFileName = originalFileName;
        this.renameFileName = renameFileName;
        this.savedUrl = savedUrl;
        this.userId = userId;
    }
}
