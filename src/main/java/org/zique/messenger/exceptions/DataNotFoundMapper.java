package org.zique.messenger.exceptions;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.zique.messenger.model.ErrorMessage;

@Provider//Tells JaxRS that this mapper object is responsible for handling exceptions
public class DataNotFoundMapper implements ExceptionMapper<NotFoundException>{

	@Override
	public Response toResponse(NotFoundException ex) {
		// TODO Auto-generated method stub
		return Response.status(Status.NOT_FOUND)
					   .entity(new ErrorMessage(ex.getMessage(), 404, "http://www.google.com"))
					   .build();
	}

}
