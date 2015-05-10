package com.jcertif.offlinebox.config;  
  
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
 
/**
 * <p>Configuration of web application</p>
 *
 * @author Firas GABSI
 * @since 1.0
 */

@Configuration 
@ComponentScan("com.jcertif.offlinebox.rs") 
@EnableWebMvc   
public class AppConfig {  

}  
