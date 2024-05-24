package blog.example.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.example.model.entity.UserEntity;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Long> {

	// UserService から渡されるユーザー情報（っメールアドレス）を条件に DB 検索
	UserEntity findByUserEmail(String userEmail);
	
	// UserService から渡されるユーザー情報を基に DB へ保存する
	UserEntity save(UserEntity userEntity);
	
	// ユーザー情報一覧を取得
	List<UserEntity> findAll();
}
