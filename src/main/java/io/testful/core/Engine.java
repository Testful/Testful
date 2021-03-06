package io.testful.core;

import static io.testful.core.util.Const.ENDPOINT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.Unirest;

import io.testful.core.validator.FastestResult;
import io.testful.core.validator.ResponseValidator;

public class Engine {

	private static final Logger log = LoggerFactory.getLogger(Engine.class);
	
	private List<ExecutionConfiguration> execConfigList = new ArrayList<>();
	
	public void registerExecConfig(ExecutionConfiguration execConfig) {
		
		log.info("Registering execution configuration for: " + execConfig.getRawEndpoint());
		
		execConfigList.add(execConfig);
		
	}
	
	public void execute() {
		
		log.trace("Executing ...");
		
		final List<FastestResult> listResults = new ArrayList<>();
		
		execConfigList.forEach(execConf -> {
			
			String endpoint = execConf.getRequestSpecification().getString(ENDPOINT);
			
			log.info("executing {}", endpoint);
			
			Request requestBuilder = Request.Builder.fromExecConfig(execConf);

			FastestResponse response = requestBuilder.execute();
			
			//System.out.println(response.getBody());
			
			FastestResult result = ResponseValidator.validate(response, execConf.getResponseValidation());
			
			listResults.add(result);

		});
		
	}
	
	public void exit() {
		
		try {
			Unirest.shutdown();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		
	}
	
}
