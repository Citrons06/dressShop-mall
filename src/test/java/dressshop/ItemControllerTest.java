package dressshop;

import dressshop.domain.item.Item;
import dressshop.repository.ItemRepository;
import dressshop.service.ItemService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ItemControllerTest {

    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;

    @Test
    void save() {
        Item item = new Item("Spring", 100000, 100);
        itemService.saveItem(item);
        Assertions.assertThat(itemRepository.findByItemName("Spring").getItemName())
                .isEqualTo(item.getItemName());
    }
}
