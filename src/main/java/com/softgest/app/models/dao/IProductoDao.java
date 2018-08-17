package com.softgest.app.models.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.softgest.app.models.entity.Producto;

@Repository
public interface IProductoDao extends JpaRepository<Producto, Long> {

	//@Query("select p from Producto p where p.categoria_id =?1")
	List<Producto> findByCategoriaId(Long categoria_id);
	Optional<Producto> findById(Long producto_id);
}
