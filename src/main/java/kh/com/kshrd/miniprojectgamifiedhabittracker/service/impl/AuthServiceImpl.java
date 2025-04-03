package kh.com.kshrd.miniprojectgamifiedhabittracker.service.impl;

import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.request.AppUserRequest;
import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.request.AuthRequest;
import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.response.AppUserResponse;
import kh.com.kshrd.miniprojectgamifiedhabittracker.dto.response.TokenResponse;
import kh.com.kshrd.miniprojectgamifiedhabittracker.exception.BadRequestException;
import kh.com.kshrd.miniprojectgamifiedhabittracker.exception.ConflictException;
import kh.com.kshrd.miniprojectgamifiedhabittracker.jwt.JwtService;
import kh.com.kshrd.miniprojectgamifiedhabittracker.model.AppUser;
import kh.com.kshrd.miniprojectgamifiedhabittracker.repository.AppUserRepository;
import kh.com.kshrd.miniprojectgamifiedhabittracker.repository.AuthRepository;
import kh.com.kshrd.miniprojectgamifiedhabittracker.service.AuthService;
import kh.com.kshrd.miniprojectgamifiedhabittracker.service.EmailService;
import kh.com.kshrd.miniprojectgamifiedhabittracker.service.OtpService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final ModelMapper modelMapper;
    private final EmailService emailService;
    private final OtpService otpService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUserResponse register(AppUserRequest request) {
        AppUser appUserByUsername = appUserRepository.getUserByIdentifier(request.getUsername());
        AppUser appUserByEmail = appUserRepository.getUserByIdentifier(request.getEmail());
        if (appUserByUsername != null && appUserByUsername.getIsVerified()) {
            throw new ConflictException("The username is already associated with an existing account. Please try with a different one.");
        }

        if (appUserByEmail != null && appUserByEmail.getIsVerified()) {
            throw new ConflictException("The email is already associated with an existing account. Please try with a different one.");
        }

        if (appUserByUsername != null) {
            appUserRepository.removeAppUserById(appUserByUsername.getAppUserId());
        }

        if (appUserByEmail != null) {
            appUserRepository.removeAppUserById(appUserByEmail.getAppUserId());
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        AppUser appUser = authRepository.register(request);
        String otp = otpService.generateOtp(appUser.getEmail());
        emailService.sendMail(otp, appUser.getEmail());
        return modelMapper.map(appUser, AppUserResponse.class);
    }

    @SneakyThrows
    @Override
    public TokenResponse login(AuthRequest request) {
        authenticate(request.getIdentifier(), request.getPassword());
        AppUser appUser = appUserRepository.getUserByIdentifier(request.getIdentifier());
        if (!appUser.getIsVerified()) {
            throw new BadRequestException("Your email address is not yet verified. Please verify your email before logging in.");
        }
        String token = jwtService.generateToken(request.getIdentifier());
        return TokenResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public void verify(String email, String otp) {

        AppUser appUser = appUserRepository.getUserByIdentifier(email);

        if (appUser == null) {
            throw new BadRequestException("The email address provided is not registered. Please check and try again.");
        }

        boolean isValidateOtp = otpService.validateOtp(email, otp);
        if (!isValidateOtp) {
            throw new BadRequestException("The OTP entered is invalid or has expired. Please request a new OTP and try again.");
        }

        appUserRepository.updateVerify();
    }

    @Override
    public void resend(String email) {

        AppUser appUser = appUserRepository.getUserByIdentifier(email);

        if (appUser == null) {
            throw new BadRequestException("The email address provided is not registered. Please check and try again.");
        }

        String otp = otpService.generateOtp(email);
        emailService.sendMail(otp, email);

    }

    @Override
    public UUID getCurrentAppUserId() {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return appUser.getAppUserId();
    }

    private void authenticate(String identifier, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(identifier, password));
        } catch (AuthenticationException e) {
            throw new BadRequestException("Invalid username, email, or password. Please check your credentials and try again.");
        }
    }


}
