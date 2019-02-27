package com.bionworks.ctakewrapper.beans;

import java.util.ArrayList;
import com.bionworks.ctakewrapper.beans.ParsedUMLSConcepts;
public class ParsedIdentifiedAnnotation {
	
	private String text;
	private int begin;
	private int end;
	private ArrayList<ParsedUMLSConcepts> umls;
	
	public ParsedIdentifiedAnnotation(){
		this.umls = new ArrayList<ParsedUMLSConcepts>();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public ArrayList<ParsedUMLSConcepts> getUmls() {
		return umls;
	}

	public void setUmls(ArrayList<ParsedUMLSConcepts> umls) {
		this.umls = umls;
	}
	
	

}
