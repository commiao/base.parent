package base.arch.component.cache.redis.util;

import java.util.Date;

/**
 * @CreateDate: 2018/12/17 14:51
 * @Description: TODO
 * @author: jingmiao
 * @version: V1.0
 */
public class Test {
    int id;
    String msg;
    boolean error;
    Date date;
    long idl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getIdl() {
        return idl;
    }

    public void setIdl(long idl) {
        this.idl = idl;
    }
}
