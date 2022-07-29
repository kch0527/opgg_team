package opgg.opgg.service.summoner;

import lombok.RequiredArgsConstructor;
import opgg.opgg.domain.user.User;
import opgg.opgg.domain.user.UserRepository;
import opgg.opgg.web.dto.user.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    @Transactional
    public Long join(UserDTO userDTO) {
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));

        return userRepository.save(userDTO.toEntity()).getId();
    }

}
