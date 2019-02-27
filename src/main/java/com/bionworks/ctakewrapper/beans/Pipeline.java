package com.bionworks.ctakewrapper.beans;

import static org.apache.ctakes.dictionary.lookup2.ae.JCasTermAnnotator.DICTIONARY_DESCRIPTOR_KEY;

import java.net.MalformedURLException;

import org.apache.ctakes.assertion.medfacts.cleartk.ConditionalCleartkAnalysisEngine;
import org.apache.ctakes.assertion.medfacts.cleartk.GenericCleartkAnalysisEngine;
import org.apache.ctakes.assertion.medfacts.cleartk.HistoryCleartkAnalysisEngine;
import org.apache.ctakes.assertion.medfacts.cleartk.PolarityCleartkAnalysisEngine;
import org.apache.ctakes.assertion.medfacts.cleartk.SubjectCleartkAnalysisEngine;
import org.apache.ctakes.assertion.medfacts.cleartk.UncertaintyCleartkAnalysisEngine;
import org.apache.ctakes.clinicalpipeline.ClinicalPipelineFactory;
import org.apache.ctakes.dictionary.lookup2.ae.AbstractJCasTermAnnotator;
import org.apache.ctakes.dictionary.lookup2.ae.DefaultJCasTermAnnotator;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.AggregateBuilder;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.resource.ResourceInitializationException;

public class Pipeline {

	private AggregateBuilder builder;

	public Pipeline() throws ResourceInitializationException, MalformedURLException {
		this.builder = new AggregateBuilder();
		builder.add(AnalysisEngineFactory.createEngineDescription(DefaultJCasTermAnnotator.class,
				AbstractJCasTermAnnotator.PARAM_WINDOW_ANNOT_KEY, "org.apache.ctakes.typesystem.type.textspan.Sentence",
				DICTIONARY_DESCRIPTOR_KEY, "org/apache/ctakes/dictionary/lookup/fast/sno_rx_16ab.xml"));

	}
	
	public AggregateBuilder getBuilder() {
		return this.builder;
	}

	public AggregateBuilder addAnalysisEngine(String pipeline) throws ResourceInitializationException, MalformedURLException {
		AnalysisEngineDescription aed = null;
		switch (pipeline) {
		case "FAST":
			aed = ClinicalPipelineFactory.getFastPipeline();
			break;
		case "DEFAULT":
			aed = ClinicalPipelineFactory.getDefaultPipeline();
			break;
		case "NP":
			aed = ClinicalPipelineFactory.getNpChunkerPipeline();
			break;
		case "COREF":
			aed = ClinicalPipelineFactory.getCoreferencePipeline();
			break;
		case "PARSE":
			aed = ClinicalPipelineFactory.getParsingPipeline();
			break;
		case "CHUNK":
			aed = ClinicalPipelineFactory.getStandardChunkAdjusterAnnotator();
			break;
		case "TOKEN":
			aed = ClinicalPipelineFactory.getTokenProcessingPipeline();
			break;
		case "POLARITY":
			aed = PolarityCleartkAnalysisEngine.createAnnotatorDescription();
			break;
		case "UNCERTAIN":
			aed = UncertaintyCleartkAnalysisEngine.createAnnotatorDescription();
			break;
		case "HISTORY":
			HistoryCleartkAnalysisEngine.createAnnotatorDescription();
			break;
		case "CONDITIONAL":
			ConditionalCleartkAnalysisEngine.createAnnotatorDescription();
			break;
		case "GENERIC":
			GenericCleartkAnalysisEngine.createAnnotatorDescription();
			break;
		case "SUBJECT":
			SubjectCleartkAnalysisEngine.createAnnotatorDescription();
			break;
		default:
			aed = ClinicalPipelineFactory.getTokenProcessingPipeline();
		}

		this.builder.add(aed);
		return this.builder;
	}

}
