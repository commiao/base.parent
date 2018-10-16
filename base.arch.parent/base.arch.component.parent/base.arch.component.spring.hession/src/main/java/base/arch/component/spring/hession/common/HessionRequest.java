package base.arch.component.spring.hession.common;
/**
 * CreateDate:2016年12月9日下午6:29:19 
 * @Description: hession请求封装Bean
 * @author:jannal
 * @version V1.0
 */
public class HessionRequest<T> extends AbstractHession{
    private static final long serialVersionUID = 1L;
    private T params;
    public T getParams() {
        return params;
    }
    public void setParams(T params) {
        this.params = params;
    }
}
