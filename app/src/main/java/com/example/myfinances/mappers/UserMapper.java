package com.example.myfinances.mappers;

import com.example.myfinances.dto.UpdateUserDto;
import com.example.myfinances.dto.UserOutData;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mapping(source = "email", target = "requestEmail")
    UpdateUserDto userOutDataToUpdateUserDto(UserOutData userOutData);
    @Mapping(target = "email", source = "requestEmail")
    UserOutData updateUserDtoToUserOutData(UpdateUserDto updateUserDto);
}
