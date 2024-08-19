package com.elbourissi.scm.Service.Impl;

import com.elbourissi.scm.Entity.User;
import com.elbourissi.scm.Helper.Constants;
import com.elbourissi.scm.Helper.Helper;
import com.elbourissi.scm.Helper.ResourceNotFound;
import com.elbourissi.scm.Repository.UserRepository;
import com.elbourissi.scm.Service.EmailService;
import com.elbourissi.scm.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService mailService;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           EmailService mailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    /**
     * @param user
     * @return
     */
    @Override
    public User save(User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleList(List.of(Constants.ROLE_USER));
        String emailToken = UUID.randomUUID().toString();
        user.setEmailToken(emailToken);
        User savedUser = userRepository.save(user);
        String emailLink = Helper.getLinkForEmailVerificatiton(emailToken);
        mailService.sendEmail(savedUser.getEmail(), "Verify Account : Smart Contact Manager" , emailLink);
        return savedUser;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    /**
     * @param user
     * @return
     */
    @Override
    public Optional<User> update(User user) {
        User user2 = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFound("User not found"));
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());
        // save the user in database
        User save = userRepository.save(user2);
        return Optional.ofNullable(save);
    }

    /**
     * @param id
     */
    @Override
    public void delete(String id) {
        User user2 = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("User not found"));
        userRepository.delete(user2);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public boolean isUserExists(String id) {
        User user2 = userRepository.findById(id).orElse(null);
        return user2 != null;
    }

    /**
     * @param email
     * @return
     */
    @Override
    public boolean isUserExistsByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        return user != null;
    }

    /**
     * @return
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * @param email
     * @return
     */
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }
}
