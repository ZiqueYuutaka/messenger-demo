package org.zique.messenger.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zique.messenger.model.Profile;

/**
 * Stub of a possible back end database accessor
 * @author Zique Yuutaka
 *
 */
public class ProfileService {
	
	private Map<String, Profile> profiles = new HashMap<>();
	
	public ProfileService(){
		Profile p1 = new Profile(1L, "userName1", "Hello", "Dude");
		Profile p2 = new Profile(2L, "userName2", "World", "Girl");
		
		profiles.put(p1.getProfileName(), p1);
		profiles.put(p2.getProfileName(), p2);
	}
	
	public List<Profile> getAllProfiles(){
		return new ArrayList<Profile>(profiles.values());
	}
	
	public Map<String, Profile> getProfileMap(){
		return profiles;
	}
	
	public Profile getProfile(long id){
		return profiles.get(id);
	}
	
	public Profile addProfile(Profile profile){
		return profiles.put(profile.getProfileName(), profile);
	}
	
	public Profile updateProfile(Profile profile){
		return profiles.put(profile.getProfileName(), profile);
	}
	
	public Profile removeProfile(String key){
		return profiles.remove(key);
	}
}
