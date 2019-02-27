package com.bionworks.ctakewrapper.beans;

public class ParsedUMLSConcepts {
	String conceptUniqueIdentifier;
	String typeUniqueIdentifier;
	String semantiName;
	
	ParsedUMLSConcepts(){
		
	}
	
	public ParsedUMLSConcepts(String concept,String type,String sematic){
		this.conceptUniqueIdentifier = concept;
		this.typeUniqueIdentifier = type;
		this.semantiName = sematic;
	}

	public String getConceptUniqueIdentifier() {
		return conceptUniqueIdentifier;
	}

	public void setConceptUniqueIdentifier(String conceptUniqueIdentifier) {
		this.conceptUniqueIdentifier = conceptUniqueIdentifier;
	}

	public String getTypeUniqueIdentifier() {
		return typeUniqueIdentifier;
	}

	public void setTypeUniqueIdentifier(String typeUniqueIdentifier) {
		this.typeUniqueIdentifier = typeUniqueIdentifier;
	}

	public String getSemantiName() {
		return semantiName;
	}

	public void setSemantiName(String semantiName) {
		this.semantiName = semantiName;
	}
	
	
}
