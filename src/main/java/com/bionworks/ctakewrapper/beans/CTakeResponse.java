package com.bionworks.ctakewrapper.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import com.bionworks.ctakewrapper.Sentence;

public class CTakeResponse {
	
	private String processedYN;
	private String sourceNote;
	private String error;
	private ArrayList<ParsedIdentifiedAnnotation> annotations;
	private JSONObject mentions;
	private Map<String,ArrayList<String>> typeIdentifier;
	private ArrayList<Sentence> sentence;
	
	public CTakeResponse(){
		this.annotations = new ArrayList<ParsedIdentifiedAnnotation>();
		this.typeIdentifier = new HashMap<String,ArrayList<String>>();
		this.sentence = new ArrayList<Sentence>();
		this.processedYN = "N";
	}

	public ArrayList<ParsedIdentifiedAnnotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(ArrayList<ParsedIdentifiedAnnotation> annotations) {
		this.annotations = annotations;
	}
	
	public void addAnnotations(ArrayList<ParsedIdentifiedAnnotation> annotations) {
		this.annotations.addAll(annotations);
	}

	public String getProcessedYN() {
		return processedYN;
	}

	public void setProcessedYN(String processedYN) {
		this.processedYN = processedYN;
	}

	public String getSourceNote() {
		return sourceNote;
	}

	public void setSourceNote(String sourceNote) {
		this.sourceNote = sourceNote;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public JSONObject getMentions() {
		return mentions;
	}

	public void setMentions(JSONObject mentions) {
		this.mentions = mentions;
	}

	public Map<String, ArrayList<String>> getTypeIdentifier() {
		return typeIdentifier;
	}

	public void setTypeIdentifier(Map<String, ArrayList<String>> typeIdentifier) {
		this.typeIdentifier = typeIdentifier;
	}

	public ArrayList<Sentence> getSentences() {
		return sentence;
	}

	public void setSentences(ArrayList<Sentence> sentences) {
		this.sentence = sentences;
	}
	
	
	public void addSentences(Sentence sentence) {
		this.sentence.add(sentence);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
