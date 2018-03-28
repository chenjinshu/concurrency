package com.mmall.concurrency;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConcurrencyApplicationTests {

	@Test
	public void contextLoads() {

		final String[] str = new String[]{"a", "b", "c"};
		str[0] = "d";
		System.out.println(Arrays.toString(str));
	}

}
