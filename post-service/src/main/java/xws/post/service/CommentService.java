package xws.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.post.domain.Comment;
import xws.post.domain.Post;
import xws.post.repository.CommentRepository;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;

	public Comment findById(Long id) {
		return commentRepository.findById(id).orElseGet(null);
	}

	public List<Comment> findAll() {
		return commentRepository.findAll();
	}

	public List<Comment> findAllByPost(Post post) {
		return commentRepository.findAllByPost(post);
	}

	public Comment save(String username, Post post, String text) {
		Comment c = new Comment();
		c.setCreated(new Date());
		c.setUsername(username);
		c.setPost(post);
		c.setText(text);
		return commentRepository.save(c);
	}
}
