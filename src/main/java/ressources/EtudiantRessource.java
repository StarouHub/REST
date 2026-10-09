package ressources;

import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/etudiants")
public class EtudiantRessource {

    EtudiantBusiness etudiantBusiness = new EtudiantBusiness();
    OptionBusiness optionBusiness = new OptionBusiness();

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEtudiant(Etudiant etudiant) {
        if (etudiant != null && etudiant.getOption() != null && etudiantBusiness.addEtudiant(etudiant)) {
            return Response.status(Response.Status.OK)
                    .entity("Etudiant added successfully")
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Option not found")
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Etudiant> getall() {
        return this.etudiantBusiness.getAllEtudiants();
    }

    @GET
    @Path("/option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getByOption(@QueryParam("codeOption") int codeOption) {
        Option option = this.optionBusiness.getOptionByCode(codeOption);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_PLAIN)
                    .entity("Option not found")
                    .build();
        }
        List<Etudiant> liste = this.etudiantBusiness.getEtudiantsByOption(option);
        return Response.status(Response.Status.OK).entity(new EtudiantList(liste)).build();
    }

    @GET
    @Path("/{identifiant}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByIdentifiant(@PathParam("identifiant") String identifiant) {
        Etudiant etudiant = this.etudiantBusiness.getEtudiantByIdentifiant(identifiant);
        if (etudiant != null) {
            return Response.status(Response.Status.OK).entity(etudiant).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Etudiant not found").build();
    }

    @DELETE
    @Path("/{identifiant}")
    public Response deleteEtudiant(@PathParam("identifiant") String identifiant) {
        if (etudiantBusiness.deleteEtudiant(identifiant)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/{identifiant}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(@PathParam("identifiant") String identifiant, Etudiant newEtudiant) {
        if (etudiantBusiness.updateEtudiant(identifiant, newEtudiant)) {
            return Response.status(Response.Status.OK).entity("Etudiant updated successfully").build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
