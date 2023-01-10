package com.example;

import com.redis.om.spring.annotations.EnableRedisDocumentRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.geo.Point;

import java.util.Optional;
import java.util.Set;

@SpringBootApplication
@EnableRedisDocumentRepositories
public class Issue145Application {

	@Autowired
	CompanyRepository companyRepo;

	@Bean CommandLineRunner loadTestData() {
		return args -> {
			companyRepo.deleteAll();
			Company redis = Company.of("Redis", "https://redis.com", new Point(-122.066540, 37.377690), 526, 2011);
			redis.setTags(Set.of("fast", "scalable", "reliable"));

			companyRepo.save(redis);

			Optional<Company> redis1 = companyRepo.findOneByName("Redis");
			System.out.println("redis1 = " + redis1);

			Iterable<Company> redis2 = companyRepo.findAllByName("Redis");
			System.out.println("redis2 = " + redis2);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(Issue145Application.class, args);
	}

}
