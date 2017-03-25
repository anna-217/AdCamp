package com.adCampaigh.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitterReturnValueHandler;

import com.adCampaigh.DTO.AddCampReq;

@Service
public class MapDB {
	public HashMap<String, AddCampReq> map = new HashMap<>();
	public HashMap<String , Long> mapTime = new HashMap<>();
	
	public void add(AddCampReq req) {
		map.put(req.getPartnerId(), req);
		mapTime.put(req.getPartnerId(), System.currentTimeMillis());
	}
	
	public AddCampReq get(String id) {
		return map.get(id);
	}
	
	public long getTime (String id) {
		return mapTime.get(id);
	}
	
	public void remove(AddCampReq req) {
		map.remove(req.getPartnerId());
		mapTime.remove(req.getPartnerId());
	}
	
	public ArrayList<AddCampReq> getList() {
	//	if (map.isEmpty())
	//		return null;
		
		ArrayList<AddCampReq> list = new ArrayList<>();
    	for (String key : map.keySet()) {
    		list.add(map.get(key));
    	}
    	
    	return list;
	}
}
