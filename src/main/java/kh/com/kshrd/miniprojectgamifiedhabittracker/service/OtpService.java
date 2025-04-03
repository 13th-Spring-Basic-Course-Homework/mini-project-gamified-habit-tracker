package kh.com.kshrd.miniprojectgamifiedhabittracker.service;

public interface OtpService {

    String generateOtp(String email);

    boolean validateOtp(String email, String inputOtp);

}
