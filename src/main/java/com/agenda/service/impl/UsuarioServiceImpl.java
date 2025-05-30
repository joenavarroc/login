package com.agenda.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import com.agenda.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.agenda.model.PasswordResetToken;
import com.agenda.model.Usuario;
import com.agenda.repository.UsuarioRepository;
import com.agenda.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void createPasswordResetToken(String email) {
        Optional<Usuario> userOpt = usuarioRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            Usuario user = userOpt.get();
            String token = UUID.randomUUID().toString();
            LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(30);

            PasswordResetToken resetToken = new PasswordResetToken(token, user, expiryDate);
            tokenRepository.save(resetToken);

            String resetLink = "http://localhost:8080/reset-password?token=" + token;

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Restablecer contraseña");
            mailMessage.setText("Haz clic en el siguiente enlace para restablecer tu contraseña:\n" + resetLink);

            mailSender.send(mailMessage);
        }
    }

    @Override
    public boolean resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByToken(token);
        if (tokenOpt.isPresent()) {
            PasswordResetToken resetToken = tokenOpt.get();
            if (!resetToken.isExpired()) {
                Usuario user = resetToken.getUser();
                user.setPassword(passwordEncoder.encode(newPassword));
                usuarioRepository.save(user);
                tokenRepository.delete(resetToken);
                return true;
            }
        }
        return false;
    }

    // Nuevo método para verificar duplicados
    @Override
    public boolean existsByUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    // Nuevo método para guardar usuarios
    @Override
    public void registrar(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
    }
}
