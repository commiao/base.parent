package base.arch.component.logger.bean;

import java.io.Serializable;
/**
 * CreateDate:2016年11月10日下午4:28:20 
 * @Description: jar包信息  
 * @author:jannal
 * @version V1.0
 */
public class JarInfo implements Serializable{

    /**
     * serialVersionUID 
     */
    private static final long serialVersionUID = 1L;

    private String jarName;
    
    private String jarPath;
    
    /**
     * 构建时间
     */
    private String buildTime;

    /**
     * 项目版本
     */
    private String projectVersion;
    
    /**
     * 项目名称
     */
    private String projectName;
    
    
    public String getJarName() {
        return jarName;
    }

    public void setJarName(String jarName) {
        this.jarName = jarName;
    }

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }

    public String getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }

    public String getProjectVersion() {
        return projectVersion;
    }

    public void setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
}
