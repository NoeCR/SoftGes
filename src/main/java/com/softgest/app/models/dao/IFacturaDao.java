package com.softgest.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softgest.app.models.entity.Factura;

@Repository
public interface IFacturaDao extends JpaRepository<Factura, Long> {

}
