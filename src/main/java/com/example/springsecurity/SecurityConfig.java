package com.example.springsecurity;

import com.example.springsecurity.server.serverImpl.TestUserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity    //注解开启Spring Security的功能
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //明文加密器,只需要在内存中有这个管理对象,如果不添加，从前端登录时会抛出异常Bad credentials（数据库操作需要这个bean，内存不需要，只需要将密码加密就可以）
    /*内置的PasswordEncoder实现列表
    NoOpPasswordEncoder（已废除）
    明文密码加密方式，该方式已被废除（不建议在生产环境使用），不过还是支持开发阶段测试Spring Security的时候使用。
    BCryptPasswordEncoder
    Argon2PasswordEncoder
    Pbkdf2PasswordEncoder
    SCryptPasswordEncoder */
    //使用以上四个方法都可以解密，但是数据库中得密码也是对应方法得加密（添加用户数据是需要password加密new BCryptPasswordEncoder().encode("123456")）
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();//passwordEncoder的实现类
    }

    //构造一个内存框架对象，获取数据库中的数据
/*    @Bean
    public UserDetailsService myUserDetailsService(){
        return new TestUserServerImpl();
    }*/
    //也可以自动注入
    @Autowired
    private TestUserServer testUserServer;

    //用户授权
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //基于内存来存储用户信息(需要加密不然会报错---Encoded password does not look like BCrypt)
        /*auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("user").password(new BCryptPasswordEncoder().encode("password")).authorities("user").and()          //设置
                .withUser("admin").password(new BCryptPasswordEncoder().encode("password")).authorities("admin", "user");*/
        //基于数据库来存储用户信息
        //auth.userDetailsService(myUserDetailsService());
        auth.userDetailsService(testUserServer);
    }

    //用户权限认证
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //这是认证的请求
                .authorizeRequests()
                .antMatchers("/", "/home", "/login", "/index", "/error").permitAll()  //这些请求 不需要认证
                //hasRole和hasAuthority的区别：我们在调用 hasAuthority 方法时，如果数据是从数据库中查询出来的，这里的权限和数据库中保存一致即可，
                // 可以不加 ROLE_ 前缀。即数据库中存储的用户角色如果是 admin，这里就是 admin。
                //也就是说，使用 hasAuthority 更具有一致性，你不用考虑要不要加 ROLE_ 前缀，数据库什么样这里就是什么样！
                // 而 hasRole 则不同，代码里如果写的是 USER，框架会自动加上 ROLE_ 前缀，所以数据库就必须是 ROLE_USER
                .antMatchers("/user/**").hasRole("USER")       //user及以下路径，需要ROLE_USER角色权限
                .antMatchers("/admin/**").hasAuthority("admin")//admin及以下路径，需要admin权限
                .and()
                //loginPage定制自定义登录页，相当于/toLogin其实自动转到/login，loginProcessingUr指定为login-》toLogin（表单提交只能为login）,加了这个需要接受用户名和密码，登录成功跳转  "/"
                //.formLogin().loginPage("/toLogin").usernameParameter("username").passwordParameter("password").loginProcessingUrl("/login").defaultSuccessUrl("/")
                .formLogin()//自带的login
                .and()
                .csrf().disable()//关闭csrf
                //等出路径为logout，登出成功跳转 "/"
                .logout().logoutUrl("/logout").logoutSuccessUrl("/")
                .and()
                .rememberMe().rememberMeParameter("rememberMe");
    }

    /**
     * 核心过滤器配置，更多使用ignoring()用来忽略对静态资源的控制
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/image/**");
    }
}

