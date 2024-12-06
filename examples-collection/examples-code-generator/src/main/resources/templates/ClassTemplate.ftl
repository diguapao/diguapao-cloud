<#if package??>
    package ${package};
</#if>

<#if originalClassContent??>
    ${originalClassContent}
<#else>
    public class ${newClassName} {

    <#-- 保留原有的字段和注释 -->
    <#include "OriginalFieldsAndMethods.ftl">

    }
</#if>