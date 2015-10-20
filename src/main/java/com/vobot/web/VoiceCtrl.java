/**
 * 
 */
package com.vobot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author weilin
 *
 */

@Controller
public class VoiceCtrl {

	@RequestMapping(value = "/voice", method = RequestMethod.GET)
	public String getVoice() {
		return "voice.html";
	}
}
