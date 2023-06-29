package htl.stp.at.userverwaltung.config;


import htl.stp.at.userverwaltung.config.auth.MyUserDetailsImpl;
import htl.stp.at.userverwaltung.domain.MyUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class RedirectHandler implements AuthenticationSuccessHandler {



    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        MyUserDetailsImpl user = (MyUserDetailsImpl) authentication.getPrincipal();

      List<GrantedAuthority> authority =  user.getAuthorities().stream().filter(grantedAuthority -> grantedAuthority.getAuthority().contains("admin")).collect(Collectors.toList());
        if (authority.isEmpty()) {
            response.sendRedirect("/user/home");
        }
        else  {

            response.sendRedirect("/admin/home");
        }

    }


}