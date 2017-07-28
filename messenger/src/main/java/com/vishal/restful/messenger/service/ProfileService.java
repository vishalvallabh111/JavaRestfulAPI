package com.vishal.restful.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.vishal.restful.messenger.database.Database;
import com.vishal.restful.messenger.model.Profile;

public class ProfileService {

	private Map<String, Profile> profiles = Database.getProfiles();

	public ProfileService() {
		profiles.put("vishal", new Profile(1L, "vishal", "vishal", "panguru"));
		profiles.put("udit", new Profile(2L, "udit", "udit", "viyyapu"));
	}

	public List<Profile> getAllProfiles() {
		return new ArrayList<Profile>(profiles.values());

	}

	public Profile getProfile(String profileName) {

		return profiles.get(profileName);
	}

	public Profile addProfile(Profile profile) {
		profile.setId(profiles.size() + 1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}

	public Profile updateProfile(Profile profile) {
		if (profile.getProfileName().isEmpty()) {
			return null;
		}
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}

	public Profile removeProfile(String profileName) {

		return profiles.remove(profileName);
	}

}
