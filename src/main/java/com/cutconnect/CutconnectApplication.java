package com.cutconnect;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

import java.util.Random;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;

import com.cutconnect.domains.*;
import com.cutconnect.repositories.*;

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
			BranchRepository branchRepository,
			ProfessionalRepository professionalRepository,
			ScheduleRepository scheduleRepository,
			AddressRepository addressRepository
	) {
		return args -> {

			BarberShop barberShop1 = new BarberShop();
			barberShop1.setName("Barbearia Teste");

			Address address1 = new Address();
			address1.setStreet("Rua Carlos Magalhães");
			address1.setNumber("60");
			address1.setDistrict("Parque Rebouças");
			address1.setCity("São Paulo");
			address1.setState("São Paulo");
			address1.setZipCode("04852050");

			Address address2 = new Address();
			address2.setStreet("Rua Carlos Magalhães");
			address2.setNumber("60");
			address2.setDistrict("Parque Rebouças");
			address2.setCity("São Paulo");
			address2.setState("São Paulo");
			address2.setZipCode("04852050");

			Branch branch1 = new Branch();
			branch1.setName("Filial 1");

			Branch branch2 = new Branch();
			branch2.setName("Filial 2");

			branch1.setBarberShop(barberShop1);
			branch2.setBarberShop(barberShop1);
			branch1.setAddress(address1);
			branch2.setAddress(address2);

			Professional professional1 = new Professional();
			professional1.setName("João Pereira");
			professional1.setPosition("Barbeiro");
			professional1.setBranch(branch1);

			Professional professional2 = new Professional();
			professional2.setName("Felipe Silva");
			professional2.setPosition("Trancista");
			professional2.setBranch(branch2);

			Schedule schedule1 = new Schedule();
			schedule1.setDate(LocalDate.of(2024, 03, 11));
			schedule1.setTime(LocalTime.of(9, 00, 00));
			schedule1.setProfessional(professional1);

			Schedule schedule2 = new Schedule();
			schedule2.setDate(LocalDate.now());
			schedule2.setTime(LocalTime.now());
			schedule2.setProfessional(professional2);

			Schedule schedule3 = new Schedule();
			schedule3.setDate(LocalDate.of(2024, 03, 11));
			schedule3.setTime(LocalTime.of(9, 00, 00));
			schedule3.setProfessional(professional2);

			Schedule schedule4 = new Schedule();
			schedule4.setDate(generateRandomFutureDate(new Random()));
			schedule4.setTime(generateRandomTime(new Random()));
			schedule4.setProfessional(professional2);

			barberShopRepository.save(barberShop1);
			addressRepository.saveAll(List.of(address1, address2));
			branchRepository.saveAll(List.of(branch1, branch2));
			professionalRepository.saveAll(List.of(professional1, professional2));
			scheduleRepository.saveAll(List.of(schedule1, schedule2, schedule3, schedule4));
		};
	}

	private static LocalTime generateRandomTime(Random random) {
		int hour = random.nextInt(24);
		int minute = random.nextInt(60);

		return LocalTime.of(hour, minute);
	}

	private static LocalDate generateRandomFutureDate(Random random) {
		int daysInFuture = random.nextInt(365);

		LocalDate currentDate = LocalDate.now();

		return currentDate.plusDays(daysInFuture);
	}

}
