package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};

/**
 * ${table.comment!} Service接口
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

}
</#if>
