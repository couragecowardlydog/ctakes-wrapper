package com.bionworks.ctakewrapper.beans;

public class ParsedUMLSConcepts {
	String conceptUniqueIdentifier;
	String typeUniqueIdentifier;
	String semantiName;
	String codingScheme;
	String code;
	
	ParsedUMLSConcepts(){
		
	}
	
	public ParsedUMLSConcepts(String concept,String type,String sematic){
		this.conceptUniqueIdentifier = concept;
		this.typeUniqueIdentifier = type;
		this.semantiName = sematic;
	}
	
	

	public ParsedUMLSConcepts(String conceptUniqueIdentifier, String typeUniqueIdentifier, String semantiName,
			String codingScheme, String code) {
		super();
		this.conceptUniqueIdentifier = conceptUniqueIdentifier;
		this.typeUniqueIdentifier = typeUniqueIdentifier;
		this.semantiName = semantiName;
		this.codingScheme = codingScheme;
		this.code = code;
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

	public String getCodingScheme() {
		return codingScheme;
	}

	public void setCodingScheme(String codingScheme) {
		this.codingScheme = codingScheme;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	

	
	
	
	
}
