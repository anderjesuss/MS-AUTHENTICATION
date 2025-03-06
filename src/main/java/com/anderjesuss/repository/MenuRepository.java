package com.anderjesuss.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.anderjesuss.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
	
	@Query(value = "select m.* from tb_menu_rol mr join tb_rol r on \r\n"
			+ "mr.cod_rol = r.cod_rol join tb_user u on \r\n"
			+ "u.idrol = r.cod_rol join tb_menu m on m.cod_menu = mr.cod_menu\r\n"
			+ "where u.email = ?1", nativeQuery = true)
	public List<Menu> menusDelUsuario(String correo);
}
