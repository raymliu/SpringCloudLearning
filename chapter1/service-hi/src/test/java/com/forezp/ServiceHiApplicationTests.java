package com.forezp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceHiApplicationTests {

	@Test
	public void contextLoads() {
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
			Set<Integer> set = new HashSet<>();
			List<Integer> list = new ArrayList<>();
			int n = scanner.nextInt();
			while (n > 0) {
				set.add(scanner.nextInt());
				n--;
			}
			for (int key : set) {
				list.add(key);
			}
			Collections.sort(list);
			for (Integer l : list) {
				System.out.println(l);
			}
		}
	}

}
