package com.codegym.Controller;

import com.codegym.Model.MediaEntity;
import com.codegym.Model.PostEntity;
import com.codegym.Model.Response;
import com.codegym.Model.UserEntity;
import com.codegym.Service.IMediaService;
import com.codegym.Service.IUserService;
import com.codegym.Service.PostService;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.server.UnicastServerRef;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
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
    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    public ResponseEntity<List<UserEntity>> listAllUsers() {
        List<UserEntity> userList = userService.findAll();
        if (userList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllMedias", method = RequestMethod.GET)
    public ResponseEntity<List<MediaEntity>> listAllMedias() {
        List<MediaEntity> mediaEntities = mediaService.findAll();
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

    public ResponseEntity<Response> addPost(@RequestPart("file[]") MultipartFile[] file, @ModelAttribute PostEntity post, @ModelAttribute("userId") String userId) {
        try {
            if (file != null) {
                for (int i = 0; i < file.length; i++)
                    System.out.println(file[i].getOriginalFilename());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Long currentUserId = Long.parseLong(userId);
        UserEntity user = userService.findById(currentUserId);
        user.setCommentsById(null);
        user.setMediaById(null);
        user.setPostsById(null);
        user.setPostLikesById(null);

        if (user != null) {
            Date currentDate = new Date();
            Timestamp currentTime = new Timestamp(currentDate.getTime());
            post.setCreatedAt(currentTime);

            String fileUpload = environment.getProperty("file_upload").toString();
            String postImageName = file[0].getOriginalFilename();
            String srcPostImage = "assets/ImageServer/" + postImageName;
            // Luu file len server
            try {
                FileCopyUtils.copy(file[0].getBytes(), new File(fileUpload + postImageName));
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            PostEntity newPost = new PostEntity(post.getTitle(), post.getCreatedAt(), post.getContent(), srcPostImage, user);
            try {
                postService.save(newPost);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            List<MediaEntity> mediaList = new ArrayList<>();
//            for (int i = 0; i < file.length; i++) {
//                String fileUpload = environment.getProperty("file_upload").toString();
//
//                String mediaName = file[i].getOriginalFilename();
//                String mediaType = file[i].getContentType();
//                String srcMedia = "assets/ImageServer/" + mediaName;
//                MediaEntity newMedia = new MediaEntity(srcMedia, mediaName,mediaType, user);
//                try {
//                    mediaService.save(newMedia);
//                    MediaEntity media = mediaService.findById(newMedia.getId());
//                    mediaList.add(media);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                // Luu file len server
//                try {
//                    FileCopyUtils.copy(file[i].getBytes(), new File(fileUpload + mediaName));
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//            }
            if (newPost != null) {
                return new ResponseEntity<Response>(new Response("Post saved successfully"), HttpStatus.OK);
            } else
                return new ResponseEntity<Response>(new Response("Post not saved"), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<Response>(new Response("Not found user for add Post"), HttpStatus.BAD_REQUEST);
        }
    }

    //------------------- Update
    @RequestMapping(value = "/updatePost/{id}", method = RequestMethod.POST, consumes = "multipart/form-data")
    @ResponseBody
    public ResponseEntity<PostEntity> updatePost(@PathVariable("id") Long postId, @RequestPart("file[]") MultipartFile[] file, @ModelAttribute PostEntity postEntity) {
        try {
            if (file != null) {
                for (int i = 0; i < file.length; i++)
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

        if (file.length > 0) {
            String postImageName = file[0].getOriginalFilename();
            String srcPostImage = "assets/ImageServer/" + postImageName;
            if (srcPostImage != null) {
                currentPostEntity.setPostImage(srcPostImage);
            } else {
                currentPostEntity.setPostImage(currentPostEntity.getPostImage());
            }
        }
        Date currentDate = new Date();
        Timestamp currentTime = new Timestamp(currentDate.getTime());
        postEntity.setUpdatedAt(currentTime);

        currentPostEntity.setId(postEntity.getId());
        currentPostEntity.setTitle(postEntity.getTitle());


        if (currentPostEntity.getPublishedStatus() == 1) {
        } else {
            currentPostEntity.setPublishedStatus(postEntity.getPublishedStatus());
        }
        currentPostEntity.setPublishTime(postEntity.getPublishTime());
        currentPostEntity.setUpdatedAt(postEntity.getUpdatedAt());
        currentPostEntity.setContent(postEntity.getContent());
        postService.save(currentPostEntity);

        return new ResponseEntity<>(currentPostEntity, HttpStatus.OK);
    }

    //    //------------------- Delete
    @RequestMapping(value = "/deletePost/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<PostEntity> deletePost(@PathVariable("id") Long id) {
        System.out.println("Fetching & Deleting Post with id " + id);

        PostEntity postEntity = postService.findById(id);
        if (postEntity == null) {
            System.out.println("Unable to delete. Post with id " + id + " not found");
            return new ResponseEntity<PostEntity>(HttpStatus.NOT_FOUND);
        }

        postService.remove(id);
        return new ResponseEntity<PostEntity>(HttpStatus.NO_CONTENT);
    }

    //--------------------------TU----------------------
    @GetMapping(value = "/getpostbyuser")
    public ResponseEntity<List<PostEntity>> getPostByUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Long id = this.userService.findByUserName(name).getId();
        List<PostEntity> list = this.userService.findPostByUser(id);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/getprivatepost/{id}")
    public ResponseEntity<PostEntity> getPostPrivate(@PathVariable("id") Long id) {
       PostEntity postEntity = this.postService.findById(id);
       if (postEntity.getUserByUserId().getUserName() == SecurityContextHolder.getContext().getAuthentication().getName());

       return null;
    }

    @GetMapping(value = "getUserWroteCurrentPost/{id}")
    public ResponseEntity<UserEntity> getUserWroteCurrentPost(@PathVariable("id") Long postId){

        PostEntity currentPost = this.postService.findById(postId);
        Long uerIdOfUserWroteCurrentPost = currentPost.getUserByUserId().getId();
        UserEntity userWroteCurrentPost = this.userService.findById(uerIdOfUserWroteCurrentPost);
        if(userWroteCurrentPost!=null)
        return new ResponseEntity<>(userWroteCurrentPost,HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
//    @PostMapping()
    //--------------------------DUNG----------------------


    //-------------------Retrieve all
//    @RequestMapping(value = "/getAllPosts/", met>>>>>>> 531852606de86e1d6e8e9b0c5274d413b2e21d49hod = RequestMethod.GET)
//    public ResponseEntity<List<PostEntity>> listAllPosts() {
//        List<PostEntity> postEntities = postService.findAll();
//        if (postEntities.isEmpty()) {
//            return new ResponseEntity<List<PostEntity>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
//        }
//        return new ResponseEntity<List<PostEntity>>(postEntities, HttpStatus.OK);
//    }

//    //-------------------Retrieve Single
//    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<PostEntity> getPost(@PathVariable("id") long id) {
//        System.out.println("Fetching Post with id " + id);
//        PostEntity postEntity = postService.findById(id);
//        if (postEntity == null) {
//            System.out.println("Post with id " + id + " not found");
//            return new ResponseEntity<PostEntity>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<PostEntity>(postEntity, HttpStatus.OK);
//    }
//
//    //-------------------Create
//    @RequestMapping(value = "/posts/", method = RequestMethod.POST)
//    public ResponseEntity<Void> createPost(@RequestBody PostEntity postEntity, UriComponentsBuilder ucBuilder) {
//        System.out.println("Creating Post " + postEntity.getTitle());
//        postService.save(postEntity);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(ucBuilder.path("/posts/{id}").buildAndExpand(postEntity.getId()).toUri());
//        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
//    }

//    //------------------- Delete
//    @RequestMapping(value = "/posts/{id}", method = RequestMethod.DELETE)
//    public ResponseEntity<PostEntity> deletePost(@PathVariable("id") Long id) {
//        System.out.println("Fetching & Deleting Post with id " + id);
//
//        PostEntity postEntity = postService.findById(id);
//        if (postEntity == null) {
//            System.out.println("Unable to delete. Post with id " + id + " not found");
//            return new ResponseEntity<PostEntity>(HttpStatus.NOT_FOUND);
//        }
//
//        postService.remove(id);
//        return new ResponseEntity<PostEntity>(HttpStatus.NO_CONTENT);
//    }

}
