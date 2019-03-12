package services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import model.SolutionDataMapper;

@Path("/IsSolutionExist/{hashCode}")
public class IsExist {

	@GET
	public boolean isExist(@PathParam("hashCode") int hashCode) {
		SolutionDataMapper dataMapper=new SolutionDataMapper();
		return dataMapper.isExsit(hashCode);
	}
}
