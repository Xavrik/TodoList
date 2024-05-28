package xavr.todolist.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xavr.todolist.services.interfaces.IUserService;
import xavr.todolist.domain.User;
import xavr.todolist.domain.Role;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class DefaultDataConfig {

    @Autowired
    private IUserService userService;

    private void createDefaultUsers() {
        List.of(
                User.builder().username("john").password("123").build(),
                User.builder().username("jane").password("123").build()
        ).forEach(userService::CreateUser);

        userService.CreateUser(
                User.builder().username("admin").password("admin").roles(new HashSet<>(Arrays.asList(Role.ADMIN))).build()
        );

        userService.CreateUser(
                User.builder().username("moder").password("moder").roles(new HashSet<>(Arrays.asList(Role.MODERATOR))).build()
        );

        userService.CreateUser(
                User.builder().username("umoderator").password("12345").roles(new HashSet<>(Arrays.asList(Role.ADMIN, Role.MODERATOR))).build()
        );
    }

}
