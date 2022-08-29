package pers.codewld.iadmin.security.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

/**
 * MD5 加密/解密器
 */
@Component
public class MD5PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        Assert.hasText((String) rawPassword, "原始密码不得为空");
        return DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        Assert.hasText((String) rawPassword, "原始密码不得为空");
        Assert.hasText(encodedPassword, "加密后的密码不得为空");
        return encodedPassword.equals(DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes()));
    }

}
