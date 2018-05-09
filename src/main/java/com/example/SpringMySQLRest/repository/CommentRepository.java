package com.example.SpringMySQLRest.repository;

import com.example.SpringMySQLRest.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author David
 */
//@RestResource(path = "comment")
public interface  CommentRepository extends JpaRepository<Comment, Long>{

}
