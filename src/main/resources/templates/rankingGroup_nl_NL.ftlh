<div id="content">
  <ul class="nav nav-tabs">
	<#list ranking as rank>
		<li<#if rank?is_first> class="active"</#if>><a href="#C${rank.key}" data-toggle="tab">${rank.key}</a></li>
	</#list>
  </ul>
  <div class="tab-content">
	<#list ranking as rank>
	  <div id="C${rank.key}" class="tab-pane<#if rank?is_first> active</#if>">
		<table class="table table-striped table-hover <#if rank?is_first>main_ranking<#else>group_ranking</#if>">
			<thead>
				<tr>
					<th>Pos</th>
					<th>Val</th>
					<th colspan="2">Speler</th>
					<th>Score</th>
					<th>T</th>
					<th>W</th>
					<th>R</th>
					<th>V</th>
					<th>TPR</th>
					<th>IRtg</th>
					<th>B</th>
				</tr>
			</thead>
			<tbody>
			<#list rank.value as rr>
				<tr>
					<td>${rr.pos}</td>
					<td>${rr.value}</td>
					<td>
					   <a href="${slugPrefix}${rr.name?replace(' ', '-')}" target="_parent">
					      <img src="${avatarPrefix}${(rr.name?lower_case)?replace(' |-', '', 'r')}" title="Naar resultaten van ${rr.name}"
					          class="avatar wp-user-avatar-30 avatar-scores alignnone photo"
					          onError="this.onError=null;this.src=&quot;${defaultAvatarPrefix}&quot;;">
					   </a>
					</td>
					<td>
					   <a href="${slugPrefix}${rr.name?replace(' ', '-')}" target="_parent">
					     ${rr.name}
					   </a>
					</td>
					<td>${rr.score?string["0.#"]}</td>
					<td>${rr.games}</td>
					<td>${rr.wins}</td>
					<td>${rr.draws}</td>
					<td>${rr.losses}</td>
					<td>${rr.tpr?c}</td>
					<td>${rr.iRtg?c}</td>
					<td>${rr.balance}</td>
				</tr>
			</#list>
			</tbody>
		</table>
	  </div>
	</#list>
  </div>
</div>
