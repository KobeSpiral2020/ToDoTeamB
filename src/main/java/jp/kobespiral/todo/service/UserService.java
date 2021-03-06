package jp.kobespiral.todo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobespiral.todo.dto.UserDto;
import jp.kobespiral.todo.entitiy.User;
import jp.kobespiral.todo.exception.UidException;
import jp.kobespiral.todo.form.UserForm;
import jp.kobespiral.todo.repository.UserRepository;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  public UserDto createUser(UserForm form){
    Date now = new Date();
    String uid = form.getUid();
    List<UserDto> uids = this.getUserByuid(uid);
    
    if (uid != null && uids.isEmpty()){
      User user = userRepository.save(new User(null, form.getUid(), form.getName(), now));
      return UserDto.build(user);
  } else { 
      throw new UidException("ユーザIDが重複しています");
  }
}


  public List<UserDto> getUserByuid(String uid){
    ArrayList<UserDto> list = new ArrayList<UserDto>();

        for (User user : userRepository.findUserByUidLike( uid )) {
            list.add(UserDto.build(user));
        }
        return list;
  }

  public List<UserDto> getAllUsers(){
    ArrayList<UserDto> list = new ArrayList<UserDto>();

    for (User user : userRepository.findAll()) {
        list.add(UserDto.build(user));
    }
    return list;
  }
  
}