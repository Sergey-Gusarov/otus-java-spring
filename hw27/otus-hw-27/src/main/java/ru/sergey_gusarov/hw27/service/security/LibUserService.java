package ru.sergey_gusarov.hw27.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ru.sergey_gusarov.hw27.domain.security.LibUserDetails;
import ru.sergey_gusarov.hw27.domain.security.LibUser;
import ru.sergey_gusarov.hw27.repository.security.UserRepository;

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
        return new LibUserDetails(libUser);
    }
}
