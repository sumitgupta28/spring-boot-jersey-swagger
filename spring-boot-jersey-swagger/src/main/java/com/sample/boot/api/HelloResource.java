package com.sample.boot.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.sample.boot.model.Hello;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@Path("/myapp")
@Api(value = "Hello resource", produces = MediaType.APPLICATION_JSON)
public class HelloResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(HelloResource.class);

	@GET
	@Path("v1/hello/{name}")
	@ApiOperation(value = "Gets a hello resource. Version 1 - (version in URL)", response = Hello.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "hello resource found"), @ApiResponse(code = 404, message = "Given admin user not found")
	})
	public Response getHelloVersionInUrl(@PathParam("name") String name) {
		LOGGER.info("getHelloVersionInUrl() v1");
		return this.getHello(name, "Version 1 - passed in URL");
	}

	@GET
	@Path("hello/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Gets a hello resource. World Version 1 (version in Accept Header)", response = Hello.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "hello resource found"), @ApiResponse(code = 404, message = "hello resource not found")
	})
	public Response getHelloVersionInAcceptHeader(@PathParam("name") String name) {
		LOGGER.info("getHelloVersionInAcceptHeader() v1");
		return this.getHello(name, "Version 1 - passed in Accept Header");
	}

	private Response getHello(String name, String partialMsg) {
		if ("404".equals(name)) {
			return Response.status(Status.NOT_FOUND).build();
		}
		Hello result = new Hello();
		result.setMsg(String.format("Hello %s. %s", name, partialMsg));
		return Response.status(Status.OK).entity(result).build();
	}

}
