package com.vishal.restful.messenger.resources;

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

import com.vishal.restful.messenger.model.Comment;
import com.vishal.restful.messenger.service.CommentService;

@Path("/") // Since path is referenced from message resource
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CommentResource {

	private CommentService commentservice = new CommentService();

	@GET
	public List<Comment> getAllComments(@PathParam("messageId") long messageId) {
		return commentservice.getAllComments(messageId);
	}

	@POST
	public Comment addMessage(@PathParam("messageId") long messageId, Comment comment) {
		return commentservice.addComment(messageId, comment);
	}

	@PUT
	@Path("/{commentId}")
	public Comment updateMessage(@PathParam("messageId") long messageId, @PathParam("commentId") long id,
			Comment comment) {
		comment.setId(id);
		return commentservice.updateComment(messageId, comment);
	}

	@DELETE
	@Path("/{commentId}")
	public void deleteComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId) {
		commentservice.removeComment(messageId, commentId);
	}

	@GET
	@Path("/{commentId}") // MessageId is in previous call, but this class has access to entire path, so
							// it's okay
	public Comment getComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId) {
		return commentservice.getComment(messageId, commentId);
	}
}