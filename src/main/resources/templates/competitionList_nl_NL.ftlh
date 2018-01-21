<div class="interne_lijsten">
	<lu class="competitions">
		<#list competitions as title>
			<li><a href="${parentSlug}${title?replace(' ', '-')}" target="_parent">${title}</a></li>
		</#list>
	</lu>
</div>