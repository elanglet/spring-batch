package fr.formation.introspringbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import fr.formation.introspringbatch.steps.ByeTasklet;
import fr.formation.introspringbatch.steps.HelloTasklet;
import fr.formation.introspringbatch.steps.OtherTasklet;

@Configuration
@Import({InfrastructureConfig.class})			// Les Jobs ont besoin de la configuration d'infrastructure de batch
public class JobConfig {

	// On injecte les objets permettant de déclarer les jobs et de les étapes
	
	@Autowired
	private JobBuilderFactory jobs;
	@Autowired
	private StepBuilderFactory steps;
	
	// Déclaration des étapes...
	
	@Bean
	public Step helloStep() {						// Le nom de la méthode est libre...
		return steps.get("helloStep")				// On nomme l'étape...
				.tasklet(new HelloTasklet())		// ...et on l'enregistre
				.build();
	}
	@Bean
	public Step byeStep() {
		return steps.get("byeStep")
				.tasklet(new ByeTasklet())
				.build();
	}
	@Bean
	public Step otherStep() {
		return steps.get("otherStep")
				.tasklet(new OtherTasklet())
				.build();
	}
	
	// Déclaration du job...
	
	@Bean
	public Job helloJob() {
		return jobs.get("helloJob")					// On nomme le job...
				
				.start(helloStep()) 				// ...on le démarre en précisant la première étape.
					.on("COMPLETED").to(byeStep())	// Si 'helloStep' se termine avec ExitStatus = COMPLETED, alors on va vers 'byeStep'
				//.next(byeStep())
					
				.from(helloStep())
					.on("OTHER").to(otherStep())	// Si 'helloStep' se termine avec ExitStatus = OTHER, alors on va vers 'otherStep'
					
				.end()	
					
				.build();
	}	
}











