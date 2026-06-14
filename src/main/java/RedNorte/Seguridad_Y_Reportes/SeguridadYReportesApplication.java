package RedNorte.Seguridad_Y_Reportes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"RedNorte.Seguridad_Y_Reportes", "com.atencion"})
@EntityScan(basePackages = {"RedNorte.Seguridad_Y_Reportes", "com.atencion"})
@EnableJpaRepositories(basePackages = {"RedNorte.Seguridad_Y_Reportes", "com.atencion"})
public class SeguridadYReportesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeguridadYReportesApplication.class, args);
    }
}