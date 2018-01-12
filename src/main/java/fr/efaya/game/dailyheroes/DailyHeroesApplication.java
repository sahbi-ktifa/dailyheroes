package fr.efaya.game.dailyheroes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.efaya.game.dailyheroes.domain.Item;
import fr.efaya.game.dailyheroes.domain.Notification;
import fr.efaya.game.dailyheroes.domain.User;
import fr.efaya.game.dailyheroes.domain.builder.NotificationBuilder;
import fr.efaya.game.dailyheroes.repository.ItemRepository;
import fr.efaya.game.dailyheroes.repository.UserRepository;
import fr.efaya.game.dailyheroes.service.LootService;
import fr.efaya.game.dailyheroes.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

@SpringBootApplication
public class DailyHeroesApplication {

	private Logger LOGGER = LoggerFactory.getLogger(DailyHeroesApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DailyHeroesApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(ItemRepository repository, UserRepository userRepository, LootService lootService, NotificationService notificationService) {
		return args -> {
			Iterable<Item> existingItems = repository.findAll();
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			Resource[] resources = resolver.getResources("classpath:items/loot_*.json");
			for (Resource resource : resources) {
				doPrimitiveItemInsertion(repository, existingItems, resource);
			}
			lootPotentialNewBasicItemsForUsers(userRepository, lootService, notificationService);
		};
	}

	private void doPrimitiveItemInsertion(ItemRepository repository, Iterable<Item> existingItems, Resource resource) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<Item>> typeReference = new TypeReference<List<Item>>(){};
		InputStream inputStream = resource.getInputStream();
		try {
            List<Item> items = mapper.readValue(inputStream,typeReference);
            if (existingItems != null && ((Collection<Item>)existingItems).size() > 0) {
                items.removeIf(i -> ((Collection<Item>) existingItems).stream().anyMatch(_i -> _i.getId().equals(i.getId())));
            }
			if (!CollectionUtils.isEmpty(items)) {
				repository.save(items);
				LOGGER.info("Items saved for: " + resource.getFilename() + " !");
			} else {
				LOGGER.info("No more items to save for: " + resource.getFilename() + " !");
			}
        } catch (IOException e){
			LOGGER.warn("Unable to save items (" + resource.getFilename() + ") : " + e.getMessage());
        }
	}

	private void lootPotentialNewBasicItemsForUsers(UserRepository userRepository, LootService lootService, NotificationService notificationService) {
		Iterable<User> users = userRepository.findAll();
		if (users != null && !((Collection<User>) users).isEmpty()) {
			users.forEach(u -> {
				int count = lootService.lootBasicItems(u);
				if (count > 0) {
					Notification notification = NotificationBuilder.newInstance()
							.withMessage("New items have been unlocked, check your profile!")
							.forUser(u.getUsername())
							.addExtra("icon", "fa-gift")
							.build();
					notificationService.saveNotification(notification);
					LOGGER.info(count + " default new items were given to " + u.getUsername());
				}
			});
		}
	}

}
