package kh.com.kshrd.miniprojectgamifiedhabittracker.service.impl;

import kh.com.kshrd.miniprojectgamifiedhabittracker.repository.AppUserRepository;
import kh.com.kshrd.miniprojectgamifiedhabittracker.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        return appUserRepository.getUserByIdentifier(identifier);
    }


}
