package org.zique.messenger.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.zique.messenger.model.Profile;
import org.zique.messenger.service.ProfileService;

@Path("/profiles")
@Consumes(MediaType.APPLICATION_JSON)	//needed to let Jersey know what to expect in POST
@Produces(MediaType.APPLICATION_JSON)	//needed to let Jersey know what to send as a response
public class ProfileResource {
	private ProfileService profileService = new ProfileService();
	private Map<String, Profile> profiles = profileService.getProfileMap();
	
	@GET
	public List<Profile> getProfiles(){
		return new ArrayList<Profile>(profiles.values());
	}
	
	@POST
	public Profile addProfile(Profile profile){
		profile.setId(profiles.size() + 1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	@GET
	@Path("/{profileName}")
	public Profile getProfile(@PathParam("profileName") String profileName){
		return profileService.getAllProfiles().get(0);
	}
	
	@PUT
	@Path("/{profileName}")
	public Profile updateProfile(@PathParam("profileName")String  profileName, Profile profile){
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	@DELETE
	@Path("/{profileName}")
	public void deleteProfile(@PathParam("profileName")String profileName){
		profiles.remove(profileName);
	}
	
}
