package com.test.todo.api.mapper;


import com.test.todo.api.model.GenerateAccessTokenResponseV1;
import com.test.todo.api.model.LoginResponseV1;
import com.test.todo.core.model.AuthToken;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenDtoMapper {
    LoginResponseV1 toLoginResponseV1(AuthToken authToken);
    GenerateAccessTokenResponseV1 toGenerateAccessTokenResponseV1(AuthToken authToken);
}
