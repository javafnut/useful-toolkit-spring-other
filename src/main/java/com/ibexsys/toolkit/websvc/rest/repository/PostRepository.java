package com.ibexsys.toolkit.websvc.rest.repository;


import com.ibexsys.toolkit.websvc.rest.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

    // List<Post> findbyUserId(Long userId);

//    @Query("Select * from Post p where p.USER_ID  = id")
//    Optional<Post> getPostsByUserId(Long id);
    @Query("select p from Post p")
    List<Post> getAllPostsByUserId();

}