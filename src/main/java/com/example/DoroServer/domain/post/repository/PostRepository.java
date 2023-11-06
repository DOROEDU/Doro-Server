package com.example.DoroServer.domain.post.repository;

import com.example.DoroServer.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long>, PostRepositoryCustom {

}
