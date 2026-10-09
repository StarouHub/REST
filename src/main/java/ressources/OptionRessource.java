package ressources;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/options")

public class OptionRessource {

OptionBusiness optionBusiness= new OptionBusiness();

@GET
@Produces(MediaType.APPLICATION_JSON)
public List<Option> getall() {
    return this.optionBusiness.getListeOptions();
}

@GET
@Path("/{code}")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public Response getByCode(@PathParam("code") int code) {
    Option option = this.optionBusiness.getOptionByCode(code);
    if (option != null) {
        return Response.status(Response.Status.OK).entity(option).build();
    }
    return Response.status(Response.Status.NOT_FOUND).entity("Option not found").build();
}

@GET
@Path("/semestre")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public Response getBySemestre(@QueryParam("semestre") int semestre) {
    Option option = this.optionBusiness.getOptionByCode(semestre);
    if (option != null) {
        return Response.status(Response.Status.OK).entity(option).build();
    }
    return Response.status(Response.Status.NOT_FOUND).entity("Option not found").build();
}

@POST
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public Response addOption(Option option) {
    if (optionBusiness.addOption(option)) {
        return Response.status(Response.Status.CREATED)
                .entity("Option added successfully")
                .build();
    } else {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Failed to add option")
                .build();
    }

}

@PUT
@Path("/{code}")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)

public Response updateOption(@PathParam("code") int code, Option newOption) {
    if (optionBusiness.updateOption(code,newOption)) {
        return Response.status(Response.Status.OK).entity("Option is required").build();

    }
    return Response.status(Response.Status.NOT_FOUND).build();
}
}