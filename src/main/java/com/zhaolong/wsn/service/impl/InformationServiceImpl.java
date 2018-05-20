package com.zhaolong.wsn.service.impl;

import java.util.List;

import javax.sound.midi.MidiDevice.Info;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhaolong.wsn.entity.Information;
import com.zhaolong.wsn.repository.InformationRepository;
import com.zhaolong.wsn.service.InformationService;

public class InformationServiceImpl implements InformationService{

	@Autowired
    private InformationRepository informationRepository;
	
	public List<Information> informationList() {
		// TODO Auto-generated method stub
		return informationRepository.findAll();
	}

	public Information getDetails(Long newsId) {
		return informationRepository.get(newsId);
	}

	public Long saveInformation(Information information) {
		// TODO Auto-generated method stub
		return informationRepository.save(information);
	}

	public void updateInformation(Information information) {
		informationRepository.saveOrUpdate(information);
	}

	public void deleteInformation(Long newsId) {
		informationRepository.delete(newsId);
	}

}
