package com.zhaolong.wsn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhaolong.wsn.entity.Event;
import com.zhaolong.wsn.repository.EventRepository;
import com.zhaolong.wsn.service.EventService;

public class EventServiceImpl implements EventService {

	@Autowired
    private EventRepository eventRepository;
	
	public Long addEvent(Event event) {
		// TODO Auto-generated method stub
		return eventRepository.save(event);
	}

}
