package me.starchier.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import me.starchier.ServerMain;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenUtil {
    public static final Logger getLogger = LogManager.getLogger(TokenUtil.class.getName());
    //过期时间 60 分钟
    private static final long EXPIRED_TIME = 60 * 60 * 1000;
    private static final String TOKEN_SECRET = "4DD5189F0E6016466C0E5948DCCAF294CE7611B9";

    public static String generateToken(String username, String password) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRED_TIME);
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            Map<String,Object> header = new HashMap<>(2);
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            return JWT.create()
                    .withHeader(header)
                    .withClaim("loginName", username)
                    .withClaim("password", password)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Throwable e) {
            getLogger.warn("TOKEN生成失败：", e);
            return null;
        }
    }
    public static boolean verifyToken(String token){
        /*
          @desc   验证token，通过返回true
         * @params [token]需要校验的串
         */
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
