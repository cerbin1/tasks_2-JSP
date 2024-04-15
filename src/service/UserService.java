package service;

import db.dao.UserDao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean validateNoUserWithGivenEmailAndUsername(String email, String username) {
        return !userDao.existsByEmail(email) && !userDao.existsByUsername(username);
    }

    public void registerUser(String email, String username, String plainPassword, String name, String surname) {
        String hashedPassword = hashPassword(plainPassword);
        userDao.createUser(email, username, hashedPassword, name, surname);

//        UUID randomUuid = UUID.randomUUID();
//        userActivationLinkDao.createLink(username, randomUuid);
//        String mailContent = "Go to this link to activate your account: \n" + APP_BASE_URL + APP_BASE_PATH + "/activate?linkId=" + randomUuid;
//        emailSendingService.sendEmail("Task Application - activation link", mailContent, email);
    }

    // note - SHA-512 should be changed with PBKDF2, BCrypt, or SCrypt but for simplicity and no additional lib SHA was used
    private String hashPassword(String plainPassword) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update("salt".getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(plainPassword.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
