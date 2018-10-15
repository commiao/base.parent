package base.arch.component.cache.redis.bean;
/**
 * 食物名称
 * @author jannal
 */
public class Food {
    /**
     * 名称
     */
    private String name;
    /**
     * 重量
     */
    private double weight;
    /**
     * 来源
     */
    private String source;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    
}
