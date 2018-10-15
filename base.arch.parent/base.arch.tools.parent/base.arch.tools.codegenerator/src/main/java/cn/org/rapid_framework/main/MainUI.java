package cn.org.rapid_framework.main;

import cn.org.rapid_framework.generator.GeneratorFacade;


public class MainUI {
    public static void main(String[]args)throws Exception{
       // GeneratorFacade g = new GeneratorFacade();
        //g.deleteOutRootDir();
        //删除生成器的输出目录//
        //g.generateByAllTable();
        //通过数据库表生成文件,template为模板的根目录
        
        //.//g.generateByAllTable();
        //g.generateByTable("account");
        //自动搜索数据库中的所有表并生成文件,template为模板的根目录
        //g.generateByClass(Blog.class,"template_clazz");
        //  g.deleteByTable("table_name", "template");
        //删除生成的文件
        //GeneratorFacade.printAllTableNames();//打印数据库所有的表名称
       /* Generator generator = g.getGenerator();
        generator.setTemplateRootDir("D:\\worksp
        ace_code\\generator\\src\\main\\resources\\template");
        g.generateByTable("account");  //通过数据库表生成文件，注意oracle需要制定schema以及表名大小
*/        //g.generateByAllTable(); 生成所有表
        GeneratorFacade generatorFacade = new GeneratorFacade();
        generatorFacade.deleteOutRootDir();
        /***
         * generateByTable 传入* 则生成所有表
         */
        generatorFacade.generateByTable("t_metaq_message");
      //g.generateByAllTable(); 生成所有表
    }
}