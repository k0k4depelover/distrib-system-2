package com.auth.statefull.cookies_auth.Services;

import com.auth.statefull.cookies_auth.Dto.LoginRequest;
import com.auth.statefull.cookies_auth.Entity.User;

public interface ILoginService {
    public User login(LoginRequest loginRequest);
    
}
