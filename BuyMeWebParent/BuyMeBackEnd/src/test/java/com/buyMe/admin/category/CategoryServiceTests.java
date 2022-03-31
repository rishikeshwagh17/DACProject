package com.buyMe.admin.category;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.buyMe.common.entity.Category;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {
	//fake object of repository
	@MockBean
	private CategoryRepository repo;
	//fake object of category service
	@InjectMocks
	private CategoryService service;
	
	@Test
	public void testCheckUniqueInNewModeReturnDuplicateName() {
		Integer id = null;
		String name ="Computers";
		String alias ="abc";
		
		Category category = new Category(id, name, alias);
		Mockito.when(repo.findByName(name)).thenReturn(category);
		Mockito.when(repo.findByAlias(alias)).thenReturn(null);
		String result = service.checkUnique(id, name, alias);
		assertThat(result).isEqualTo("DuplicateName");
	}
	
}
