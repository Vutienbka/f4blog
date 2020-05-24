package com.codegym.Model;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "post", schema = "project")
public class PostEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title", length=1000)
    private String title;
    @Column(name = "publishedStatus")
    private byte publishedStatus = 0;
    @Column(name = "publishTime")
    private Timestamp publishTime;
    @Column(name = "createdAt")
    private Timestamp createdAt;
    @Column(name = "updatedAt")
    private Timestamp updatedAt;
    @Column(name = "content",length = 15000)
    private String content;
    @OneToMany(mappedBy = "postByPostId", fetch = FetchType.EAGER)
    private Set<CommentEntity> commentsById;
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private UserEntity userByUserId;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(name = "post_category", joinColumns = @JoinColumn(name = "postId"), inverseJoinColumns = @JoinColumn(name = "categoryId"))
    private Set<CategoryEntity> categoryEntityList;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "post_tag", joinColumns = @JoinColumn(name = "postId"), inverseJoinColumns = @JoinColumn(name = "tagId"))
    private Set<TagEntity> tagEntityList;
    @OneToMany(mappedBy = "postByPostId", fetch = FetchType.EAGER)
    private Set<PostLikeEntity> postLikesById;
    public PostEntity() {
    }

    public PostEntity(String title, Timestamp createdAt, String content, UserEntity userByUserId) {
        this.title = title;
        this.createdAt = createdAt;
        this.content = content;
        this.userByUserId = userByUserId;
    }

    public Set<TagEntity> getTagEntityList() {
        return tagEntityList;
    }

    public void setTagEntityList(Set<TagEntity> tagEntityList) {
        this.tagEntityList = tagEntityList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte getPublishedStatus() {
        return publishedStatus;
    }

    public void setPublishedStatus(byte publishedStatus) {
        this.publishedStatus = publishedStatus;
    }

    public Timestamp getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Timestamp publishTime) {
        this.publishTime = publishTime;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Set<CommentEntity> getCommentsById() {
        return commentsById;
    }

    public void setCommentsById(Set<CommentEntity> commentsById) {
        this.commentsById = commentsById;
    }

    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }

    public Set<CategoryEntity> getCategoryEntityList() {
        return categoryEntityList;
    }

    public void setCategoryEntityList(Set<CategoryEntity> categoryEntityList) {
        this.categoryEntityList = categoryEntityList;
    }

    public Set<PostLikeEntity> getPostLikesById() {
        return postLikesById;
    }

    public void setPostLikesById(Set<PostLikeEntity> postLikesById) {
        this.postLikesById = postLikesById;
    }
}
