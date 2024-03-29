package com.joysdk.common.web.context;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.joysdk.common.util.FilterUtil;

public class SystemProperties {

    final static Logger loger=LoggerFactory.getLogger(SystemProperties.class);

    private static Properties props;

    /**
     * 加载指定的多个资源文件。其中资源文件是以"a.properities \n b.properities 形式给出
     * @param filename
     */
    public static void loadPropertyFile(String filename) {
        loger.info("Loading system initialize rules file from:" + filename);
        props=new Properties();
        InputStream in=null;
        try {
            List<String> filenames=FilterUtil.spliteUrlPatterns(filename);

            if(filenames != null) {
                for(String filename1: filenames) {
                    in=SystemProperties.class.getClassLoader().getResourceAsStream(filename1);
                    props.load(in);
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            loger.error("Fail to load system initialize rules file.", ex);
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch(java.io.IOException e) {
                    e.printStackTrace();
                    loger.error("I/O Exception at close InputStream.");
                }
            }
        }
    }

    
    /**
     * 模式匹配的方式加载属性文件，<b>但是在jar中的文件需要写全名，不能通过模式匹配加载</b>
     * @param filename
     */
    public static void loadPropertyFileByPatterns(String files) {
        loger.info("Loading system initialize rules file from:" + files);
        props=new Properties();
        InputStream in=null;
        try {
            List<String> filenames=FilterUtil.spliteUrlPatterns(files);
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(); 
            if(filenames != null) {
                for(String filename: filenames) {
                    Resource[] resources=resolver.getResources(filename);  
                    for(Resource re : resources){
                        in=re.getInputStream();
                        System.out.println(re.getFilename());
                        props.load(in);
                    }
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            loger.error("Fail to load system initialize rules file.", ex);
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch(java.io.IOException e) {
                    e.printStackTrace();
                    loger.error("I/O Exception at close InputStream.");
                }
            }
        }
    }

    
    /**
     * 加载指定的多个资源文件。
     * @param filename
     */
    public static void loadPropertyFiles(List<String> filenames) {
        props=new Properties();
        InputStream in=null;
        try {
            if(filenames != null) {
                for(String filename1: filenames) {
                    in=SystemProperties.class.getClassLoader().getResourceAsStream(filename1);
                    props.load(in);
                }
            }
        } catch(Exception ex) {
            loger.error("Fail to load system initialize rules file.", ex);
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch(java.io.IOException e) {
                    loger.error("I/O Exception at close InputStream.");
                }
            }
        }
    }

    /**
     * Get the value in system property file by the key
     * @param key key String
     * @return String
     */
    public static String getProperty(String key) {
        Object obj=props.get(key);
        if(obj == null) {
            return null;
        } else {
            return obj.toString();
        }
    }

    public static String getMsg(int errType) {
        Object obj=props.get("sys.err." + errType);
        if(obj == null) {
            return null;
        } else {
            return obj.toString();
        }
    }

    /**
     * Get the value in system property file by the key, with a default value,once the value is not set.
     * @param key key String
     * @param defaultValue defaultValue String
     * @return String
     */
    public static String getProperty(String key, String defaultValue) {
        if(props == null)
            return defaultValue;
        Object obj=props.get(key);
        if(obj != null) {
            return obj.toString();
        } else {
            return defaultValue;
        }
    }
}
