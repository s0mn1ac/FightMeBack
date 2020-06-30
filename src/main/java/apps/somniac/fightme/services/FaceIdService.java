package apps.somniac.fightme.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import apps.somniac.fightme.dtos.FaceIdDto;
import apps.somniac.fightme.dtos.UserDto;

@Service
public interface FaceIdService {
	public FaceIdDto facialRecognition(String username, String image);
	public UserDto createPersonId(Long id);
	public UserDto deletePersonId(Long id);
	public void fillPersonId(MultipartFile file, Long id);
	public void trainFacialRecognition();
	public FaceIdDto[] listPersonId();
}
