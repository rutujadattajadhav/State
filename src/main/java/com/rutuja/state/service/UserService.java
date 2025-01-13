package com.rutuja.state.service;

import com.rutuja.state.entity.UserEntity;
import com.rutuja.state.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@EnableWebFluxSecurity
public class UserService implements ReactiveUserDetailsService {
    @Autowired
    private UserRepository userRepository;

    //method = findByUsername
    //return type = Mono<UserDetails>
    //params = String username
    //method call = userRepository.findById(username).map(UserEntity::new)
    //method = findById return type = Optional<UserEntity>
    //method = map return type = <U> Optional<U> return modified object like object UserDetails
    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findById(username).map(UserEntity ::new);
    }

}
