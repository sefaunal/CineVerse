package com.sefaunal.cineverse.Service;

import com.sefaunal.cineverse.Model.Comment;
import com.sefaunal.cineverse.Model.User;
import com.sefaunal.cineverse.Repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * @author github.com/sefaunal
 * @since 2025-04-01
 */
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void saveComment(Comment comment, User user) {
        comment.setUserID(user.getID());
        comment.setUserDetails(user);
        comment.setCommentDate(Instant.now());

        commentRepository.save(comment);
    }

    public List<Comment> findAllByMovieID(String movieID) {
        return commentRepository.findAllByMovieID(movieID);
    }
}
