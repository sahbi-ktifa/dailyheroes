package fr.efaya.game.todorpg;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.efaya.game.todorpg.domain.Item;
import fr.efaya.game.todorpg.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

@SpringBootApplication
public class TodorpgApplication {

	private Logger LOGGER = LoggerFactory.getLogger(TodorpgApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TodorpgApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(ItemRepository repository) {
		return args -> {
			Iterable<Item> existingItems = repository.findAll();
			doPrimitiveItemInsertion(repository, existingItems, "/items/loot_repeatable.json");
			doPrimitiveItemInsertion(repository, existingItems, "/items/loot_unique.json");
		};
	}

	private void doPrimitiveItemInsertion(ItemRepository repository, Iterable<Item> existingItems, String refFilePath) {
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<Item>> typeReference = new TypeReference<List<Item>>(){};
		InputStream inputStream = TypeReference.class.getResourceAsStream(refFilePath);
		try {
            List<Item> items = mapper.readValue(inputStream,typeReference);
            if (existingItems != null && ((Collection<Item>)existingItems).size() > 0) {
                items.removeIf(i -> ((Collection<Item>) existingItems).stream().anyMatch(_i -> _i.getId().equals(i.getId())));
            }
			if (!CollectionUtils.isEmpty(items)) {
				repository.save(items);
				LOGGER.info("Items saved for: " + refFilePath + " !");
			} else {
				LOGGER.info("No more items to save for: " + refFilePath + " !");
			}
        } catch (IOException e){
			LOGGER.warn("Unable to save items (" + refFilePath + ") : " + e.getMessage());
        }
	}
}
