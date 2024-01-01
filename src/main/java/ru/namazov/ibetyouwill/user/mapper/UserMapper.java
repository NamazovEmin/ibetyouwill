package ru.namazov.ibetyouwill.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import ru.namazov.ibetyouwill.user.dto.UserDTO;
import ru.namazov.ibetyouwill.user.entity.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(UserDTO userDTO);

    UserDTO toDTO(User user);
}
