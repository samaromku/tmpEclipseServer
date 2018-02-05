package utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import entities.User;
import managers.TokenManager;
import managers.UsersManager;
import network.UserEnum;
import storage.Token;

public class CreatorPicker {	
	public static User pickCreator(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		Token token = (Token)session.getAttribute("token");
		if(token!=null){
		User creator = TokenManager.instance.getUserByToken(token.getTokenUUID());
		creator.setRole(UserEnum.MANAGER_ROLE);
		System.out.println(creator);
		UsersManager.INSTANCE.setCreator(creator);
		return creator;
		}else {
			return null;
		}
	}
}
