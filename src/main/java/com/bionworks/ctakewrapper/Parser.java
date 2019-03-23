package com.bionworks.ctakewrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.ctakes.core.cc.pretty.SemanticGroup;
import org.apache.ctakes.core.cc.pretty.textspan.DefaultTextSpan;
import org.apache.ctakes.core.cc.pretty.textspan.TextSpan;
import org.apache.ctakes.core.util.OntologyConceptUtil;
import org.apache.ctakes.typesystem.type.refsem.UmlsConcept;
import org.apache.ctakes.typesystem.type.textsem.IdentifiedAnnotation;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import com.bionworks.ctakewrapper.beans.ParsedIdentifiedAnnotation;
import com.bionworks.ctakewrapper.beans.ParsedUMLSConcepts;

/**
 * 
 * @author Vivekananda S
 * @version 0.0.1
 * @category Identified Annotation Parser { @literal UMLS - Unified Medical
 *           Language System } The UMLS, or Unified Medical Language System, is
 *           a set of files and software that brings together many health and
 *           biomedical vocabularies and standards to enable interoperability
 *           between computer systems.
 *
 */

public class Parser {

	public Parser() {

	}
	
	
	
	public Map<String,ArrayList<String>>  analyzeTUI(final ArrayList<ParsedIdentifiedAnnotation> annotations) {
		Map<String,ArrayList<String>> tui = new HashMap<String,ArrayList<String>>();
	
		for (int counter = 0; counter < annotations.size(); counter++) { 	
			
	          ParsedIdentifiedAnnotation annotation = annotations.get(counter);
	          ArrayList<ParsedUMLSConcepts> umls = annotation.getUmls();
	          for (Iterator<ParsedUMLSConcepts> iterator = umls.iterator(); iterator.hasNext();) {
				ParsedUMLSConcepts parsedUMLSConcepts = (ParsedUMLSConcepts) iterator.next();
				ArrayList<String> significants = tui.get(parsedUMLSConcepts.getSemantiName());
				if(null==significants)
					significants = new ArrayList<String>();
				significants.add(annotation.getText());
				tui.put(parsedUMLSConcepts.getSemantiName(),significants);
			}
	      }  
		return tui;
	}

	/**
	 * *
	 * 
	 * @param jcas     Processed Document
	 * @param sentence A sentence from processer pipeline
	 */
	public  ArrayList<ParsedIdentifiedAnnotation> parseSentence(final JCas jcas, final AnnotationFS sentence) {
		final int sentenceBegin = sentence.getBegin();
		final Collection<IdentifiedAnnotation> identifiedAnnotations = JCasUtil.selectCovered(jcas,
				IdentifiedAnnotation.class, sentence);
		
		
		final Map<String, ParsedIdentifiedAnnotation> coveringAnnotationMap = new HashMap<String, ParsedIdentifiedAnnotation>();
		/**
		 * Looping over Identified annotation , to remove duplicates if any and to find
		 * CUI, TUI and sematic
		 */
		for (final IdentifiedAnnotation annotation : identifiedAnnotations) {
			annotation.getCAS().toString();
			ParsedIdentifiedAnnotation _annotation = new ParsedIdentifiedAnnotation();
			final TextSpan textSpan = new DefaultTextSpan(annotation, sentenceBegin);
			
			// Add attributes to response here
			_annotation.setUmls(getUMLConcept(annotation));
			_annotation.setBegin(annotation.getBegin());
			_annotation.setEnd(annotation.getEnd());
			_annotation.setText(annotation.getCoveredText());
	
			coveringAnnotationMap.put(textSpan.toString(), _annotation);

		}
		return new ArrayList<ParsedIdentifiedAnnotation>(coveringAnnotationMap.values());

	}

	/***
	 * 
	 * Concepts in the Unified Medical Language System (UMLS) Metathesaurus have a
	 * concept unique identifier (CUI) and a type unique identifier (TUI, i.e.,
	 * semantic type) which are curated, normalized codes. For example, "pain" would
	 * have a cui=C0030193 and tui=T184. Equivalent to cTAKES:
	 * edu.mayo.bmi.uima.core.type.UmlsConcept
	 */
	private ArrayList<ParsedUMLSConcepts> getUMLConcept(final IdentifiedAnnotation identifiedAnnotation) {

		Map<String, ParsedUMLSConcepts> umls = new HashMap<String, ParsedUMLSConcepts>();
		final Collection<UmlsConcept> umlsConcepts = OntologyConceptUtil.getUmlsConcepts(identifiedAnnotation);
		for (Iterator<UmlsConcept> iterator = umlsConcepts.iterator(); iterator.hasNext();) {
			UmlsConcept umlsConcept = (UmlsConcept) iterator.next();
			final String CUI = trimTo8(umlsConcept.getCui());
			final String TUI = umlsConcept.getTui();
			final String codingScheme =  umlsConcept.getCodingScheme();
			final String code = umlsConcept.getCode();
			final String sematicName = SemanticGroup.getSemanticName(TUI);
			umls.put(CUI, new ParsedUMLSConcepts(CUI, TUI, sematicName,codingScheme,code));

		}
		return new ArrayList<ParsedUMLSConcepts>(umls.values());
	}

	 private String trimTo8(final String text) {
		if (text.length() <= 8) {
			return text;
		}
		return "<" + text.substring(text.length() - 7, text.length());
	}
	 
	 
	 

}
