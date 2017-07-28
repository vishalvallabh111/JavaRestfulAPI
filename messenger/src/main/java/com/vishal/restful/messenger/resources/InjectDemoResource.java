package com.vishal.restful.messenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("injectdemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemoResource {
	// First way to access parameters
	@GET
	@Path("/annotations")
	public String getParamsUsingAnnotations(@MatrixParam("param") String id, @HeaderParam("authToken") String token,
			@CookieParam("cookie") String cookieValue) {
		return "Matrix param: " + id + " auth token: " + token + " Cookie Value: " + cookieValue;
	}

	// Second way to access parameters
	@GET
	@Path("/context")
	public String getParamUsingContext(@Context UriInfo uriInfo, @Context HttpHeaders headers) {
		String path = uriInfo.getAbsolutePath().toString();
		String cookies = headers.getCookies().toString();
		return path + " : " + cookies;
	}
	// Third way to access parameters: beanparam
	// Look into MessageResource
}