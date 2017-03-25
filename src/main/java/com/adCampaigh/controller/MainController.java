package com.adCampaigh.controller;

import com.adCampaigh.DTO.AdCampaigh;
import com.adCampaigh.DTO.AddCampReq;
import com.adCampaigh.service.MapDB;

import org.apache.log4j.Logger;
import org.hibernate.validator.internal.util.privilegedactions.GetConstraintValidatorList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by daweizhuang on 7/15/16.
 */
@Controller
public class MainController {
    private final static Logger LOGGER = Logger.getLogger(MainController.class);
    
    @Autowired
    MapDB map;
    
    @RequestMapping("/")
	public String welcome(){
		return "hello";
	}
    
    @ResponseBody
    @RequestMapping(value = "/ad", method = RequestMethod.POST)
    public AddCampReq createNewAd(@Valid @RequestBody AddCampReq request, BindingResult res) {
        
    	if (LOGGER.isDebugEnabled()){
            LOGGER.debug("POST JSON Request got : "+request);
        }
    	
    	if(res.hasErrors()) {
    		throw new RuntimeException();
    	} else {   	
    		
    		map.add(request);
    		LOGGER.info(request.getPartnerId());    			
    	}
        return request;
    }
    @ResponseBody
    @RequestMapping(value = "/ad/{partner_id}", method = RequestMethod.GET)
    public AdCampaigh getAd(@PathVariable("partner_id") String partnerId, HttpSession session) {
        if (LOGGER.isDebugEnabled()){
            LOGGER.debug("GET Request got : "+partnerId);
        }
        
       
        AdCampaigh adCampaigh = new AdCampaigh();
     //   session.setAttribute("error", "test  lllll");
        try {
        	AddCampReq campReq = map.get(partnerId);  
        	 /* calculate duration */           
            long current = System.currentTimeMillis();
            long dur = campReq.getDuration() * 1000 + map.getTime(partnerId);
            
            if (dur < current) {
            	LOGGER.info("less");
            	map.remove(campReq);          
            	session.invalidate();            	
            	return null;
            } else {
            	LOGGER.info("greater " + dur + " " + current);
            	adCampaigh.setAdContent(campReq.getAdContent());
            	adCampaigh.setPartnerId(partnerId);
            	return adCampaigh;
            }
        	
        } catch (Exception e) {
			// TODO: handle exception
        	throw e;
		}
    }
    
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ArrayList<AddCampReq> getList() {
    	return map.getList();
    }
    
    
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleValidationException(MethodArgumentNotValidException e){
        LOGGER.error(e);
        e.printStackTrace();
        return e.getLocalizedMessage();
    }
    
    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e){
        LOGGER.error(e);
        e.printStackTrace();
        
        return "Something went wrong in the server but we didn't predict it:(";
      
    }
    
    
}
