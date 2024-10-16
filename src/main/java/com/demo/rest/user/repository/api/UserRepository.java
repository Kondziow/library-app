package com.demo.rest.user.repository.api;

import com.demo.rest.repository.api.Repository;
import com.demo.rest.user.entity.User;

import java.util.UUID;

public interface UserRepository extends Repository<User, UUID> {
}
