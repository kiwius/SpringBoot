package springSecurityFormLogin.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface UserDetailsService {
    public UserDetails loadUserByUserName(String memberId);
}
