package com.guney.component;

import com.guney.component.dao.ApplicationDao;
import com.guney.component.models.CollegeStudent;
import com.guney.component.service.ApplicationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class MvcTestingExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MvcTestingExampleApplication.class, args);
    }

    /* New for Section 2.2 */
    @Bean(name = "applicationExample")
    ApplicationService getApplicationService() {
        return new ApplicationService();
    }

    /* New for Section 2.2 */
    @Bean(name = "applicationDao")
    ApplicationDao getApplicationDao() {
        return new ApplicationDao();
    }

    @Bean(name = "collegeStudent")
    @Scope(value = "prototype")
    CollegeStudent getCollegeStudent() {
        return new CollegeStudent();
    }

}
