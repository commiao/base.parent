package base.arch.component.spring.hession.common;

public class HessionResponse<T>  extends AbstractHession{
    private static final long serialVersionUID = 1L;
    private T data ;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    
}
