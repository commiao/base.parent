package basic.arch.tools.config.properties;

import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.ConfigFactory;
import org.aeonbits.owner.Reloadable;

@Sources({"classpath:conf/config.properties"})
public interface MysqlJdbcConfig extends Reloadable {

    @Key("config.mysql.url")
    public String url();
    @Key("config.mysql.username")
    public String username();
    @Key("config.mysql.password")
    public String password();
    @Key("config.mysql.driverClass")
    public String driverClass();
    @Key("config.mysql.env")
    public String env();
    @Key("config.mysql.override")
    public String override();
    @Key("config.mysql.name")
    public String name();
    
    

    public  final static class ServerConfigInner{
        public final static MysqlJdbcConfig config = ConfigFactory.create(MysqlJdbcConfig.class);
    }
    public static final MysqlJdbcConfig instance = MysqlJdbcConfig.ServerConfigInner.config;
	
}
