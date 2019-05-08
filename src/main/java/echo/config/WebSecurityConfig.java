package echo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.BeanIds;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.cors.CorsUtils;

import echo.service.AccountService;

// import echo.security.AuthProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // @Autowired
    // AuthProvider authProvider;
    // @Autowired
    // TokenHelper tokenHelper;

    @Autowired
    private AccountService accountService;

    @Autowired
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    public CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(accountService) // 입력된 user에 대한 상세정보
                .passwordEncoder(passwordEncoder()); // password를 encorder하는 방식을 정함
    }
    
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/css/**, /static/js/**, *.ico");
    }
    // spring security는 front back server 분리 힘듬 security가 제공하는 기능은 한 WAS 서버에서 접근하는 클라이언트의 sessionid를 기준으로 보안인증기능을 제공하기 때문
    // 여기저기에 검색한 결과 front back server 일시 sessionid를 활용하는 것보다 jwt를 활용하는것이 더 효율적인듯 
    // front의 router controll이 안된다. session id가 안넘어온다. 
    // 그래서 Open Authorization 즉 OAuth라는 기술이 security 카테고리에 있다. 이것을 이용하여 front back분리를 한다.
    // 그래도 방법이 있지 않을까 formLogin()에서 loginPage("/login")일시 post "/login" sumit하면 successhandler로 넘어가는걸로 봐선 success인것 같은데 
    // sessionid가 null인 문제만 해결하면 되지 않을까 provider 제작해서 sessionid를 넘겨 authentication를 생성한다면 말야
    // usernamepasswordauthenticationtoken 에서 사용자의 인증시에 전달된 httpservletrequest의 정보에서 필요한 정보(아이피,sessionid)를 가져와서 저장된다
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // httpSecurity.addFilterBefore(new CustomFilter(), ChannelProcessingFilter.class);
        // httpSecurity.authorizeRequests().antMatchers("/").permitAll()
        //             .requestMatchers(CorsUtils::isPreFlightRequest).permitAll().anyRequest()
        //         .fullyAuthenticated().and().httpBasic().and().csrf().disable();                
        httpSecurity.cors().disable()
        .csrf().disable()
        .authorizeRequests().antMatchers("/", "/home","/signin").permitAll().antMatchers("/api/auth/**", "/api/redis/**")
                .permitAll().anyRequest()
                .authenticated().and()
                .formLogin().loginPage("/login")
                .defaultSuccessUrl("/alert").failureUrl("/login?error=true")
                .successHandler(customAuthenticationSuccessHandler).failureHandler(customAuthenticationFailureHandler)
                .permitAll().and()
                .logout().logoutSuccessUrl("/login").permitAll();

        //http.authenticationProvider(authProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}