package tech.ada.tenthirty.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.ada.tenthirty.ecommerce.model.ItemReservado;

import java.util.List;

public interface ItemReservadoRepository extends JpaRepository<ItemReservado, Long> {
    List<ItemReservado> findBySku(String s);
}
