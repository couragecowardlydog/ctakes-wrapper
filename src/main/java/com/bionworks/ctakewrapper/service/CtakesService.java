package com.bionworks.ctakewrapper.service;

import java.io.StringWriter;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ctakes.typesystem.type.textspan.Sentence;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.json.JsonCasSerializer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import com.bionworks.ctakewrapper.Parser;
import com.bionworks.ctakewrapper.beans.CTakeResponse;
import com.bionworks.ctakewrapper.beans.ParsedIdentifiedAnnotation;
import com.bionworks.ctakewrapper.beans.Pipeline;

@Service
public class CtakesService {

	private Pipeline pipeline;
	private AnalysisEngine engine;
	private Parser parser;

	public final JCas jcas;

	public CtakesService() throws UIMAException, MalformedURLException {
		jcas = JCasFactory.createJCas();
		this.pipeline = new Pipeline();
		this.engine = this.pipeline.addAnalysisEngine("FAST").createAggregate();
		this.parser = new Parser();

	}

	public CTakeResponse getResponse(String note) {

		StringWriter sw = new StringWriter();
		JsonCasSerializer jcs = new JsonCasSerializer();
		CTakeResponse ctakeresponse = new CTakeResponse();
		this.jcas.reset();
		this.jcas.setDocumentText(note);
		jcs.setPrettyPrint(true);

		try {
			this.engine.process(this.jcas);
			Collection<Sentence> sentences = JCasUtil.select(jcas, Sentence.class);
			
			// Sentence from pipeline
			for (Iterator<Sentence> iterator = sentences.iterator(); iterator.hasNext();) {
				Sentence sentence = (Sentence) iterator.next();
				System.out.println(sentence.getCoveredText());
				ctakeresponse.addAnnotations(this.parser.parseSentence(jcas, sentence));
				// Localized sentence
				com.bionworks.ctakewrapper.Sentence _sentence = new com.bionworks.ctakewrapper.Sentence(
						sentence.getCoveredText(), this.parser.analyzeTUI(this.parser.parseSentence(jcas, sentence)));
				ctakeresponse.addSentences(_sentence);
			}
			
			CAS cas = jcas.getCas();
			jcs.serialize(cas, sw);
			ctakeresponse.setMentions(getMentions(sw.toString(), note));
			ctakeresponse.setProcessedYN("Y");
			ctakeresponse.setError(null);
			ctakeresponse.setSourceNote(note);
			ctakeresponse.setTypeIdentifier(this.parser.analyzeTUI(ctakeresponse.getAnnotations()));
		} catch (Exception e) {
			ctakeresponse.setProcessedYN("N");
			ctakeresponse.setError(e.getLocalizedMessage());
		}

		return ctakeresponse;

	}

	public JSONObject getMentions(String ctakes, String note) {
		JSONParser jsonParser = new JSONParser();
		JSONObject obj = new JSONObject();
		try {
			obj = (JSONObject) jsonParser.parse(ctakes);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		obj = (JSONObject) obj.get("_views");
		obj = (JSONObject) obj.get("_InitialView");
		JSONArray MedicationMention = (JSONArray) obj.get("MedicationMention");
		JSONArray AnatomicalSiteMention = (JSONArray) obj.get("AnatomicalSiteMention");
		JSONArray DiseaseDisorderMention = (JSONArray) obj.get("DiseaseDisorderMention");
		JSONArray SignSymptomMention = (JSONArray) obj.get("SignSymptomMention");
		JSONArray ProcedureMention = (JSONArray) obj.get("ProcedureMention");
		JSONArray WordToken = (JSONArray) obj.get("WordToken");

		JSONObject output = new JSONObject();
		output.put("MedicationMention", parseJsonMention(note, WordToken, MedicationMention));
		output.put("AnatomicalSiteMention", parseJsonMention(note, WordToken, AnatomicalSiteMention));
		output.put("DiseaseDisorderMention", parseJsonMention(note, WordToken, DiseaseDisorderMention));
		output.put("SignSymptomMention", parseJsonMention(note, WordToken, SignSymptomMention));
		output.put("ProcedureMention", parseJsonMention(note, WordToken, ProcedureMention));
		output.put("Original", obj);
		return output;
	}

	private JSONArray parseJsonMention(String document, JSONArray wordtoken, JSONArray jsonArray) {

		JSONArray output = new JSONArray();
		if (null == wordtoken || null == jsonArray)
			return output;
		for (int i = 0, size = jsonArray.size(); i < size; i++) {
			JSONObject objectInArray = (JSONObject) jsonArray.get(i);
			long begin = (long) objectInArray.get("begin");
			long end = (long) objectInArray.get("end");
			String original_word = document.substring((int) begin, (int) end);
			String canonical_form = "";
			for (int i2 = 0, size2 = wordtoken.size(); i2 < size2; i2++) {
				JSONObject tokenInArray = (JSONObject) wordtoken.get(i2);
				long begin2 = (long) tokenInArray.get("begin");
				long end2 = (long) tokenInArray.get("end");
				if (begin == begin2 && end == end2)
					canonical_form = (String) tokenInArray.get("canonicalForm");
			}
			objectInArray.put("originalWord", original_word);
			objectInArray.put("canonicalForm", canonical_form);
			output.add(objectInArray);
		}
		return output;
	}

}
