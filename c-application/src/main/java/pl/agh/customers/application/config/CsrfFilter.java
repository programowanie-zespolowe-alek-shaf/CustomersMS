package pl.agh.customers.application.config;


import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class CsrfFilter extends GenericFilterBean {

    private static final String DEFAULT_CSRF_TOKEN_ATTR_NAME = HttpSessionCsrfTokenRepository.class.getName().concat(".CSRF_TOKEN");

    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = ((HttpServletRequest) req);
        HttpServletResponse response = ((HttpServletResponse) res);

        Object csrfToken = Optional.ofNullable(request.getSession()).map(s -> s.getAttribute(DEFAULT_CSRF_TOKEN_ATTR_NAME)).orElse(null);

        if (csrfToken instanceof CsrfToken) {
            response.addHeader("X-CSRF-Token", ((CsrfToken) csrfToken).getToken());
        }

        chain.doFilter(request, response);
    }

}
