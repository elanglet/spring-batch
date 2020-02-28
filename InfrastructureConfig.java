package fr.formation.springbatchlte.config;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration												// Déclare cette classe comme classe de configuration
@EnableBatchProcessing										// Ajoute la configuration de base Spring Batch
@PropertySource("classpath:/application.properties")		// Permet de charger le fichier .properties
public class InfrastructureConfig {

	// Objet permettant d'accéder au contenu du fichier .properties
	@Autowired
	private Environment env;
	
	
	// Création de la dataSource pour le JobRepo
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("infra.jdbc.driver"));
		dataSource.setUrl(env.getProperty("infra.jdbc.url"));
		dataSource.setUsername(env.getProperty("infra.jdbc.username"));
		dataSource.setPassword(env.getProperty("infra.jdbc.password"));
		
		return dataSource;
	}
}
