package xws.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.post.domain.Comment;
import xws.post.domain.Post;
import xws.post.dto.CommentDTO;
import xws.post.repository.CommentRepository;
import xws.post.repository.PostRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

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

	public List<CommentDTO>getCommentsForPost(String postID){
		Long id = Long.parseLong(postID);
		Post p = postRepository.findById(id).orElseGet(null);
		List<Comment> comments = commentRepository.findAllByPost(p);
		List<CommentDTO> dtos = new ArrayList<>();
		for(Comment c : comments){
			String username = c.getUsername();
			String text = c.getText();
			Date date = c.getCreated();
			dtos.add(new CommentDTO(username,text,date));
		}
		return dtos;
	}
}
