package base.arch.component.logger.bean;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;


public class Group implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 组名称
     */
    private String name;
    /**
     * 组描述
     */
    private String desc;
    
    /**
     * 类名称
     */
    private String className;
    
    /**
     * jar包信息
     */
    private JarInfo jarInfo;
    
    /**
     * war 包信息
     */
    private WarInfo warInfo;
    
    /**
     * 日志级别
     */
    private HTLevel level=HTLevel.INFO;
    
    /**
     * 线程id
     */
    private String threadId;
    
    /**
     * 日志类型
     */
    private String type;
    
    /**
     * 包类型
     */
    private String packageType ="jar";
    
    /**
     * 异常
     */
    private  Throwable throwable;
    /**
     * 消息
     */
    private String message;
    
    private Date date =new Date();
    
    
    public Group(){
        
    }

    public Group(String className) {
        super();
        this.className = className;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Group other = (Group) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
    public HTLevel getLevel() {
        return level;
    }
    public void setLevel(HTLevel level) {
        this.level = level;
    }
 
    public Throwable getThrowable() {
        return throwable;
    }
    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
    public String getThreadId() {
        return threadId;
    }
    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public JarInfo getJarInfo() {
        return jarInfo;
    }

    public void setJarInfo(JarInfo jarInfo) {
        this.jarInfo = jarInfo;
    }

    public WarInfo getWarInfo() {
        return warInfo;
    }

    public void setWarInfo(WarInfo warInfo) {
        this.warInfo = warInfo;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }
    
    
   
}
