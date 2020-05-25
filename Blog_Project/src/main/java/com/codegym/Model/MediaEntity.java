package com.codegym.Model;

import javax.persistence.*;


@Entity
@Table(name = "media", schema = "project")
public class MediaEntity {
    private Long id;
    private String srcMedia;
    private String mediaName;
    private String mediaType;
    private UserEntity userByUserId;

    public MediaEntity() {
    }

    public MediaEntity(String srcMedia, String mediaName, String mediaType, UserEntity userByUserId) {
        this.srcMedia = srcMedia;
        this.mediaName = mediaName;
        this.mediaType = mediaType;
        this.userByUserId = userByUserId;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "srcMedia", length = 1000)
    public String getSrcMedia() {
        return srcMedia;
    }

    public void setSrcMedia(String srcMedia) {
        this.srcMedia = srcMedia;
    }

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }

    @Column(name = "mediaName")
    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    @Column(name = "mediaType")
    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
}
