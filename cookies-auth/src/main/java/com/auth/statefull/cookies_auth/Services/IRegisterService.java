package com.auth.statefull.cookies_auth.Services;

import com.auth.statefull.cookies_auth.Dto.RegisterRequest;
import com.auth.statefull.cookies_auth.Entity.User;

public interface IRegisterService {
    public User registerUser(RegisterRequest registerRequest);
}
