package com.joysdk.common.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joysdk.common.web.context.SystemProperties;

public class LoadPropertiesUtil {

    final static private Logger loger=LoggerFactory.getLogger(LoadPropertiesUtil.class);

    final static private String SERVER_CONFIG_FILE="server.properties";

    final static private String KEY_PROJECT_CONFIG_FILE_NAMES="project.loading.properties";

    final static private String CONFIG_FILE_SEPERATOR=";";

    /**
     * load properties 文件。
     */
    public static void loadProperties() {

        List<String> projectProperties=LoadPropertiesUtil.getProjectProperties(SERVER_CONFIG_FILE);

        if(projectProperties != null) {
            SystemProperties.loadPropertyFiles(projectProperties);
        }
    }

    private static List<String> getProjectProperties(String serverConfigFile) {
        loger.info("Loading system initialize rules file from:" + SERVER_CONFIG_FILE);

        List<String> projectProperties=null;

        Properties props=new Properties();
        InputStream in=null;
        try {
            in=SystemProperties.class.getClassLoader().getResourceAsStream(serverConfigFile);
            props.load(in);

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

        String projectLoadingProperties=props.getProperty(KEY_PROJECT_CONFIG_FILE_NAMES);

        if(StringUtils.isNotBlank(projectLoadingProperties)) {
            projectProperties=LoadPropertiesUtil.spliteFiless(projectLoadingProperties);
        }

        return projectProperties;
    }

    /**
     * @param projectLoadingProperties
     * @return
     */
    private static List<String> spliteFiless(String projectLoadingProperties) {
        List<String> properties=null;
        if(StringUtils.isNotBlank(projectLoadingProperties)) {
            String[] tmp=StringUtils.split(projectLoadingProperties, CONFIG_FILE_SEPERATOR);
            properties=new ArrayList<String>();

            for(String url: tmp) {
                if(StringUtils.isNotBlank(url)) {
                    properties.add(url.trim());
                }
            }
        }

        return properties;
    }

}
