package com.joony.muvirec.config;

import org.apache.commons.text.StringEscapeUtils;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;

/**
 * @author imjju
 * XSS를 방지할 특수문자 지정 클래스
 */
@SuppressWarnings("serial")
public class XssProtectSupport extends CharacterEscapes {
		private final int[] asciiEscapes;

	    public XssProtectSupport() {
	    	//XSS를 방지할 특수문자를 지정해준다.
	        asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();
	        asciiEscapes['<'] = CharacterEscapes.ESCAPE_CUSTOM;
	        asciiEscapes['>'] = CharacterEscapes.ESCAPE_CUSTOM;
	        asciiEscapes['\"'] = CharacterEscapes.ESCAPE_CUSTOM;
	        asciiEscapes['('] = CharacterEscapes.ESCAPE_CUSTOM;
	        asciiEscapes[')'] = CharacterEscapes.ESCAPE_CUSTOM;
	        asciiEscapes['#'] = CharacterEscapes.ESCAPE_CUSTOM;
	        asciiEscapes['\''] = CharacterEscapes.ESCAPE_CUSTOM;
	    }

	    @Override
	    public int[] getEscapeCodesForAscii() {
	        return asciiEscapes;
	    }//getEscapeCodesForAscii

	    @Override
	    public SerializableString getEscapeSequence(int ch) {
	        //커스터마이징을 하지 않은 경우
	    	return new SerializedString(StringEscapeUtils.escapeHtml4(Character.toString((char) ch)));
	    	
	    }//getEscapeSequence


}//class
