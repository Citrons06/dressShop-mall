package dressshop.controller;

import dressshop.domain.item.Item;
import dressshop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/item/save")
    public String saveItem(@ModelAttribute Item item) {
        itemService.saveItem(item);
        return "item/saveForm";
    }
}
