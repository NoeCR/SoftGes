package com.softgest.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.softgest.app.models.entity.Categoria;

@Repository
public interface ICategoriaDao extends JpaRepository<Categoria, Long> {

}
