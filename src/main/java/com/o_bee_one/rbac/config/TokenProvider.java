package com.o_bee_one.rbac.config;

import io.jsonwebtoken.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider implements Serializable {

  @Value("${jwt.token.validity}")
  public long TOKEN_VALIDITY;

  @Value("${jwt.signing.key}")
  public String SIGNING_KEY;

  @Value("${jwt.authorities.key}")
  public String AUTHORITIES_KEY;

  @Value("${jwt.permissions.key}")
  public String PERMISSION_KEY;

  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token).getBody();
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public String generateToken(Authentication authentication) {
    String authorities =
        authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .filter(authority -> authority.startsWith("ROLE_"))
            .collect(Collectors.joining(","));

    String permissions =
        authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .filter(authority -> authority.startsWith("PERMISSION_"))
            .collect(Collectors.joining(","));

    return Jwts.builder()
        .setSubject(authentication.getName())
        .claim(AUTHORITIES_KEY, authorities)
        .claim(PERMISSION_KEY, permissions)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
        .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
        .compact();
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = getUsernameFromToken(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  UsernamePasswordAuthenticationToken getAuthenticationToken(
          final String token, final UserDetails userDetails) {

    final JwtParser jwtParser = Jwts.parser().setSigningKey(SIGNING_KEY);
    final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
    final Claims claims = claimsJws.getBody();

    final Collection<? extends GrantedAuthority> roles =
            Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                    .map(SimpleGrantedAuthority::new)
                    .toList();

    final Collection<? extends GrantedAuthority> permissions =
            Arrays.stream(claims.get(PERMISSION_KEY).toString().split(","))
                    .map(SimpleGrantedAuthority::new)
                    .toList();

    final Collection<GrantedAuthority> authorities = new ArrayList<>();
    authorities.addAll(roles);
    authorities.addAll(permissions);

    return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
  }

}
