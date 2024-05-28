package xavr.todolist.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xavr.todolist.domain.Role;
import xavr.todolist.domain.User;
import xavr.todolist.repositories.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class UserDetailServiceImplementation implements UserDetailsService {

    final private UserRepository  userRepository;

    public UserDetailServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not Found")));
        Set<GrantedAuthority> grantedAuthoritySet= new HashSet<>();
        for (Role role: user.get().getRoles()){
            grantedAuthoritySet.add(new SimpleGrantedAuthority(role.name()));
        }
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), grantedAuthoritySet);
    }
}
