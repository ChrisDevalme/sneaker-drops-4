package com.pluralsight.sneakerdrops;

import com.pluralsight.sneakerdrops.data.BrandRepository;
import com.pluralsight.sneakerdrops.data.SneakerRepository;
import com.pluralsight.sneakerdrops.models.Brand;
import com.pluralsight.sneakerdrops.models.Sneaker;
import com.pluralsight.sneakerdrops.service.DropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class StartupRunner implements CommandLineRunner {

    private final BrandRepository brandRepository;
    private final SneakerRepository sneakerRepository;
    private final DropService dropService;

    @Autowired
    public StartupRunner(BrandRepository brandRepository, SneakerRepository sneakerRepository, DropService dropService) {
        this.brandRepository = brandRepository;
        this.sneakerRepository = sneakerRepository;
        this.dropService = dropService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
        System.out.println(dropService.getStatus());
        for (Brand brand : brandRepository.findAll()) {
            System.out.println(brand.getId() + " - " + brand.getName());
        }
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Sneaker Library ---");
            System.out.println("1) List all sneakers");
            System.out.println("0) Quit");
            System.out.print("Your Choice: ");

            switch (scanner.nextInt()) {
                case 1 ->listSneakers();
                case 0 -> running = false;
                default -> System.out.println("Wrong Input!");
            }
        }
    }

    private void listSneakers() {
        System.out.println("---" +  sneakerRepository.count() + " Sneakers---");
        for (Sneaker s : sneakerRepository.findAll()) {
            System.out.println(s.getId() + " - " + s.getModel() + "(" + s.getPrice() + ")");
        }
    }

    private void seedData() {
        if (brandRepository.count() == 0) {
            brandRepository.save(new Brand("Nike"));
            brandRepository.save(new Brand("Adidas"));
            brandRepository.save(new Brand("New Balance"));
            brandRepository.save(new Brand("Reebok"));
            brandRepository.save(new Brand("Prada"));
        }
        if (sneakerRepository.count() == 0) {
            sneakerRepository.save(new Sneaker("Air Force 1", 100, 1972));
            sneakerRepository.save(new Sneaker("Air Jordan 1", 180, 1973));
            sneakerRepository.save(new Sneaker("Prada Cup", 900, 2000));
            sneakerRepository.save(new Sneaker("Yeezy", 220, 2012));
            sneakerRepository.save(new Sneaker("Air Max", 180, 1985));
        }
    }
}