
package cn.cherish.tineng.common.shiro;

import cn.cherish.tineng.dal.entity.User;
import cn.cherish.tineng.service.UserService;
import com.google.common.base.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * Created by Cherish on 2017/1/4.
 */
@Slf4j
public class MShiroRealm extends AuthorizingRealm {

    /**
     *  @Autowired 注入userService，UserService不能被动态代理代理，@Transactional等就不起效了
     *  所以UserSerivece使用@Scope("prototype")
     * cn.cherish.mboot.config.ShiroConfiguration.shiroFilterFactoryBean()方法中
     * shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager());
     * 设置安全管理器出问题，xml的配置方式尽管一样
     * 这或许是@Bean shiroFilterFactoryBean方式的诟病吧
     */
    @Autowired
    private UserService userService;

    /**`
     * 权限认证，为当前登录的Subject授予角色和权限
     *  经测试：本例中该方法的调用时机为需授权资源被访问时
     *  经测试：并且每次访问需授权资源时都会执行该方法中的逻辑，这表明本例中默认并未启用AuthorizationCache
     *  经测试：如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（也就是cache时间，在ehcache-shiro.xml中配置），超过这个时间间隔再刷新页面，该方法会被执行
	 * @see AuthorizingRealm#doGetAuthorizationInfo(PrincipalCollection)
	 */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        log.debug("##################执行Shiro权限认证##################");
        //获取当前登录输入的用户名，等价于(String) principalCollection.fromRealm(getName()).iterator().next();
        ShiroUser shiroUser = (ShiroUser) getAvailablePrincipal(principals);

        //到数据库查是否有此对象
        User user = userService.findByUsername(shiroUser.username);// 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        if (user != null) {
            //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            //用户的角色集合
            info.addRole("admin");
            return info;
        }else {
            //throw new AuthorizationException();
            // 返回null的话，访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
             return null;
        }

    }

    /**
     *  subject.login(token);登录时调用.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        log.debug("验证当前Subject时获取到token为：{}", ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));

        //查出是否有此用户
        User user = userService.findByUsername(token.getUsername());
        if (user == null) {
            throw new UnknownAccountException("此用户不存在");
        }

//        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
        return new SimpleAuthenticationInfo(
                new ShiroUser(user.getId(), user.getUsername(), user.getNickname()),//user.getUsername()
                user.getPassword(), getName());
    }

    /**
     * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
     */
    static class ShiroUser implements Serializable {
        private static final long serialVersionUID = -1373760761780840081L;
        public Long id;
        public String username;
        public String nickname;

        private ShiroUser(Long id, String username, String nicknam) {
            this.id = id;
            this.username = username;
            this.nickname = nickname;
        }

        /**
         * 本函数输出将作为默认的<shiro:principal/>输出.
         */
        @Override
        public String toString() {
            return username;
        }

        /**
         * 重载hashCode,只计算username;
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(username);
        }

        /**
         * 重载equals,只计算username;
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            ShiroUser other = (ShiroUser) obj;
            if (username == null) {
                if (other.username != null) {
                    return false;
                }
            } else if (!username.equals(other.username)) {
                return false;
            }
            return true;
        }
    }

}

