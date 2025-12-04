package com.cgadoury.onthebench.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.cgadoury.onthebench.mvvm.TeamsViewModel
import com.cgadoury.onthebench.api.model.standing.Standing
import com.cgadoury.onthebench.ui.theme.TeamColors
import com.cgadoury.onthebench.utility.loadSvgImage

/**
 * Purpose - Teams Screen - display NHL teams for the current season
 * @author Colton Gadoury
 * @param modifier: The modifier for the screen
 * @param teamsViewModel: The view model where initial team data is retrieved
 * @constructor Emits a new Teams Screen composable
 * @return Unit
 */
@Composable
fun TeamsScreen(
    modifier: Modifier,
    teamsViewModel: TeamsViewModel,
    navController: NavController
){
    teamsViewModel.getFavouriteTeams()

    val teams by teamsViewModel.standingsResponse
    val favouriteTeams by teamsViewModel.favouriteTeamsResponse
    val hasFavourites = !favouriteTeams.isEmpty()

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = "Favourite Teams",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge
            )
        }

        item {
            FavouriteTeamRow(
                modifier = modifier.padding(horizontal = 8.dp, vertical = 12.dp),
                favouriteTeams = favouriteTeams,
                navController = navController
            )
        }

        item {
            HorizontalDivider(modifier = modifier.padding(horizontal = 12.dp, vertical = 24.dp))
        }

        item {
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                text = "Standings",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium
            )
        }

        items(teams) { team ->
            TeamCard(
                modifier = modifier.padding(6.dp),
                team = team,
                navController = navController
            )
        }
    }
}

/**
 * Purpose - Team Card - display a team
 * @author Colton Gadoury
 * @param modifier: The modifier
 * @param team: The team to display
 * @constructor Emits a new Team Card composable
 * @return Unit
 */
@Composable
fun TeamCard(
    modifier: Modifier,
    team: Standing?,
    navController: NavController
) {
    val teamColor = TeamColors.colors[team?.teamAbbrev?.default] ?: Color.White
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, teamColor),
        colors = CardDefaults.cardColors(
            containerColor = teamColor.copy(alpha = 0.03f)
        ),
        onClick = {
            navController.navigate("teamDetail/${team?.teamAbbrev?.default}")
        }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(65.dp)
                    .clip(CircleShape)
                    .background(Color.Gray.copy(alpha = 0.3f))
                    .align(Alignment.CenterVertically),
                model = ImageRequest.Builder(
                    LocalContext.current
                ).data(team?.teamLogo)
                    .build(),
                contentDescription = null,
                imageLoader = loadSvgImage(context = LocalContext.current)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = team?.teamName?.default.orEmpty(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "${team?.conferenceName}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )

                    VerticalDivider(
                        modifier= Modifier
                            .height(20.dp)
                            .padding(horizontal = 20.dp),
                        thickness = 2.dp,
                        color = Color.Black
                    )

                    Text(
                        text = "${team?.divisionName}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

/**
 * Purpose - favourite team row - displays favourite teams in rows of 3
 * @param modifier: The application modifier
 * @param favouriteTeams: A list of favourite teams
 * @param navController: The application navigation controller
 * @return Unit
 */
@Composable
fun FavouriteTeamRow(
    modifier: Modifier,
    favouriteTeams: List<Standing>,
    navController: NavController
) {
    FlowRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        itemVerticalAlignment = Alignment.CenterVertically,
        maxItemsInEachRow = 3
    ) {
        favouriteTeams.forEach { team ->
            val teamColor = TeamColors.colors[team.teamAbbrev.default]

            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                AsyncImage(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(teamColor!!.copy(alpha = 0.8f))
                        .border(
                            BorderStroke(width = 2.dp, teamColor),
                            shape = CircleShape
                        )
                        .clickable {
                            navController.navigate("teamDetail/${team.teamAbbrev.default}")
                                   },
                    model = ImageRequest.Builder(
                        LocalContext.current
                    ).data(team.teamLogo)
                        .build(),
                    contentDescription = team.teamName.default + " Logo",
                    imageLoader = loadSvgImage(
                        context = LocalContext.current
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = team.teamName.default.split(" ").last(),
                    fontSize = 14.sp,
                    letterSpacing = 2.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}