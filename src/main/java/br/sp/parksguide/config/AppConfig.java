package br.sp.parksguide.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.sp.parksguide.interceptor.AppInterceptor;



@Configuration
public class AppConfig implements WebMvcConfigurer{
	
	@Autowired
	private AppInterceptor interceptor;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor);
	}
	
	// CONFIGURA A CONEXÃO DA APLICAÇÃO AO BANCO DE DADOS MySQL
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSouce = new DriverManagerDataSource();
		dataSouce.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSouce.setUrl("jdbc:mysql://localhost:3307/parksguide");
		dataSouce.setUsername("root");
		dataSouce.setPassword("root");
		return dataSouce;
	}
	
	// CONFIGURA O HIBERNATE (ORM - MAPEAMENTO OBJETO RELACIONAL)
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQL8Dialect");
		adapter.setShowSql(true);
		adapter.setPrepareConnection(true);
		adapter.setGenerateDdl(true);
		return adapter;
	}
	
}
