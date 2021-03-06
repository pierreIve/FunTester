package com.fun.config

import com.fun.frame.SourceCode
import com.fun.utils.WriteRead
import com.alibaba.fastjson.JSONObject
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * 读取配置工具
 */
@SuppressFBWarnings("MS_SHOULD_BE_FINAL")
class PropertyUtils extends SourceCode {

    public static Logger logger = LoggerFactory.getLogger(PropertyUtils.class)

    /**
     * 获取指定.properties配置文件中所以的数据
     * @param propertyName
     *        调用方式：
     *            1.配置文件放在resource源包下，不用加后缀
     *              PropertiesUtil.getAllMessage("message")
     *            2.放在包里面的
     *              PropertiesUtil.getAllMessage("com.test.message")
     * @return
     */
    static Property getProperties(String propertyName) {
        logger.debug("读取配置文件：{}", propertyName)
        try {
            new Property(ResourceBundle.getBundle(propertyName.trim()))
        } catch (MissingResourceException e) {
            getLocalProperties(WORK_SPACE + propertyName + ".properties")
        }
    }

/**
 * 获取指定路径下的文件配置
 * @param filePath
 * @return
 */
    static Property getLocalProperties(String filePath) {
        logger.debug("读取配置文件：{}", filePath)
        try {
            new Property(WriteRead.readTxtByJson(filePath,FILTER))
        } catch (MissingResourceException e) {
            logger.warn("找不到配置文件", e)
            new Property()
        }
    }

    static Property getPropertiesByFile(String propertyName) {
        logger.debug("读取配置文件：{}", propertyName)
        try {
            new Property(WriteRead.readTxtByJson(WORK_SPACE + propertyName,FILTER))
        } catch (MissingResourceException e) {
            logger.warn("找不到配置文件", e)
            new Property()
        }
    }

/**
 * 配置项
 */
    static class Property {

        Map<String, String> properties = new HashMap<>()

        def Property(ResourceBundle resourceBundle) {
            def set = resourceBundle.keySet()
            for (def key in set) {
                properties.put key, resourceBundle.getString(key)
            }
        }

        def Property(JSONObject json) {
            properties.putAll(json)
        }

/**
 * 获取string类型
 * @param name
 * @return
 */
        String getProperty(String name) {
            PropertyUtils.logger.debug("获取配置项：{}", name)
            if (contain(name)) properties.get(name)
        }

/**
 * 获取int值
 * @param name
 * @return
 */
        int getPropertyInt(String name) {
            changeStringToInt(properties.get(name))
        }
/**
 * 获取long值
 * @param name
 * @return
 */
        int getPropertyLong(String name) {
            Long.valueOf(properties.get(name))
        }

/**
 * 获取boolean值
 * @param name
 * @return
 */
        boolean getPropertyBoolean(String name) {
            changeStringToBoolean(properties.get(name))
        }

/**
 * 获取数组
 * @param name
 * @return
 */
        String[] getArrays(String name) {
            getProperty(name).split(",")
        }

/**
 * 返回配置文件的配置项的大小
 * @return
 */
        int size() {
            properties.size()
        }

/**
 * 输出所以配置项
 * @return
 */
        def printAll() {
            output properties
        }

/**
 * 是否有配置项
 * @param key
 * @return
 */
        boolean contain(def key) {
            boolean var = properties.containsKey key asBoolean()
            if (!var) PropertyUtils.logger.error("配置{}未发现！", key)
            var
        }
    }
}
