package com.marvim.wishlist;

import com.marvim.wishlist.domain.ports.output.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class WishlistApplicationTests {

	@MockitoBean
	private WishlistRepository wishlistRepository;

	@Test
	void contextLoads() {
	}

}
