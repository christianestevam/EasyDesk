package com.ufc.easydesk.repository;

import com.ufc.easydesk.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
