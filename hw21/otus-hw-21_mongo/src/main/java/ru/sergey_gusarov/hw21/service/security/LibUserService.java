package ru.sergey_gusarov.hw21.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ru.sergey_gusarov.hw21.domain.security.LibUserDetails;
import ru.sergey_gusarov.hw21.domain.security.LibUser;
import ru.sergey_gusarov.hw21.repository.security.UserRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class LibUserService implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        LibUser libUser = userRepository.findUserByUserName(userName);
        if (libUser == null) {
            throw new UsernameNotFoundException(userName);
        }

        List<SimpleGrantedAuthority> authorities =
                Arrays.asList(new SimpleGrantedAuthority("USER"));

        //return new User(user.getUsername(), user.getPassword(), authorities);

        return new LibUserDetails(libUser);
    }
}
