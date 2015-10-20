/**
 * 
 */
package com.vobot.thread;

import java.io.File;
import java.util.Date;

import org.apache.log4j.Logger;

import com.vobot.context.SpringContextHolder;
import com.vobot.entity.VoiceText;
import com.vobot.service.SpeechService;
import com.vobot.speech.BaiduSpeech;

/**
 * @author weilin
 *
 */
public class SpeechThread implements Runnable {
	private static final Logger log = Logger.getLogger(SpeechThread.class);
	
	private File file;
	
	public SpeechThread(File file){
		this.file = file;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		BaiduSpeech bs = BaiduSpeech.getInstance();
		String voiceText = bs.speech(file);
		
		log.info("-----Voice Text-----\n" + voiceText);
		
		String fileName = file.getName();
		fileName = fileName.substring(0, fileName.indexOf('.'));
		log.info(fileName);
		
		String[] nameArr = fileName.split("__");
		String name = nameArr[1];
		int seqNo = Integer.valueOf(nameArr[0]);
		
		VoiceText text = new VoiceText();
		text.setFileName(name);
		text.setSeqNo(seqNo);
		text.setContent(voiceText);
		text.setCreateDate(new Date());
		
		SpeechService ss = (SpeechService)SpringContextHolder.getBean("speechService");
		ss.saveVoice(text);
	}

}
