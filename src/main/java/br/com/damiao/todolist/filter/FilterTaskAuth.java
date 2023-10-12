package br.com.damiao.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.damiao.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();
        
        if (servletPath.equals("/tasks")) {
            var auth = request.getHeader("Authorization");
            var base64 = auth.substring("Basic".length()).trim();
            byte[] authDecode = Base64.getDecoder().decode(base64);
            var authString = new String(authDecode);
            String[] credencials = authString.split(":");
            String username = credencials[0];
            String password = credencials[1];

            var user = this.userRepository.findByUsername(username);
            if (user == null) {
                response.sendError(401, "Invalid credentials.");
            } else {
                var passwordVerify = BCrypt.verifyer()
                    .verify(password.toCharArray(), user.getPassword());
                if (passwordVerify.verified) {
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401, "Invalid password.");
                }
            }
        } else {
            filterChain.doFilter(request, response);  
        }        
    }
}
