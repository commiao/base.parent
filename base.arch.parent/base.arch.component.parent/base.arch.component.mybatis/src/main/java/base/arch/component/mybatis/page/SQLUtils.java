package base.arch.component.mybatis.page;

import org.apache.commons.lang3.StringUtils;

/***
 * @author jannal
 */
public class SQLUtils {

    /***
     * 将分页查询sql(pageSql)转换成查询总记录数sql(countSql)
     * @param pageSql
     * @return
     */
   public static String pageSqlToCountSql(String pageSql){
       int fromIndex = pageSql.toUpperCase().indexOf("FROM".toUpperCase());
      // System.out.println("From的索引是"+fromIndex);
       int selectIndex = pageSql.toUpperCase().indexOf("SELECT".toUpperCase());
       //System.out.println("Select的索引是"+selectIndex);
       //从select截取到from
       String substring = pageSql.substring(selectIndex, fromIndex+4);
       String replace = StringUtils.replace(pageSql, substring, " SELECT count(*) FROM ");
       //System.out.println("最终的字符串"+replace);
       return replace;
   }
   
   public static void main(String[] args) {
     String sql = "select * from test a";
     String sql2 = "select * from from test a";
     String sql3 = "select a.name,a.test,a.age  from test a";
     String sql4 = "select a.name,a.test,a.age  from test a";
     String sql5=" select from aa";
     String sql6="    select aa.test FROM aa ";
     String sql7="    SELECT aa.test from aa ";
     String sql8="    SELECT aa.test FROM aa ";
     
     
     pageSqlToCountSql(sql);
     pageSqlToCountSql(sql2);
     pageSqlToCountSql(sql3);
     pageSqlToCountSql(sql4);
     pageSqlToCountSql(sql5);
     pageSqlToCountSql(sql6);
     pageSqlToCountSql(sql7);
     pageSqlToCountSql(sql8);
   }
}
