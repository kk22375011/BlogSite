package blog.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.example.config.WebSecurityConfig;
import blog.example.model.dao.UserDao;
import blog.example.model.entity.UserEntity;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	// ユーザーの情報を保存する
	public boolean createAccount(String userName, String userEmail, String password) {
		
		// RegisterController から渡されるユーザー情報（ユーザーメールアドレス）を条件に DB 検索で検索する
		UserEntity userEntity = userDao.findByUserEmail(userEmail);
		
		// RegisterController から渡されるユーザー情報（ユーザー名、パスワード）を条件に DB 検索で検索した結果
		// なかった場合はには、保存
		if(userEntity == null) {
			
			userDao.save(new UserEntity(userName, userEmail, password));
			WebSecurityConfig.addUser(userEmail, password);
			return true;
		} else {
			
			return false;
		}
	}
	
	// ユーザーの一覧を取得する
	public List<UserEntity> getAllAccounts(){
		
		return userDao.findAll();
	}
	
	// id を見つけるために
	// コントローラーでわたってきた userEmail を基にして DB を検索
	public UserEntity selectById(String userEmail) {
	
		return userDao.findByUserEmail(userEmail);
	}
}
