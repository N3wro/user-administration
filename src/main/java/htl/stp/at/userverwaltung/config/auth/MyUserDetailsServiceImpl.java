package htl.stp.at.userverwaltung.config.auth;


import htl.stp.at.userverwaltung.persistence.MyUserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public record MyUserDetailsServiceImpl(MyUserRepository userRepository) implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Unknown user/password combination"));
        return new MyUserDetailsImpl(user);
    }
}