package xws.auth.exception;

public class UsernameNotUniqueException extends  Exception{
    public UsernameNotUniqueException(String errorMessage) {
        super(errorMessage);
    }
}
