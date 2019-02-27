package com.bionworks.ctakewrapper;

import java.util.ArrayList;
import java.util.Map;

public class Sentence {
	private String sentence;
	private Map<String, ArrayList<String>> typeIdentifier;

	public Sentence() {

	}

	public Sentence(String sentence,Map<String, ArrayList<String>> typeIdentifier) {
		this.sentence = sentence;
		this.typeIdentifier = typeIdentifier;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public Map<String, ArrayList<String>> getTypeIdentifier() {
		return typeIdentifier;
	}

	public void setTypeIdentifier(Map<String, ArrayList<String>> typeIdentifier) {
		this.typeIdentifier = typeIdentifier;
	}

}
