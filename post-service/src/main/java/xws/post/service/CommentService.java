package xws.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.post.domain.Comment;
import xws.post.repository.CommentRepository;

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

}
