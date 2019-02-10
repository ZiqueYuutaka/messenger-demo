package org.zique.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;

import org.zique.messenger.database.DatabaseClass;
import org.zique.messenger.model.Comment;
import org.zique.messenger.model.Message;

public class CommentService {
	
	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public List<Comment> getAllComments(long messageId){
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return new ArrayList<Comment>(comments.values());
	}
	
	public Comment getComment(long messageId, long commentId){
		Message message = messages.get(messageId);
		if(message == null){
			//following exception displays the tomcat error page
			//throw new WebApplicationException(Status.NOT_FOUND);//Has multiple constructors for various issues
			
			//following code uses constructor with response to return a proper JSON error
			//not necessarily "clean" since response is more a resource error rather than a service error
/*			Response response = Response.status(Status.NOT_FOUND)
					   					.entity(new ErrorMessage("message not found", 404, "http://my.example.url.com"))
					   					.build();
			throw new WebApplicationException(response);*/
			
			//BEST PRACTICE IS TO USE PROVIDED EXCEPTION CLASSES AND MAPPERS
			throw new NotFoundException("message " + messageId + " not found for comment " + commentId);
		}
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		Comment comment = comments.get(commentId);
		if(comment == null){
			//throw new WebApplicationException(Status.NOT_FOUND);//Has multiple constructors for various issues
			//following code uses constructor with response to return a proper JSON error
			//not necessarily "clean" since response is more a resource error rather than a service error
/*			Response response = Response.status(Status.NOT_FOUND)
					   					.entity(new ErrorMessage("comment not found", 404, "http://my.example.url.com"))
					   					.build();
			throw new WebApplicationException(response);*/
			//BEST PRACTICE IS TO USE EXCEPTION MAPPERS
			throw new NotFoundException("comment " + commentId + " not found");
		}
		return comment;
	}
	
	public Comment addComment(long messageId, Comment comment){
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		comment.setId(comments.size()+1);
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment updateComment(long messageId, Comment comment){
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		if(comment.getId() <= 0){
			return null;
		}
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment removeComment(long messageId, long commentId){
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return comments.remove(commentId);
	}

}
