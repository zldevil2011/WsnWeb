<#include 'config.ftl'>

<#assign contextPath = '/WsnWeb/'/>

<#function getCssUrl url>
    <#return contextPath + cssPrefix[url]>
</#function>

<#function getJsUrl url>
    <#return contextPath + jsPrefix[url]>
</#function>

<#function getImageUrl url>
    <#return contextPath + imagePrefix + url>
</#function>
