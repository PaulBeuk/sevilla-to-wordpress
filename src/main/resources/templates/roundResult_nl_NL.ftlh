<#list results as result>
	<table class="table table-striped table-hover round_results">
		<thead>
			<tr>
				<th colspan="2">Wit</th>
				<th/><th colspan="2">Zwart</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>
					<a href="${slugPrefix}${result.white.name?replace(' ', '-')}" target="_parent">
				      <img src="${avatarPrefix}${(result.white.name?lower_case)?replace(' |-', '', 'r')}" title="Naar resultaten van ${result.white.name}"
				          class="avatar wp-user-avatar-30 avatar-scores alignnone photo"
				          onError="this.onError=null;this.src=&quot;${defaultAvatarPrefix}&quot;;">
				   </a>
				</td>
				<td>
				   <a href="${slugPrefix}${result.white.name?replace(' ', '-')}" target="_parent">
				     ${result.white.name}
				   </a>
				</td>
				<td>${result.result}</td>
				<td>
					<a href="${slugPrefix}${result.black.name?replace(' ', '-')}" target="_parent">
				      <img src="${avatarPrefix}${(result.black.name?lower_case)?replace(' |-', '', 'r')}" title="Naar resultaten van ${result.black.name}"
				          class="avatar wp-user-avatar-30 avatar-scores alignnone photo"
				          onError="this.onError=null;this.src=&quot;${defaultAvatarPrefix}&quot;;">
				   </a>
				</td>
				<td>
				   <a href="${slugPrefix}${result.black.name?replace(' ', '-')}" target="_parent">
				     ${result.black.name}
				   </a>
				</td>
			</tr>
		</tbody>
	</table>
</#list>

		