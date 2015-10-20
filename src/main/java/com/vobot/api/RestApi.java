/**
 * 
 */
package com.vobot.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vobot.dto.SpeechIn;
import com.vobot.dto.Status;
import com.vobot.service.SpeechService;

/**
 * @author weilin
 *
 */
@Controller
@RequestMapping(headers="Accept=application/json", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
public class RestApi {
	
	@Autowired
	private SpeechService speechService;
	
	@RequestMapping(value = "/speech/process")
	@ResponseBody
	public Status processSpeech(@RequestBody SpeechIn param,
			HttpServletRequest request) {
		Status status = new Status();
		
		speechService.getVoiceText(param.getDir());
		
		status.setSuccess(true);
		status.setMessage("Process successfully.");
		return status;
	}
}
