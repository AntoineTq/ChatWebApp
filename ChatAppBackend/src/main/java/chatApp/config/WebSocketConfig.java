package chatApp.config;
import chatApp.model.Person;
import chatApp.repository.PersonRepository;
import chatApp.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private PersonRepository personRepository;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:4200").withSockJS();
    }

    // Got the idea to create WS with JWT from this tutorial :
    // https://medium.com/@poojithairosha/spring-boot-3-authenticate-websocket-connections-with-jwt-tokens-2b4ff60532b6
    // and modified it.
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {

                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                if (StompCommand.CONNECT.equals(accessor.getCommand())) {

                    String authorizationHeader = accessor.getFirstNativeHeader("Authorization");

                    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                        throw new RuntimeException("Missing or invalid Authorization header");
                    }
                    String token = authorizationHeader.substring(7);

                    String userEmail = jwtService.validateToken(token);
                    Optional<Person> person = personRepository.findByEmail(userEmail);
                    if (person.isEmpty()) {
                        throw new RuntimeException("user not found (WS inbound client interceptor)");
                    }

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            person.get(),
                            null,
                            List.of(new SimpleGrantedAuthority("USER")));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    accessor.setUser(authToken);
                }

                return message;
            }

        });
    }


}