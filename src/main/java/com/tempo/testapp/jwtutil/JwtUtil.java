package com.tempo.testapp.jwtutil;

import com.tempo.testapp.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    static final long JWT_TOKEN_VALIDITY = 5*60*60;

    public static final long serialVersionUID = 2342342345L;

    @Value("${jwt.secret}")
    private String secretKey;

    //retrieve username from jwt token
    public String getUsernameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }
    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //for retrieving ay information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
    //check if token is expired
    private Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    //todo generate token for user
    public String generateToken(User user){
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, user);
    }
    // define the claims of the token, lie issues, expiration, subject
    //sign the jwt using hs512 akgi abd secret key
    private String doGenerateToken(Map<String, Object> claims, User user){
        return Jwts.builder().setClaims(claims).setSubject(user.getUser_name()).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .claim("userId", user.getUser_name())
                .claim("email", user.getEmail())
                .claim("name", user.getName())
                .compact();
    }
    //validate token
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = getUsernameFromToken((token)) ;
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
