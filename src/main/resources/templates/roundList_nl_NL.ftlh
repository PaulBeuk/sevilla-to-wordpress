<div class="interne_lijsten">
<lu class="previous_rounds">
<#list 1..totalRounds as ro>
	<li>
		<a href="${slugPrefix}<#if ro?is_last>stand<#else>ronde-${ro}</#if>" target="_parent"><#if ro?is_last>stand<#else>ronde ${ro}</#if></a>
	</li>
</#list>
</lu>
</div>