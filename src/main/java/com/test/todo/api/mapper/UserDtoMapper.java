package com.test.todo.api.mapper;


import com.test.todo.api.model.CreateUserAccountRequestV1;
import com.test.todo.api.model.UpdateUserMeV1;
import com.test.todo.api.model.UserRepresentationV1;
import com.test.todo.core.domain.model.UserAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    UserAccount toUserAccount(CreateUserAccountRequestV1 createUserAccountRequestV1);
    UserRepresentationV1 toUserRepresentationV1(UserAccount userAccount);
    UserAccount toUserAccountUpdate(UpdateUserMeV1 user);
}
