package com.kalida.config;

import static com.kalida.model.enums.TypeTechnology.FRAMEWORK;
import static com.kalida.model.enums.TypeTechnology.OTHER;
import static com.kalida.model.enums.TypeTechnology.PROGRAM_LANGUAGE;
import static com.kalida.model.enums.TypeTechnology.SGBD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.kalida.dto.TechnologyDTO;
import com.kalida.dto.UserNewDTO;
import com.kalida.model.Experience;
import com.kalida.model.Notification;
import com.kalida.model.Technology;
import com.kalida.model.enums.TypeLang;
import com.kalida.repository.TechnologyRepository;
import com.kalida.repository.UserRepository;
import com.kalida.security.User;

@Configuration
@Profile("test")
public class ConfigTest {

    @Value("${security.salt}")
    private String salt;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TechnologyRepository tecnologyRepository;

    @Bean
    public void mockSGBD() {
        mockTecnologies();
        mockUsers();
        mockExperiences();
    }

    private void mockUsers() {
        String[] names = {
                "Bia", "Carlos", "Marcos", "Bruna", "Carla", "Maria", "Pedro",
                "Joao", "Maricelia", "Maiara", "Dandara", "Clenilton", "Bianca", "Marcela",
                "Sandra", "Julia", "Saionara", "Gustavo", "Andre", "Ana", "Diego", "Andrew"
        };
        int counter = 1;
        String password = new BCryptPasswordEncoder(16).encode("#0xCUSTOM" + salt);
        List<User> users = new ArrayList<>();

        for (String name : names) {
            String lowerName = name.toLowerCase();
            short lang = counter++ % 3 == 0 ? TypeLang.EN_US.getCod() : TypeLang.PT_BR.getCod();
            UserNewDTO userDTO = new UserNewDTO(lowerName + "_santos", lowerName, name + " Santos", password, lang,
                    lowerName + "@mail.com");
            User user = modelMapper.map(userDTO, User.class);
            user.setPermissions(new ArrayList<>());
            user.setNotifications(new ArrayList<>());
            Notification notification = new Notification("Wellcome to Kalida!");
            notification.setUser(user);
            user.getNotifications().add(notification);
            users.add(user);
        }

        userRepository.saveAll(users);
    }

    private void mockTecnologies() {
        List<TechnologyDTO> technologiesDTO = Arrays.asList(
                new TechnologyDTO("HTML", OTHER),
                new TechnologyDTO("CSS", OTHER),
                new TechnologyDTO("Java", PROGRAM_LANGUAGE),
                new TechnologyDTO("JavaScript", PROGRAM_LANGUAGE),
                new TechnologyDTO("C", PROGRAM_LANGUAGE),
                new TechnologyDTO("C++", PROGRAM_LANGUAGE),
                new TechnologyDTO("C#", PROGRAM_LANGUAGE),
                new TechnologyDTO("Python", PROGRAM_LANGUAGE),
                new TechnologyDTO("PHP", PROGRAM_LANGUAGE),
                new TechnologyDTO("Lua", PROGRAM_LANGUAGE),
                new TechnologyDTO("Groove", PROGRAM_LANGUAGE),
                new TechnologyDTO("VisualBasic", PROGRAM_LANGUAGE),
                new TechnologyDTO("Assembly", PROGRAM_LANGUAGE),
                new TechnologyDTO("SQL", PROGRAM_LANGUAGE),
                new TechnologyDTO("go", PROGRAM_LANGUAGE),
                new TechnologyDTO("R", PROGRAM_LANGUAGE),
                new TechnologyDTO("Objective-C", PROGRAM_LANGUAGE),
                new TechnologyDTO("MATLAB", PROGRAM_LANGUAGE),
                new TechnologyDTO("Swift", PROGRAM_LANGUAGE),
                new TechnologyDTO("Ruby", PROGRAM_LANGUAGE),
                new TechnologyDTO("Delphy", PROGRAM_LANGUAGE),
                new TechnologyDTO("Fortran", PROGRAM_LANGUAGE),
                new TechnologyDTO("Rust", PROGRAM_LANGUAGE),
                new TechnologyDTO("Perl", PROGRAM_LANGUAGE),
                new TechnologyDTO("SAS", PROGRAM_LANGUAGE),
                new TechnologyDTO("Postgres", SGBD),
                new TechnologyDTO("MySql", SGBD),
                new TechnologyDTO("MongoDB", SGBD),
                new TechnologyDTO("Kalida", FRAMEWORK),
                new TechnologyDTO("Flask", FRAMEWORK),
                new TechnologyDTO("Spring", FRAMEWORK));

        technologiesDTO.sort(Comparator.comparing(TechnologyDTO::getName));
        List<Technology> technologies = technologiesDTO.stream()
                .map(technology -> modelMapper.map(technology, Technology.class))
                .collect(Collectors.toList());

        tecnologyRepository.saveAll(technologies);

    }

    private void mockExperiences() {
        User user = userRepository.findByUsername("andrew_santos");
        user.setExperiences(new ArrayList<>());
        List<String> techNames = Arrays.asList("HTML", "CSS", "JavaScript", "PHP", "Python", "SQL", "Postgres", "C#",
                "Lua", "Flask", "Spring");

        List<Technology> technologies = tecnologyRepository.findAll();
        technologies.removeIf(tech -> !techNames.contains(tech.getName()));

        List<Experience> experiences = technologies.stream().map(tech -> {
            Experience experience = new Experience();
            experience.setTechnology(tech);
            experience.setStartYear((short) 2011);
            experience.setUser(user);
            return experience;
        }).collect(Collectors.toList());

        user.getExperiences().addAll(experiences);
        userRepository.save(user);

    }

}
