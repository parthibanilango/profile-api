package com.gl.profile.application.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gl.profile.application.profile.model.UserProfile;
import com.gl.profile.application.repository.UserProfileRepository;
import com.gl.profile.application.service.SequenceGeneratorService;

@RestController
@RequestMapping("/api/v1")
public class ProfileController {
    
	@Autowired
    private UserProfileRepository userProfileRepository;
	
	@Autowired
	private SequenceGeneratorService sequenceGenerator;
 
    @GetMapping("/profile/{id}")
    public ResponseEntity <UserProfile> getProfileById(@PathVariable String id)
    throws Exception {
    	Optional<UserProfile> userProfileResp = userProfileRepository.findById(Long.valueOf(id));
    	if(userProfileResp.isPresent()) {
    		return ResponseEntity.ok().body(userProfileResp.get());
    	}else {
    		UserProfile userProfile= new UserProfile();
    		userProfile.setId(Long.valueOf(id));
    		return ResponseEntity.badRequest().body(userProfile);
    	}

    }

    @PostMapping("/profile")
    public UserProfile createArticle(@RequestBody UserProfile userProfile) {
    	userProfile.setId(sequenceGenerator.generateSequence(UserProfile.SEQUENCE_NAME));
        return userProfileRepository.save(userProfile);
    }

    @PutMapping("/profile")
    public ResponseEntity < UserProfile > updateProfile(@RequestBody UserProfile userProfileReq) throws Exception {
    	Optional<UserProfile> userProfileOpt = userProfileRepository.findById(userProfileReq.getId());
    	if(userProfileOpt.isPresent()) {
    		UserProfile userProfile = userProfileOpt.get();
    	   	if(userProfileReq.getUser_id() > 0) {
    	   		userProfile.setUser_id(userProfileReq.getUser_id());
	       	}
	       	if(null != userProfileReq.getCategory_id()) {
	       		userProfile.setCategory_id(userProfileReq.getCategory_id());
	       	}
	    	if(null != userProfileReq.getBio()) {
	    		userProfile.setBio(userProfileReq.getBio());
	    	}
	    	if(null != userProfileReq.getImage_Url()) {
	    		userProfile.setImage_Url(userProfileReq.getImage_Url());
	    	}
	    	if(null != userProfileReq.getFirst_name()) {
	    		userProfile.setFirst_name(userProfileReq.getFirst_name());
	    	}
	    	if(null != userProfileReq.getLast_name()) {
	    		userProfile.setImage_Url(userProfileReq.getImage_Url());
	    	}
	    	if(null != userProfileReq.getPhoneno()) {
	    		userProfile.setPhoneno(userProfileReq.getPhoneno());
	    	}
	        	final UserProfile updatedProfile = userProfileRepository.save(userProfile);
	        	return ResponseEntity.ok(updatedProfile);
	    }else {
	    	return ResponseEntity.badRequest().body(userProfileReq);
	    }
       
    }

    @DeleteMapping("/profile")
    public Map<String, Boolean> deleteArticle(@RequestBody UserProfile userProfileReq) throws Exception
     {
    	Map<String, Boolean> response = new HashMap<>();
    	Optional<UserProfile> userProfileOpt = userProfileRepository.findById(userProfileReq.getId());
    	if(userProfileOpt.isPresent()) {
    		userProfileRepository.delete(userProfileReq);
    		response.put("deleted", Boolean.TRUE);
    	}else {
    		response.put("deleted", Boolean.FALSE);
    	}
        return response;
    }
}