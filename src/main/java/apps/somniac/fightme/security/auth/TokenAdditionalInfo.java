package apps.somniac.fightme.security.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import apps.somniac.fightme.entities.CharacterEntity;
import apps.somniac.fightme.entities.UserEntity;
import apps.somniac.fightme.exceptions.messages.DataErrorMessages;
import apps.somniac.fightme.services.LoginService;

@Transactional
@SuppressWarnings("deprecation")
@Component
public class TokenAdditionalInfo implements TokenEnhancer {
	
	@Autowired
	private LoginService loginService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		UserEntity u = loginService.findByUsername(authentication.getName()).orElseThrow(() -> {
			throw new UsernameNotFoundException(DataErrorMessages.USER_NO_CONTENT);
		});
		
		List<String> charactersList = new ArrayList<>();
		String name;
		
		for (CharacterEntity c : u.getCharacters()) {
			name = c.getName();
			charactersList.add(name);
		}
		
		Map<String, Object> info = new HashMap<>();
		
		info.put("name", u.getUsername());
		info.put("characters", charactersList);
//		info.put("img", u.getImg());
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		
		return accessToken;
		
	}

}
