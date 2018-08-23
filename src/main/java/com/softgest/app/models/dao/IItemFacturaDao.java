package com.softgest.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softgest.app.models.entity.ItemFactura;

@Repository
public interface IItemFacturaDao extends JpaRepository<ItemFactura, Long> {

}
