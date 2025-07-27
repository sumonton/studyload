package com.smc.mp.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @Date 2022/4/16
 * @Author smc
 * @Description:自定义全局操作
 */
public class MySqlInjector extends AbstractMethod {
    /**
     * 扩展inject方法，完成自定义操作
     * @param mapperClass
     * @param modelClass
     * @param tableInfo
     * @return
     */
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        //注入sql语句
        String sql = "update "+ tableInfo.getTableName()+" set is_delete = 1 where id = 9";
        //注入方法名，要要mapper中的方法名一只
        String id = "logicDelete";
        //构造sqlSource
        SqlSource sqlSource = languageDriver.createSqlSource(configuration,sql,modelClass);
        return this.addInsertMappedStatement(mapperClass, modelClass, id, sqlSource, new NoKeyGenerator(), null, null);
    }
}
