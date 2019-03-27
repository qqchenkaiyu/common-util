package com.cky.tomcat;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
/**
 * Created by Administrator on 2019/3/27.
 */
public class TomcatUtil {
    public static  void run(){
        run("src/main/webapp","/WEB-INF/classes");

    }
    public static  void run(String  webapp,String classes){
        Tomcat tomcat = new Tomcat();
        String path = TomcatUtil.class.getResource("/").getPath();
        try {
            //必须告知webapp地址，否则视图解析器无法工作
            Context context = tomcat.addWebapp("/", new File(webapp).getAbsolutePath());
            //告知java代码地址，否则tomcat就成了静态资源服务器
            StandardRoot standardRoot = new StandardRoot(context);
            standardRoot.addPreResources(new DirResourceSet(standardRoot,classes,path,"/"));
            context.setResources(standardRoot);
            tomcat.start();
            tomcat.getServer().await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
