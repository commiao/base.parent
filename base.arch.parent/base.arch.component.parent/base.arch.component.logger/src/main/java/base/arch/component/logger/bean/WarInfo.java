package base.arch.component.logger.bean;

import java.io.Serializable;

public class WarInfo implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 构建时间
     */
    private String buildTime;
    
    /**
     * 启动完成时间
     */
    private String startCompleteTime;

    public String getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }

    public String getStartCompleteTime() {
        return startCompleteTime;
    }

    public void setStartCompleteTime(String startCompleteTime) {
        this.startCompleteTime = startCompleteTime;
    }
    
}
