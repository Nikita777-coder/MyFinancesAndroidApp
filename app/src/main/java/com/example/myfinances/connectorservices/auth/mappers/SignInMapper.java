package com.example.myfinances.connectorservices.auth.mappers;

import com.example.myfinances.connectorservices.auth.dto.SignInRequest;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SignInMapper {
    SignInMapper SIGN_IN_MAPPER = Mappers.getMapper(SignInMapper.class);
    @Mapping(target = "email", conditionExpression = "java(isPublicDataEmail(publicData))", source = "publicData")
    @Mapping(target = "login", conditionExpression = "java(!isPublicDataEmail(publicData))", source = "publicData")
    SignInRequest userDataToSignInRequest(String publicData, String password);
    default boolean isPublicDataEmail(String publicData) {
        return publicData.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
    }
}
