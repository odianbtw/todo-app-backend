package com.test.todo.dao.postgres.mapper;


import com.test.todo.core.domain.model.UserAccount;
import com.test.todo.dao.postgres.model.UserAccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    UserAccountEntity toUserAccountEntity(UserAccount account);
    UserAccount toUserAccount(UserAccountEntity entity);
}
