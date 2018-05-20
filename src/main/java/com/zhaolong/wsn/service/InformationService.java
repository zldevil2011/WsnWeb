package com.zhaolong.wsn.service;

import java.util.List;

import javax.sound.midi.MidiDevice.Info;

import com.zhaolong.wsn.entity.Information;

public interface InformationService {
	List<Information> informationList();
	Information getDetails(Long newsId);
	Long saveInformation(Information information);
	void updateInformation(Information information);
	void deleteInformation(Long newsId);
}
