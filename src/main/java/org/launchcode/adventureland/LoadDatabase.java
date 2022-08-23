//package org.launchcode.adventureland;
//
//import org.launchcode.adventureland.models.Equipment;
//import org.launchcode.adventureland.models.data.EquipmentRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class LoadDatabase {
//    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
//
//    @Bean
//    CommandLineRunner initDatabase(EquipmentRepository repository) {
//
//        return args -> {
//
//            log.info("preloading" + repository.save(new Equipment("Water Gear", "Kayak", "Innova", 20, 50)));
//            log.info("preloading" + repository.save(new Equipment("Water Gear", "Canoe", "PakCanoes", 20, 65)));
//            log.info("preloading" + repository.save(new Equipment("Snow Gear", "Skis", "K2", 20, 65)));
//            log.info("preloading" + repository.save(new Equipment("Snow Gear", "Snowboard", "Burton", 20, 65)));
//            log.info("preloading" + repository.save(new Equipment("Camping Gear", "Tent", "Ozark Outdoors", 20, 45)));
//            log.info("preloading" + repository.save(new Equipment("Camping Gear", "Stove", "Coleman", 20, 40)));
//            log.info("preloading" + repository.save(new Equipment("Climbing Gear", "Climbing Shoes", "Black Diamond", 20, 25)));
//            log.info("preloading" + repository.save(new Equipment("Climbing Gear", "Harness", "Black Diamond", 20, 20)));
//        };
//    }
//}