package kolesov.maksim.mapping.map.service;

public interface JwtService {

    boolean verify(String token);

    String getSub(String token);

    boolean isAccess(String token);

}
