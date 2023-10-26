package com.tistory.ospace.simplesecurity2.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
//import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;

import com.tistory.ospace.simplesecurity2.entity.Post;
import com.tistory.ospace.simplesecurity2.entity.Search;

//https://medium.com/sfl-newsroom/spring-security-expression-based-access-control-56411a60ab3b
//@PreAuthorize("hasPermission(#id, 'com.test.Foo', read) or hasPermission(#id, 'com.test.Foo', admin)")
//@PreAuthorize("hasRole('ROLE_MASTER')")
//@PreAuthorize("hasAuthority('MASTER')")
//@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
//@PreAuthorize("hasPermission(#report, write) or hasPermission(#report, admin)")

//BasePermission
//Action: read, write, delete, ADMINISTRATION, create
@Mapper
public interface PostRepository {
	
	Integer countBy(@Param("search") Search search);
	
	@PostFilter("hasPermission(filterObject, 'READ')")
	List<Post> findAllBy(@Param("search") Search search);

	@PreAuthorize("hasPermission(#entity, 'READ')")
	Post findBy(Post entity);
	
	@PostAuthorize("hasPermission(returnObject, 'READ')")
	Post findById(Integer id);
	
	//@PreAuthorize("hasPermission(#entity, 'com.tistory.ospace.simplesecurity2.entity.POST', 'WRITE')")
	//@PreAuthorize("#entity.id != null ? hasPermission(#entity, 'UPDATE') : hasPermission(#entity, 'CREATE')")
	@PreAuthorize("hasPermission(#entity, 'WRITE')")
	void update(@Param("entity") Post entity);
	
	// 추가할 때에는 ID가 없기 때문에 객체 기반으로 처리가 불가.
	// -.사용자 권한에 따라서 제어
	// -.특정객체를 갖고 있는 경우에 따라 제어
	@PreAuthorize("hasPermission(#entity, 'CREATE')")
	void insert(@P("entity") Post entity);
}
