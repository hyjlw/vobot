/**
 * 
 */
package com.vobot.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vobot.dao.VoiceDao;
import com.vobot.entity.VoiceText;
import com.vobot.thread.SpeechThread;
import com.vobot.util.PropertiesReader;

/**
 * @author weilin
 *
 */
@Service
public class SpeechService {
	
	@Autowired
	private VoiceDao voiceDao;
	
	public void getVoiceText(String fileDir){
		String dirPath = PropertiesReader.getInstnace().getBaseDir() + fileDir;
		File dir = new File(dirPath);
		
		if(!dir.exists()){
			return;
		}
		
		File[] files = dir.listFiles();
		for(File file : files){
			Thread thread = new Thread(new SpeechThread(file));
			
			thread.start();
//			try {
//				thread.join();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
	}
	
	@Transactional
	public void saveVoice(VoiceText voiceText){
		voiceDao.save(voiceText);
	}
}
