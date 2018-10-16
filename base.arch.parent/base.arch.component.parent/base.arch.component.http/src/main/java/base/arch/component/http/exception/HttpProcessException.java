package base.arch.component.http.exception;

public class HttpProcessException  extends RuntimeException {

    private static final long serialVersionUID = -2749168865492921426L;

    public HttpProcessException(Exception e){
        super(e);
    }
    public HttpProcessException(String msg) {
        super(msg);
    }
    public HttpProcessException(String message, Exception e) {
        super(message, e);
    }

}
