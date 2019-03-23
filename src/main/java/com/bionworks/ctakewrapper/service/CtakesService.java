package com.bionworks.ctakewrapper.service;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.ctakes.typesystem.type.textspan.Sentence;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.springframework.stereotype.Service;

import com.bionworks.ctakewrapper.Parser;
import com.bionworks.ctakewrapper.beans.CTakeResponse;
import com.bionworks.ctakewrapper.beans.Pipeline;

@Service
public class CtakesService {

	private Pipeline pipeline;
	private AnalysisEngine engine;
	private Parser parser;

	public JCas jcas;

	public CtakesService() throws UIMAException, MalformedURLException, FileNotFoundException {
		jcas = null;
		try {
		jcas = JCasFactory.createJCas();
		this.pipeline = new Pipeline();
		this.engine = this.pipeline.addAnalysisEngine("FAST").createAggregate();
		this.parser = new Parser();
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error"+e.getLocalizedMessage());
		}

	}

	public CTakeResponse getResponse(String note) {
		CTakeResponse ctakeresponse = new CTakeResponse();
		this.jcas.reset();
		this.jcas.setDocumentText(note);
		try {
			this.engine.process(this.jcas);
			Collection<Sentence> sentences = JCasUtil.select(jcas, Sentence.class);
			
			// Sentence from pipeline
			for (Iterator<Sentence> iterator = sentences.iterator(); iterator.hasNext();) {
				Sentence sentence = (Sentence) iterator.next();
				ctakeresponse.addAnnotations(this.parser.parseSentence(jcas, sentence));
				// Localized sentence
				com.bionworks.ctakewrapper.Sentence _sentence = new com.bionworks.ctakewrapper.Sentence(
						sentence.getCoveredText(), this.parser.analyzeTUI(this.parser.parseSentence(jcas, sentence)));
				ctakeresponse.addSentences(_sentence);
			}
			ctakeresponse.setProcessedYN("Y");
			ctakeresponse.setError(null);
			ctakeresponse.setSourceNote(note);
		} catch (Exception e) {
			ctakeresponse.setProcessedYN("N");
			ctakeresponse.setError(e.getLocalizedMessage());
		}

		return ctakeresponse;

	}




}
