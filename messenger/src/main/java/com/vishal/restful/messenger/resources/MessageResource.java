package com.vishal.restful.messenger.resources;

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
//import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.vishal.restful.messenger.model.Message;
import com.vishal.restful.messenger.resources.beans.MessageFilterBean;
import com.vishal.restful.messenger.service.MessageService;

@Path("messages")
@Consumes(MediaType.APPLICATION_JSON)
// @Produces(value ={MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

	MessageService ms = new MessageService();

	/*
	 * @GET public List<Message> getMessages(@BeanParam MessageFilterBean
	 * filterBean) { if (filterBean.getYear() > 0) return
	 * ms.getAllMessagesForYear(filterBean.getYear()); if ((filterBean.getStart() >=
	 * 0) && (filterBean.getSize() > 0)) return
	 * ms.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
	 * return ms.getAllMessages(); }
	 */

	@GET
	@Produces(MediaType.APPLICATION_JSON) // This is to override the class level @Produces value
	public List<Message> getAllJsonMessagesforYear(@BeanParam MessageFilterBean filterBean) {
		if (filterBean.getYear() > 0)
			return ms.getAllMessagesForYear(filterBean.getYear());
		if ((filterBean.getStart() >= 0) && (filterBean.getSize() > 0))
			return ms.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		return ms.getAllMessages();
	}

	@GET
	@Produces(MediaType.TEXT_XML) // This is to override the class level @Produces value
	public List<Message> getAllXmlMessagesforYear(@BeanParam MessageFilterBean filterBean) {
		if (filterBean.getYear() > 0)
			return ms.getAllMessagesForYear(filterBean.getYear());
		if ((filterBean.getStart() >= 0) && (filterBean.getSize() > 0))
			return ms.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		return ms.getAllMessages();
	}

	/*
	 * @GET
	 * 
	 * @Path("/{messageId}") public Message getMessage(@PathParam("messageId") Long
	 * messageId){ Message message=ms.getMessage(messageId);
	 * 
	 * return message; }
	 */
	// ---> The above method is modified to contain links as follows
	// Adding HATEOS LINKS for self messages

	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") Long messageId, @Context UriInfo uriInfo) {
		Message message = ms.getMessage(messageId);
		message.addLink(this.getUriForSelf(uriInfo, message), "self"); // Adding link to message that just got
		message.addLink(this.getUriForProfile(uriInfo, message), "profile"); // Adding link to profile of the person who
																				// commented this
		message.addLink(this.getUriForComments(uriInfo, message), "comments");
		return message;
	}

	private String getUriForComments(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder()
				// .path(CommentResource.class) does work since it's a sub class. Only "/" is
				// registered
				.path(MessageResource.class).path(MessageResource.class, "getCommentResource") // Has a variable here
																								// {messageId}
				.resolveTemplate("messageId", message.getId()) // Likewise resolve all variables
				.path(CommentResource.class).build();
		return uri.toString();
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		return uriInfo.getBaseUriBuilder().path(MessageResource.class).path(Long.toString(message.getId())).build()
				.toString();
	}

	// Now Adding links to profile

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder().path(ProfileResource.class).path(message.getAuthor()).build();
		return uri.toString();
	}

	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo) throws URISyntaxException {
		Message newMessage = ms.addMessage(message);
		// or return Response.status(Status.CREATED).entity(newMessage).build();
		// or return Response.created(new
		// URI("/messenger/webapi/messages/"+newMessage.getId()))
		// .entity(newMessage)
		// .build();
		// Better way
		return Response.created(new URI(uriInfo.getPath() + newMessage.getId())).entity(newMessage).build();
	}

	@PUT
	@Path("/{messageId}")
	public Message updateMessage(Message msg, @PathParam("messageId") long id) {
		msg.setId(id);
		return ms.updateMessage(msg);
	}

	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id) {
		ms.removeMessage(id);
	}

	static CommentResource cr = new CommentResource();

	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return cr;
	}

}
