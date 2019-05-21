package com.fun.base.bean


import com.fun.frame.SourceCode
import net.sf.json.JSONObject
import org.apache.commons.beanutils.BeanUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class BeanUtil extends SourceCode {
    private static Logger logger = LoggerFactory.getLogger(BeanUtil.class)

/**
 * 从json对象中拷贝属性到bean
 * @param bean
 * @param source
 * @return
 */
   def static copy(BaseBean bean, Object source) {
        try {
            BeanUtils.copyProperties(bean, source)
        } catch (Exception e) {
            logger.warn("拷贝属性失败！", e)
        }
    }

/**
 * 转为json
 * @param bean
 * @return
 */
    static JSONObject toJson(BaseBean bean) {
        JSONObject.fromObject(bean)
    }
}