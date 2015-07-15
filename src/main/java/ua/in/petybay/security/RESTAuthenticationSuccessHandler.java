package ua.in.petybay.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ua.in.petybay.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by slavik on 14.07.15.
 */
@Component
public class RESTAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

//        clearAuthenticationAttributes(request);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");

        User user = ((SecUserDetails)(authentication.getPrincipal())).getUser();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(user);

//        JSONObject obj = new JSONObject();
//        obj.put("message", "hello from server");

        response.getWriter().print(json);
    }
}
