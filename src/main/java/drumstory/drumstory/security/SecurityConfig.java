package drumstory.drumstory.security;

import drumstory.drumstory.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtility jwtUtility;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(AbstractHttpConfigurer::disable) // spring security 기본 인증 해제 -> jwt 사용
                .formLogin(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))       // CORS 설정
                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Swagger UI와 API 문서화 경로에 대한 접근을 모든 사용자에게 허용
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").denyAll()
                                .requestMatchers("/todayReservation").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/reservation/**").hasAnyRole("ADMIN", "MEMBER")
                        .anyRequest().permitAll()
                )
                // 간단한 테스트를 위해 csrf토큰 비활성화
                .csrf((csrf) -> csrf.ignoringRequestMatchers("/**"))
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtility, customUserDetailsService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));  // ✅ 프론트엔드 도메인 허용
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);  // ✅ 쿠키 & 인증 정보 포함 가능

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}