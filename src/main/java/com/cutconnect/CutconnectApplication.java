package com.cutconnect;

import com.cutconnect.domains.BarberShop;
import com.cutconnect.domains.Branch;
import com.cutconnect.domains.Professional;
import com.cutconnect.domains.Schedule;
import com.cutconnect.repositories.BarberShopRepository;
import com.cutconnect.repositories.BranchRepository;
import com.cutconnect.repositories.ProfessionalRepository;
import com.cutconnect.repositories.ScheduleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
			ProfessionalRepository professionalRepository,
			BarberShopRepository barberShopRepository,
			BranchRepository branchRepository,
			ScheduleRepository scheduleRepository) {
		return args -> {
			// Mock de uma Barbearia
			BarberShop barberShop = new BarberShop();
			barberShop.setName("Barbearia Teste");

			// Mock de Profissionais
			Professional professional1 = new Professional();
			professional1.setName("Profissional 1");
			professional1.setBarberShop(barberShop);

			Professional professional2 = new Professional();
			professional2.setName("Profissional 2");
			professional2.setBarberShop(barberShop);

			List<Professional> professionals = new ArrayList<>();
			professionals.add(professional1);
			professionals.add(professional2);

			barberShop.setProfessionals(professionals);

			// Mock de uma Filial (Branch)
			Branch branch = new Branch();
			branch.setName("Filial 1");
			branch.setBarberShop(barberShop);

			// Mock de um Agendamento (Schedule)
			Schedule schedule = new Schedule();
			schedule.setProfessional(professional1);
			schedule.setBranch(branch);
			schedule.setDateTime(LocalDateTime.now().plusDays(1));

			// Salvando no banco de dados
			barberShopRepository.save(barberShop);
			branchRepository.save(branch);
			professionalRepository.saveAll(professionals);
			scheduleRepository.save(schedule);
		};
	}


}
