package tech.ada.tenthirty.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.ada.tenthirty.ecommerce.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findBySku(String s);
}
