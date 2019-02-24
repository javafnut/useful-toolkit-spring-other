package com.ibexsys.toolkit.websvc.rest.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.ibexsys.toolkit.websvc.rest.entity.post.Post;
import com.ibexsys.toolkit.websvc.rest.entity.user.User;
import com.ibexsys.toolkit.websvc.rest.entity.user.UserDaoService;

// @TODO Get all static methods ??? is this an exception to best practices
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.ibexsys.toolkit.websvc.rest.entity.user.UserNotFoundException;
import com.ibexsys.toolkit.websvc.rest.repository.PostRepository;
import com.ibexsys.toolkit.websvc.rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ControllerLinkBuilderFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserDaoService service;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PostRepository postRepo;


    @GetMapping("/users")
    public List<User> retreiveAllUsers(){
       return userRepo.findAll();
    }


    // Non-HATEOAS VERSION
    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable Long id){

        Optional<User> user = userRepo.findById(id);

         if (!user.isPresent()) {
            throw new UserNotFoundException("User Not Found id:" + id);
         }

        return user;
     }

//     @GetMapping("/users/{name}")
//     public List<User> getUserByName(@PathVariable String name){
//        List<User> users = userRepo.findByName(name);
//
//        if (users.size() == 0 ){
//            throw new UserNotFoundException("User(s) Name Not Found:" + name);
//        }
//
//        return users;
//
//     }

    // HATEOAS VERSION
    @GetMapping("/user_hateoas/{id}")
    public  Resource<User> getUserByIdHATEOAS(@PathVariable Long id){

        Optional<User> user = userRepo.findById(id);
        ControllerLinkBuilder linkTo = null;

        if (!user.isPresent()) {
            throw new UserNotFoundException("User Not Found id:" + id);
        }

        /** HATEOS - Simple Example
         - Used for returning multiple links on one page
         - Create "all-users", link in the results SERVER_PATYH + /users
         - In this case use method retrieveAllUsers()
         1. Create a resource
         2. use ControllerLinkBuilder to link the method of this call to call methdd
         3. Add link to resource
         **/

        Resource<User> resource =  new Resource<User>(user.get());

        // Add All Users
        linkTo = linkTo(methodOn(this.getClass()).retreiveAllUsers());
        resource.add(linkTo.withRel("all-users"));

       // Delete this user - @TODO how to change header from get to delete and handle void return
       // linkTo = linkTo(methodOn(this.getClass()).deleteUserById(id));
       // resource.add(linkTo.withRel("delete-this-user"));

        return resource;
    }


     @DeleteMapping("/users/{id}")
     public void deleteUserById(@PathVariable Long id){

         Optional<User> user = userRepo.findById(id);

         if (user.isPresent()) {
             userRepo.deleteById(id);
         } else {
             throw new UserNotFoundException("User Not Found id:" + id);
         }
     }


     @PostMapping("/users")
     public ResponseEntity<Object> createUser(@Valid @RequestBody User user){

         User savedUser = userRepo.save(user);

        // Create URI of created user
         URI location = ServletUriComponentsBuilder
                 .fromCurrentRequest()
                 .path("/{id}")
                 .buildAndExpand(savedUser.getId()).toUri();

         return ResponseEntity.created(location).build();
     }

    @GetMapping("/users/{id}/post")
    public List<Post> getPostsUserById(@PathVariable Long id){

        // Optional<User> user = userR epo.findById(id);
        //Optional<Post> posts =
        List<Post> posts = postRepo.getAllPostsByUserId();

//        if (!posts.isPresent()) {
//            throw new UserNotFoundException("User Not Found id:" + id);
//        }

        return posts;
    }





//     @PostMapping("/users/{id}/posts")
//     public ResponseEntity<Object> createPost(@PathVariable Long id,@Valid @RequestBody Post post)
//     {
//
//         //Optional<User> user = userRepo.findById(id);
//         User user = userRepo.findById(id).get();
//
//         if (user == null) {
//
//             throw new UserNotFoundException("User Not Found id:" + id);
//         }
//
//
//         Post savedPost = postRepo.save(post);
//
//
//         user.addPost(savedPost);
//         user = userRepo.save(user);
//
//
//         // Create URI of created user
//         URI location = ServletUriComponentsBuilder
//                 .fromCurrentRequest()
//                 .path("/{id}")
//                 .buildAndExpand(user).toUri();

//         // Create URI of created user
//         URI location = ServletUriComponentsBuilder
//                 .fromCurrentRequest()
//                 .path("/{id}")
//                 .buildAndExpand(savedUser.getId()).toUri();

//         return ResponseEntity.created(location).build();
//
//
//    }

}
