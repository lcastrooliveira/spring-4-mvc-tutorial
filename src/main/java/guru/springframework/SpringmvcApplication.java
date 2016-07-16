package guru.springframework;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringmvcApplication {

	public static void main(String[] args) {
//		ApplicationContext ctx = 
		SpringApplication.run(SpringmvcApplication.class, args);
//		System.out.println("Beans ********");
//		System.out.println(ctx.getBeanDefinitionCount());
//		for(String name : ctx.getBeanDefinitionNames()) {
//			System.out.println(name);
//		}
	}
}
