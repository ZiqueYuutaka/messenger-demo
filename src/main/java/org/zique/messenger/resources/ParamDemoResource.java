package org.zique.messenger.resources;

import javax.ws.rs.BeanParam;
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

import org.zique.messenger.resources.beans.MessageFilterBean;

/**
 * Demo class of various paramater annotations that
 * capture their respective data
 * @MatrixParam
 * @HeaderParam
 * @CookieParam
 * @FormParam is omitted
 * @author Zique Yuutaka
 *
 */

@Path("/paramdemo")
@Consumes(MediaType.TEXT_PLAIN)	//needed to let Jersey know what to expect in POST
@Produces(MediaType.TEXT_PLAIN)	//needed to let Jersey know what to send as a response
public class ParamDemoResource {

	/**
	 * First way to access parameters
	 * @param matrixParam
	 * @param header
	 * @param cookieName
	 * @return
	 */
	@GET
	@Path("/annotations")
	public String getParams(@MatrixParam("param") String matrixParam,
							@HeaderParam("customParam")String header,
							@CookieParam("cookieName")String cookieName){
		StringBuilder builder = new StringBuilder();
		if(!matrixParam.equals("")){
			builder.append("Matrix param: " + matrixParam);
		}
		if(header != null){
			builder.append("\nHeader param: " + header);
		}
		/*		if(!cookieName.equals("")){
			builder.append("\nCookie name: " + cookieName);
		}*/
		
		return builder.toString();
	}
	
	/**
	 * Second way to access parameters
	 * @param uriInfo
	 * @param headers
	 * @return
	 */
	@GET
	@Path("context")
	public String getParamsUsingContext(@Context UriInfo uriInfo, @Context HttpHeaders headers){
		
		String path = uriInfo.getAbsolutePath().toString();
		String headerStr = headers.getCookies().toString();
		
		return "Path: " +path + ", Cookies: " + headerStr;
	}
	
	@GET
	@Path("beans")
	public String getParamsUsingBeans(@BeanParam MessageFilterBean filterBean){
		StringBuilder builder = new StringBuilder();
		
		if(filterBean.getYear()!= 0){
			builder.append("Year: " + filterBean.getYear());
		}
		if(filterBean.getStart()!= 0){
			builder.append("\nStart: " + filterBean.getStart());
		}
		if(filterBean.getSize() != 0){
			builder.append("\nSize: " + filterBean.getSize());
		}
		
		return builder.toString();
	}
}
