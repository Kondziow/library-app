package com.demo.rest.component;

import com.demo.rest.user.dto.function.UserToResponseFunction;
import com.demo.rest.user.dto.function.UsersToResponseFunction;

public class DtoFunctionFactory {
    public UsersToResponseFunction usersToResponse(){return new UsersToResponseFunction();}
    public UserToResponseFunction userToResponse(){return new UserToResponseFunction();}
}
