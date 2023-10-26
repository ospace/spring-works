package application;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import library.Library;

@Component
public class Foo {
	@Autowired
	private Library lib;
	
	
	public Foo() {
		System.out.println(">> Foo construct");
	}
	
	@PostConstruct
	private void init() {
		System.out.println(">> Foo: " + lib.hello());
	}
}
