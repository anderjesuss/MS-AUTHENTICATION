package com.anderjesuss.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anderjesuss.entity.Menu;
import com.anderjesuss.repository.MenuRepository;
import com.anderjesuss.service.MenuServices;

@Service
public class MenuServicesImpl implements MenuServices {
	
	@Autowired
	private MenuRepository repo;

	@Override
	public List<Menu> menusUsuario(String correo) {
		return repo.menusDelUsuario(correo);
	}
	
}
