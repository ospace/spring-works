package com.example.demo3;

import javax.annotation.PostConstruct;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value="foo-component.enabled")
public class FooComponent {
	
	private FooComponent() {
		throw new RuntimeException("foo");
	}
	
	@PostConstruct
	private void init () {
		throw new RuntimeException("foo");
	}

}
