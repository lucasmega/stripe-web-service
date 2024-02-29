package com.cutconnect;

import com.cutconnect.domains.BarberShop;
import com.cutconnect.domains.Branch;
//import com.cutconnect.domains.Professional;
//import com.cutconnect.domains.Schedule;
import com.cutconnect.repositories.BarberShopRepository;
import com.cutconnect.repositories.BranchRepository;
//import com.cutconnect.repositories.ProfessionalRepository;
//import com.cutconnect.repositories.ScheduleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@EntityScan(basePackages = "com.cutconnect.domains")
public class CutconnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CutconnectApplication.class, args);
	}

	@Bean
	public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
		return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
			@Override
			public void customize(ConfigurableWebServerFactory factory) {
				factory.setPort(4242);
			}
		};
	}


	@Bean
	public CommandLineRunner mockData(
			BarberShopRepository barberShopRepository,
			BranchRepository branchRepository
	) {
		return args -> {

			BarberShop barberShop1 = new BarberShop();
			barberShop1.setName("Barbearia Teste");

			Branch branch1 = new Branch();
			branch1.setName("Filial 1");

			Branch branch2 = new Branch();
			branch2.setName("Filial 2");

			branch1.setBarberShop(barberShop1);
			branch2.setBarberShop(barberShop1);

			barberShopRepository.save(barberShop1);
			branchRepository.saveAll(List.of(branch1, branch2));
		};
	}


}
