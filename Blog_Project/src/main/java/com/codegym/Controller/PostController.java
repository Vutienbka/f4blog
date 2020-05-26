package com.codegym.Controller;

import com.codegym.Model.MediaEntity;
import com.codegym.Model.PostEntity;
import com.codegym.Model.Response;
import com.codegym.Model.UserEntity;
import com.codegym.Service.IMediaService;
import com.codegym.Service.IUserService;
import com.codegym.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@RestController
public class PostController {

    @Autowired
    IUserService userService;
    @Autowired
    PostService postService;
    @Autowired
    IMediaService mediaService;
    @Autowired
    Environment environment;
    @Autowired
    AuthenticationManager authenticationManager;

    //--------------------------TOAN----------------------

    //--------------------------TIEN----------------------
    @RequestMapping(value = "/getAllMedias", method = RequestMethod.GET)
    public ResponseEntity<List<MediaEntity>> listAllMedias() {
        List<MediaEntity> mediaEntities= mediaService.findAll();
        if (mediaEntities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(mediaEntities, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllPosts", method = RequestMethod.GET)
    public ResponseEntity<List<PostEntity>> listAllPosts() {
        List<PostEntity> postEntities = postService.findAll();
        if (postEntities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(postEntities, HttpStatus.OK);
    }

    @PostMapping(value = "/savePost", consumes = "multipart/form-data")
    @ResponseBody
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Response> addPost(@RequestPart("file[]") MultipartFile[] file, @ModelAttribute PostEntity post) {
        try {
            if (file != null) {
                for(int i = 0; i<file.length;i++)
                    System.out.println(file[i].getOriginalFilename());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//       String userName = ((UserDetails)(SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getUsername();

        Long userId = 1L;
        UserEntity user = userService.findById(userId);
        user.setCommentsById(null);
        user.setMediaById(null);
        user.setPostsById(null);
        user.setPostLikesById(null);

        if(user !=null) {
            Date currentDate = new Date();
            Timestamp currentTime = new Timestamp(currentDate.getTime());
            post.setCreatedAt(currentTime);
            PostEntity newPost = new PostEntity(post.getTitle(),post.getCreatedAt(),post.getContent(),user);
            try {
                postService.save(newPost);
            }catch (Exception e){
                e.printStackTrace();
            }

            List<MediaEntity> mediaList = new ArrayList<>();
            for (int i = 0; i < file.length; i++) {
                String fileUpload = environment.getProperty("file_upload").toString();

                String mediaName = file[i].getOriginalFilename();
                String mediaType = file[i].getContentType();
                String srcMedia = "./assets/ImageServer/" + mediaName;
                MediaEntity newMedia = new MediaEntity(srcMedia, mediaType, mediaName, user);
                try {
                    mediaService.save(newMedia);
                    MediaEntity media = mediaService.findById(newMedia.getId());
                    mediaList.add(media);
                }catch (Exception e){
                    e.printStackTrace();
                }
                // Luu file len server
                try {
                    FileCopyUtils.copy(file[i].getBytes(), new File(fileUpload + mediaName));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (newPost != null && (mediaList.size() == file.length)) {
                return new ResponseEntity<Response>(new Response("Post saved successfully"), HttpStatus.OK);
            } else
                return new ResponseEntity<Response>(new Response("Post not saved"), HttpStatus.BAD_REQUEST);
        }else {
            return  new ResponseEntity<Response>(new Response("Not found user for add Post"), HttpStatus.BAD_REQUEST);
        }
    }

        //------------------- Update
    @RequestMapping(value = "/updatePost/{id}", method = RequestMethod.POST,consumes = "multipart/form-data")
    @ResponseBody
    public ResponseEntity<PostEntity> updatePost(@PathVariable("id") Long postId, @RequestPart("file[]") MultipartFile[] file, @ModelAttribute PostEntity postEntity) {
        try {
            if (file != null) {
                for(int i = 0; i<file.length;i++)
                    System.out.println(file[i].getOriginalFilename());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Updating Post " + postId);
        PostEntity currentPostEntity = postService.findById(postId);
        if (currentPostEntity == null) {
            System.out.println("Post with id " + postId + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Date currentDate = new Date();
        Timestamp currentTime = new Timestamp(currentDate.getTime());
        postEntity.setUpdatedAt(currentTime);

        currentPostEntity.setId(postEntity.getId());
        currentPostEntity.setTitle(postEntity.getTitle());
        if(currentPostEntity.getPublishedStatus()==1){
        }else {
            currentPostEntity.setPublishedStatus(postEntity.getPublishedStatus());
        }
        currentPostEntity.setPublishTime(postEntity.getPublishTime());
        currentPostEntity.setUpdatedAt(postEntity.getUpdatedAt());
        currentPostEntity.setContent(postEntity.getContent());
        postService.save(currentPostEntity);

        return new ResponseEntity<>(currentPostEntity, HttpStatus.OK);
    }

    //--------------------------TU----------------------

    //--------------------------DUNG----------------------

}
