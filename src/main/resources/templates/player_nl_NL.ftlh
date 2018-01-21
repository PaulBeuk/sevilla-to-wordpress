<div class="interne_scores">
[accordion]
[toggle title="Mijn cijfers" state="closed"]
	<table class="table table-striped table-hover player_overview">
		<thead>
	   		<tr>
	   			<th colspan="2">Speler</th>
	   			<th>m/v</th>
	   			<th>Pos</th>
	   			<th>#</th>
	   			<th>1</th>
	   			<th>=</th>
	   			<th>0</th>
	   			<th>Score</th>
	   			<th>Perc</th>
	   			<th>TPR</th>
	   			<th>Elo</th>
	   		</tr>
	   	</thead>
	   	<tbody>
	   		<tr>
	   			<td>
	   				<a href="${profilePrefix}${name?replace(' |-', '', 'r')}" target="_parent">
	   					<img src="${avatarPrefix}${(name?lower_case)?replace(' |-', '', 'r')}" 
	   						title="Wie is ${name}?" 
	   						class="avatar wp-user-avatar-30 avatar-scores alignnone photo" 
	   						onError="this.onError=null;this.src=&quot;${defaultAvatarPrefix}&quot;;">
	   				</a>
	   			</td>
	   			<td>
	   				<a href="${profilePrefix}${name?replace(' |-', '', 'r')}" target="_parent">${name}</a>
	   			</td>
	   			<td><#if gender??>${gender}</#if></td>
	   			<td>${pos}</td>
	   			<td>${games}</td>
	   			<td>${wins}</td>
	   			<td>${draws}</td>
	   			<td>${losses}</td>
	   			<td>${score}</td>
	   			<td>${percentage?string["0.#"]}</td>
	   			<td>${tpr}</td>
	   			<td>${iRtg}</td>
	   		</tr>
	   	</tbody>
	</table>
[/toggle]
[toggle title="Mijn ronde resultaten deze competitie" state="open"]
<table class="table table-striped table-hover player_results">
	<thead>
		<tr>
			<th>R</th>
			<th colspan="2">Wit</th>
			<th/><th colspan="2">Zwart</th>
		</tr>
	</thead>
	<tbody>
	<#list roundResults.games as game>
		<#if game.absent>
		<tr>
			<td>${game.round}</td>
			<td colspan="5">${game.result}</td>
		</tr>
		<#else>
		<tr>
			<td>${game.round}</td>
			<#if game.iAmWhite>
			<td>
			   <a href="${profilePrefix}${game.white?replace(' |-', '', 'r')}" target="_parent">
			      <img src="${avatarPrefix}${(game.white?lower_case)?replace(' |-', '', 'r')}" title="Naar resultaten van ${game.white}"
			          class="avatar wp-user-avatar-30 avatar-scores alignnone photo"
			          onError="this.onError=null;this.src=&quot;${defaultAvatarPrefix}&quot;;">
			   </a>
			</td>
			<td>
			   <a href="${profilePrefix}${game.white?replace(' |-', '', 'r')}" target="_parent">
			     ${game.white}
			   </a>
			</td>
			<#else>
			<td>
			   <a href="${slugPrefix}${game.white?replace(' ', '-')}" target="_parent">
			      <img src="${avatarPrefix}${(game.white?lower_case)?replace(' |-', '', 'r')}" title="Naar resultaten van ${game.white}"
			          class="avatar wp-user-avatar-30 avatar-scores alignnone photo"
			          onError="this.onError=null;this.src=&quot;${defaultAvatarPrefix}&quot;;">
			   </a>
			</td>
			<td>
			   <a href="${slugPrefix}${game.white?replace(' ', '-')}" target="_parent">
			     ${game.white}
			   </a>
			</td>
			</#if>	
			<td>${game.result}</td>
			<#if game.iAmWhite>
			<td>
			   <a href="${slugPrefix}${game.black?replace(' ', '-')}" target="_parent">
			      <img src="${avatarPrefix}${(game.black?lower_case)?replace(' |-', '', 'r')}" title="Naar resultaten van ${game.black}"
			          class="avatar wp-user-avatar-30 avatar-scores alignnone photo"
			          onError="this.onError=null;this.src=&quot;${defaultAvatarPrefix}&quot;;">
			   </a>
			</td>
			<td>
			   <a href="${slugPrefix}${game.black?replace(' ', '-')}" target="_parent">
			     ${game.black}
			   </a>
			</td>
			<#else>
			<td>
			   <a href="${profilePrefix}${game.black?replace(' |-', '', 'r')}" target="_parent">
			      <img src="${avatarPrefix}${(game.black?lower_case)?replace(' |-', '', 'r')}" title="Naar resultaten van ${game.black}"
			          class="avatar wp-user-avatar-30 avatar-scores alignnone photo"
			          onError="this.onError=null;this.src=&quot;${defaultAvatarPrefix}&quot;;">
			   </a>
			</td>
			<td>
			   <a href="${profilePrefix}${game.black?replace(' |-', '', 'r')}" target="_parent">
			     ${game.black}
			   </a>
			</td>
			</#if>
		</tr>
		</#if>
	</#list>
	</tbody>
</table>
[/toggle]
[toggle title="Naar mijn resultaten van eerdere competities" state="closed"]
<div class="interne_lijsten">
	<lu class="competitions">
		<#list myCompetitions as title>
			<li><a href="${parentSlug}/${title?replace(' ', '-')}" target="_parent">${title}</a></li>
		</#list>
	</lu>
</div>
[/toggle]
[toggle title="De lijst met interne competities" state="closed"]
<div class="interne_lijsten">
	<lu class="competitions">
		<#list competitions as title>
			<li><a href="${parentSlug}/${title?replace(' ', '-')}" target="_parent">${title}</a></li>
		</#list>
	</lu>
</div>
[/toggle]
[/accordion]
</div>