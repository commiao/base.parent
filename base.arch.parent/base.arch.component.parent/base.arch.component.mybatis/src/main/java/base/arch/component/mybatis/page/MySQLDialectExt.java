package base.arch.component.mybatis.page;

import java.util.List;

import org.apache.ibatis.mapping.MappedStatement;

import com.github.miemiedev.mybatis.paginator.dialect.MySQLDialect;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

/***
 * 优化父类源码的一些性能问题(相对于mysql的问题)
 * @author jannal 2015-12-02
 */
public class MySQLDialectExt extends MySQLDialect{

    public MySQLDialectExt(MappedStatement mappedStatement, Object parameterObject, PageBounds pageBounds) {
        super(mappedStatement, parameterObject, pageBounds);
    }
    
    /***
     * 重写获取总记录的sql语句，原始的是子查询，现在优化为不使用子查询，数据量大的情况下性能提升几百倍
     */
    protected String getCountString(String sql){
        return SQLUtils.pageSqlToCountSql(sql);
    }
    
    
    /**
     * 将sql转换为带排序的SQL
     * @param sql SQL语句
     * @return 总记录数的sql
     *在此处定义排序，就不能再mapper.xml中定义排序规则
     * 
     */
    protected String getSortString(String sql, List<Order> orders){
        if(orders == null || orders.isEmpty()){
            return sql;
        }

        StringBuffer buffer = new StringBuffer(sql);
        for(Order order : orders){
            if(order != null){
                buffer.append(" order by ").append(order.toString()).append(", ");
            }

        }
        int length = buffer.length();
        buffer.delete(length-2, length);
        return buffer.toString();
    }

}
