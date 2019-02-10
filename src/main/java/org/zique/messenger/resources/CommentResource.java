package org.zique.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.zique.messenger.model.Comment;
import org.zique.messenger.service.CommentService;

/**
 * Sub resource for the comments section of the Message
 * @author Zique Yuutaka
 *
 */
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)	//needed to let Jersey know what to expect in POST
@Produces(MediaType.APPLICATION_JSON)	//needed to let Jersey know what to send as a response
public class CommentResource {
	
	private CommentService commentService = new CommentService();
	
	@GET
	public List<Comment> getAllComments(@PathParam("messageId") long messageId){
		System.out.println("Getting all comments for " +messageId);
		return commentService.getAllComments(messageId);
	}
	
	/**
	 * 
	 * @param messageId comes from the parent resource MessageResource
	 * @param id
	 * @return
	 */
	@GET
	@Path("/{commentId}")
	public Comment getComment(@PathParam("messageId") int messageId, @PathParam("commentId") int id){
		System.out.println("getting comment " + id + " for message " + messageId);
		return commentService.getComment(messageId, id);
	}
	
	@POST
	public Comment addComment(@PathParam("messageId") long messageId, Comment comment){
		System.out.println("POSTING new comment to message " + messageId);
		return commentService.addComment(messageId, comment);
	}
	
	@PUT
	@Path("/{commentId}")
	public Comment updateComment(@PathParam("messageId") long messageId, @PathParam("commentId") int id, Comment comment){
		System.out.println("updating comment " + id + " at message " + messageId);
		comment.setId(id);
		return commentService.updateComment(messageId, comment);
	}
	
	@DELETE
	@Path("/{commentId}")
	public void deteleComment(@PathParam("messageId") int messageId, @PathParam("commentId") int commentId){
		System.out.println("removing comment " + commentId + " at message " + messageId);
		commentService.removeComment(messageId, commentId);
	}
}
