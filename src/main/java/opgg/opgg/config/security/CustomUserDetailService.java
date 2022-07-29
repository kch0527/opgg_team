package opgg.opgg.config.security;

import lombok.RequiredArgsConstructor;
import opgg.opgg.domain.user.User;
import opgg.opgg.domain.user.UserRepository;
import opgg.opgg.web.dto.user.UserSessionDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    private final HttpSession session;

    //username 이 DB에 있는지 확인
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("해당 사용자가 존재하지 않습니다. : " + username));

        session.setAttribute("user", new UserSessionDTO(user));

        return new CustomUserDetails(user);
    }
}
