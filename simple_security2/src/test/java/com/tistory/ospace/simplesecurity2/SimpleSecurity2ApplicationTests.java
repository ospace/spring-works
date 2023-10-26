package com.tistory.ospace.simplesecurity2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import com.tistory.ospace.simplesecurity2.entity.Post;
import com.tistory.ospace.simplesecurity2.entity.Search;
import com.tistory.ospace.simplesecurity2.repository.PostRepository;
/*
 * 계정: 어드민, 사용자
 * 권한: 마스터
 * 
 * 어드민 계정는 모든 데이터 모든 접근 가능하지만 마스터 권한 데이터는 불가능
 * 사용자 계정는 자신 데이터만 모든 접근가능하고 다른 데이터는 일부만 읽기 가능
 * 
 * 마스터 권한은 모든 것을 할 수 있다.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleSecurity2ApplicationTests {
	private static final Logger logger = LoggerFactory.getLogger(SimpleSecurity2ApplicationTests.class);
	private static final Integer FIRST_ID = 1;
	private static final Integer SECOND_ID = 2;
	private static final Integer THIRD_ID = 3;
	private static final Integer FORTH_ID = 4;
	private static final String CONTENT = "foo";
	
	@Autowired
	private PostRepository repo;
	
	@Test
	@WithMockUser(username = "admin")
	public void 관리자계정으로_findAllBy_조회() {
		List<Post> details = repo.findAllBy(null);
		logger.info("details[{}]", details);
		
	    assertNotNull(details);
	    assertEquals(3, details.size());
	    //assertEquals(FIRST_ID, details.get(0).getId());
	    assertEquals(SECOND_ID, details.get(0).getId());
	    assertEquals(THIRD_ID, details.get(1).getId());
	    assertEquals(FORTH_ID, details.get(2).getId());
	}
	
	@Test
	@WithMockUser(username = "admin")
	public void 관리자계정으로_첫번째_데이터수정() {
		Post firstPost = Post.of(FIRST_ID);
		firstPost.setContent(CONTENT);
	        
	    logger.info("firstPost[{}]", firstPost);
	    
	    repo.update(firstPost);
	}
	
	// TODO create에 대한 권한은 테스트는 추후 좀더 생각
	// 기존 생성된 객체에 대한 접근관리는 가능하지만
	// 새로 생성되는 객체는 행위에 대한 권한 관리가 필요
	// 혹은 특정 사용자에게만 특정범위내에 객체 생성 허가도 가능할듯함
	@Test(expected = AccessDeniedException.class)
	@WithMockUser(username = "admin")
	public void 관리자계정으로_insert_생성_but_실패() {
		Post post = new Post();
		///post.setTitle(CONTENT);
		post.setContent(CONTENT);
		repo.insert(post);
		logger.info("post[{}]", post);
	}

	@Test
	@WithMockUser(username = "user")
	public void 사용자계정으로_findAllBy_조회() { 
		List<Post> details = repo.findAllBy(null);
		logger.info("details[{}]", details);
		
	    assertNotNull(details);
	    assertEquals(2, details.size());
	    assertEquals(THIRD_ID, details.get(0).getId());
	    assertEquals(FORTH_ID, details.get(1).getId());
	}
	
	@Test
	@WithMockUser(roles = {"MASTER"})
	public void MASTER권한으로_findAllBy_조회() {
		Search search = new Search();
		search.setKeyword("nd");
		List<Post> details = repo.findAllBy(search);
		logger.info("details[{}]", details);
		
	    assertNotNull(details);
	    assertEquals(4, details.size());
	    assertEquals(FIRST_ID, details.get(0).getId());
	    assertEquals(SECOND_ID, details.get(1).getId());
	    assertEquals(THIRD_ID, details.get(2).getId());
	    assertEquals(FORTH_ID, details.get(3).getId());
	}

	@Test(expected = AccessDeniedException.class)
	@WithMockUser(username = "user")
	public void 사용자계정으로_첫번째데이터_조회거부() { 
		repo.findById(FIRST_ID);
	}
	
	@Test
	@WithMockUser(username = "user")
	public void 사용자계정으로_세번째데이터_갱신() { 
		Post thirdPost = Post.of(THIRD_ID);
		thirdPost.setContent(CONTENT);
	        
	    logger.info("thirdPost[{}]", thirdPost);
	    
	    repo.update(thirdPost);
	}
}
