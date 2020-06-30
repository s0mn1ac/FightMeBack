package apps.somniac.fightme.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;

import apps.somniac.fightme.dtos.UserDto;

@Service
public interface UserService {
	
	public UserDto getUser(Long id);
	public UserDto getUserByUserName(String userName);
	public void addUser(UserDto user);
	public void deleteUser(Long id);
	public List<UserDto> getUsers();
	public UserDto modifyUser(Long id, UserDto user);
	public UserDto modifyPassword(Long id, UserDto user);
	public Page<UserDto> getUsersPerPage(Integer page);
	public List<UserDto> getUserByName(String userName);
//	public void uploadImage(MultipartFile file, Long idUser);
	
}
