package org.zique.messenger.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.zique.messenger.model.Message;
import org.zique.messenger.resources.beans.MessageFilterBean;
import org.zique.messenger.service.MessageService;

/**
 * Resource that handles API calls
 * @author Zique Yuutaka
 *
 */
@Path("messages")
//Can annotate the entire class with the @Consumes and @Produces if
//	all methods consume and/or produce APPLICATION_JSON
@Consumes(MediaType.APPLICATION_JSON)	//needed to let Jersey know what to expect in POST
@Produces(MediaType.APPLICATION_JSON)	//needed to let Jersey know what to send as a response
public class MessageResource {
	
	MessageService messageService = new MessageService();
	
	@GET
	//@Produces(MediaType.TEXT_PLAIN)	//return content-type
	//@Produces(MediaType.APPLICATION_XML)	//JAX-B converts strings to XML
	//@Produces(MediaType.APPLICATION_JSON) 	//For this to work must have jersey-media-moxy added to pom
	public List<Message> getMessages(@BeanParam MessageFilterBean filter){
		//return "Hello world!";
		
		//filter by year
		if(filter.getYear() > 0){
			System.out.println("filtering by year...");
			return messageService.getAllMessagesForYear(filter.getYear());
		}
		
		//paginate query
		if(filter.getStart() >= 0 && filter.getSize() > 0){
			System.out.println("paginate...");
			return messageService.getAllMessagesPaginated(filter.getStart(), filter.getSize());
		}
		
		return messageService.getAllMessages();
	}
	
	@GET
	//@Path("/test")	//subsequent path but hardcoded
	@Path("/{messageId}")	//variable subsequent path and can be strung together with multiple variables
	//@Produces(MediaType.TEXT_PLAIN)
	//@Produces(MediaType.APPLICATION_JSON) 	//For this to work must have jersey-media-moxy added to pom
	public Message getMessage(@PathParam("messageId")long id, @Context UriInfo info){
		Message message = messageService.getMessage(id);
		
		//HATEOAS idea implementation
		String messageUri = getUriForSelf(info, message);
		String profileUri = getUriForProfile(info, message);
		String commentsUri = getUriForComments(info, message);
		message.addLink(messageUri,"self");
		message.addLink(profileUri,"profile");
		message.addLink(commentsUri, "comments");
		//HATEOAS end
		
		return message;
/*		try{
			Long msgId = Long.valueOf(id);
			for(Message msg: messageService.getAllMessages()){
				if(msgId == msg.getId()){
					return messageService.getAllMessages().get(Integer.parseInt(id) - 1);
				}
			}
			return null;
		}catch(NumberFormatException ex){
			return null;
		}*/
	}
	
	/**
	 * Method to construct the uri of a sub resource
	 * @param info
	 * @param message
	 * @return
	 */
	private String getUriForComments(UriInfo info, Message message) {

		return info.getBaseUriBuilder()							//get the base uri
			.path(MessageResource.class)						//dynamically get resource uri
			.path(MessageResource.class, "getCommentResource")	//get the method that calls subresource
			.path(CommentResource.class)						//get the subresource uri
			.resolveTemplate("messageId", message.getId())		//replace the uri variable with the message id
			.build()
			.toString();
	}

	private String getUriForSelf(UriInfo info, Message message) {
		return info.getBaseUriBuilder()				//get the base uri
				.path(MessageResource.class)			//dynamically get resource uri
				.path(Long.toString(message.getId()))	//add message id to path
				.build()
				.toString();
	}
	
	private String getUriForProfile(UriInfo info, Message message) {
		return info.getBaseUriBuilder()				//get the base uri
			.path(ProfileResource.class)			//dynamically get resource uri
			.path(message.getAuthor())	//add message id to path
			.build()
			.toString();
	}
	
	///////////////////////////////////		POST METHODS		//////////////////////////////
	
	@POST
	//@Consumes(MediaType.APPLICATION_JSON)	//needed to let Jersey know what to expect in POST
	//@Produces(MediaType.APPLICATION_JSON)
	//Jersey knows how to parse incoming JSON data to the object type so
	//the argument just needs to be the correct Object
	//Caveat: Any additional fields passed in with the JSON POST
	//	that is not a field in the object will be ignored and a success message will
	//	be displayed
	public Response addMessage(Message message, @Context UriInfo info)throws URISyntaxException{
		System.out.println("POSTing to " + info.getAbsolutePath());
		Message newMessage = messageService.addMessage(message);
/*		return Response.status(Status.CREATED)
					   .entity(newMessage)
					   .build();*/
		String newId = String.valueOf(newMessage.getId());
		URI uri = info.getAbsolutePathBuilder().path(newId).build(); //can chain multiple path() methods
		return Response.created(uri)
				       .entity(newMessage)
				       .build();
		//return newMessage;
	}
	
	///////////////////////////////////		PUT METHODS		//////////////////////////////
	
	@PUT
	@Path("/{messageId}")
	//@Consumes(MediaType.APPLICATION_JSON)	//needed to let Jersey know what to expect in PUT
	//@Produces(MediaType.APPLICATION_JSON)
	public Message updateMessage(Message msg, @PathParam("messageId")long id){
		//get message id from request url and set it to the incoming object
		//msg.setId(id);
		//update the message
		return messageService.updateMessage(msg);
		//return msg;
	}
	
	///////////////////////////////////		DELETE METHODS		//////////////////////////////
	
	@DELETE
	@Path("/{messageId}")
	//@Produces(MediaType.APPLICATION_JSON)
	public void deleteMessage(@PathParam("messageId")long id){
		//delete the message
		//messageService.removeMessage(msg);
		System.out.println("message deleted...");
	}
	
	///////////////////////////////////		COMMENT METHODS		/////////////////////////////
	
	//HTTP methods not needed so that CommentResource can be mapped correctly
	@Path("/{messageId}/comments")
	//@Produces(MediaType.TEXT_PLAIN)
	public CommentResource getCommentResource(){
		return new CommentResource();
	}

}
