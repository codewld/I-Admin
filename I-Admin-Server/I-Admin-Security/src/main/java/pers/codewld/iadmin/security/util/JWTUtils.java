package pers.codewld.iadmin.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import pers.codewld.iadmin.common.exception.CustomException;
import pers.codewld.iadmin.common.model.enums.ResultCode;
import pers.codewld.iadmin.common.util.ContextUtils;
import pers.codewld.iadmin.security.model.entity.IUserDetails;

import java.util.Date;
import java.util.List;

/**
 * JWT 工具类
 */
public class JWTUtils {

    /** 过期时长，以秒为单位 */
    private static final Long expiration;

    /** 加密/解密算法 */
    private static final Algorithm algorithm;

    /** JWT校验器 */
    private static final JWTVerifier jwtVerifier;

    static {
        String secret = ContextUtils.getProperty("security.jwt.secret");
        expiration = Long.valueOf(ContextUtils.getProperty("security.jwt.expiration"));
        algorithm = Algorithm.HMAC256(secret);
        jwtVerifier = JWT.require(algorithm).build();
    }

    /**
     * 签发
     */
    public static String sign(IUserDetails iUserDetails) {
        return JWT
                .create()
                .withAudience(iUserDetails.getId())
                .withClaim("username", iUserDetails.getUsername())
                .withClaim("roleIdList", iUserDetails.getRoleIdList())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration * 1000))
                .sign(algorithm);
    }

    /**
     * 解析
     */
    public static IUserDetails decode(String token) {
        try {
            // 校验
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            // 提取信息
            String id = decodedJWT.getAudience().get(0);
            String username = decodedJWT.getClaim("username").asString();
            List<String> roleIdList = decodedJWT.getClaim("roles").asList(String.class);
            // 重新组合为对象
            IUserDetails iUserDetails = new IUserDetails();
            iUserDetails.setId(id);
            iUserDetails.setUsername(username);
            iUserDetails.setRoleIdList(roleIdList);
            return iUserDetails;
        } catch (Exception e) {
            throw new CustomException(ResultCode.INVALID_CREDENTIAL, e.getMessage());
        }
    }

}
