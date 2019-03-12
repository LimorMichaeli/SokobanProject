package services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.SolutionDataMapper;

@Path("/SolutionsProvider/{hashCode}")
public class GetMoves {

	@GET
	public String getSolution(@PathParam("hashCode") int hashCode) {
		SolutionDataMapper dataMapper = new SolutionDataMapper();
        return dataMapper.getState(hashCode).toString();
	}
}
