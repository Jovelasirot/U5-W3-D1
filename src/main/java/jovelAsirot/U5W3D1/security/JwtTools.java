package jovelAsirot.U5W3D1.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jovelAsirot.U5W3D1.entities.Employee;
import jovelAsirot.U5W3D1.exceptions.UnauthorizedException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTools {

    public String createToken(Employee employee) {
        Date currentDate = new Date(System.currentTimeMillis());

        return Jwts.builder().issuedAt(currentDate).expiration( new Date(System.currentTimeMillis() +  1000 * 60 * 60 * 24 * 7)).subject(String.valueOf(employee.getId())).signWith(Keys.hmacShaKeyFor(secret.ge)).compact()
    }

    public void verifyToken(String token){
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build().parse(token);
        } catch (Exception ex) {
            throw new UnauthorizedException("Error with token, try again");
        }
    }

}