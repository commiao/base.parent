package basic.arch.tools.config.properties;

import java.io.Serializable;

public class ConfigInfo implements Serializable {

	private static final long serialVersionUID = -1804110130106712741L;

	private String name;
    
    private String value;
    
    
    private String key;


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getValue() {
        return value;
    }


    public void setValue(String value) {
        this.value = value;
    }


    public String getKey() {
        return key;
    }


    public void setKey(String key) {
        this.key = key;
    }


    @Override
    public String toString() {
        return "ConfigInfo [name=" + name + ", value=" + value + ", key=" + key + "]";
    }
}
