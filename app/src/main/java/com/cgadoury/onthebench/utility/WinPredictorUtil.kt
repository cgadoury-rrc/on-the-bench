package com.cgadoury.onthebench.utility

import com.cgadoury.onthebench.api.model.standing.Standing

/**
 * Purpose - predict game winner - predict a game winner based on a number of season stats
 * and return their predicted chance to win
 * @param homeTeam: The home team to compare
 * @param awayTeam: The away team to compare
 * @return Triple<Standing, Int, Int>
 */
fun predictGameWinner(
    homeTeam: Standing,
    awayTeam: Standing,
    xFactor: Int
    ): Triple<Standing, Int, Int> {

    val teamStatComparisons = listOfNotNull(
        when {
            homeTeam.points > awayTeam.points -> homeTeam
            awayTeam.points > homeTeam.points -> awayTeam
            else -> null
        },
        when {
            homeTeam.winPctg > awayTeam.winPctg -> homeTeam
            awayTeam.winPctg > homeTeam.winPctg -> awayTeam
            else -> null
        },
        when {
            homeTeam.homeWins > awayTeam.roadWins -> homeTeam
            awayTeam.roadWins > homeTeam.homeWins -> awayTeam
            else -> null
        },
        when {
            homeTeam.goalDifferential > awayTeam.goalDifferential -> homeTeam
            awayTeam.goalDifferential > homeTeam.goalDifferential -> awayTeam
            else -> null
        },
        when {
            homeTeam.regulationWins > awayTeam.regulationWins -> homeTeam
            awayTeam.regulationWins > homeTeam.regulationWins -> awayTeam
            else -> null
        },
        when {
            homeTeam.l10Wins > awayTeam.l10Wins -> homeTeam
            awayTeam.l10Wins > homeTeam.l10Wins -> awayTeam
            else -> null
        },
        when {
            isOnWinStreak(homeTeam.streakCode) && !isOnWinStreak(awayTeam.streakCode) -> homeTeam
            isOnWinStreak(awayTeam.streakCode) && !isOnWinStreak(homeTeam.streakCode) -> awayTeam
            else -> null
        },
        when {
            homeTeam.goalAgainst < awayTeam.goalAgainst -> homeTeam
            awayTeam.goalAgainst < homeTeam.goalAgainst -> awayTeam
            else -> null
        },
        when {
            xFactor > 85 -> homeTeam
            xFactor < 10 -> awayTeam
            else -> null
        }
    )
    val statCount = teamStatComparisons.count()
    val statsHeldPerTeam = teamStatComparisons.groupingBy { it }.eachCount()
    val comparisonWinner = statsHeldPerTeam.maxBy { it.value }
    val predictedWinner = comparisonWinner.key
    val predictedWinnerValue = ((comparisonWinner.value.toDouble() / statCount) * 100).toInt()
    val predictedLoserValue = 100 - predictedWinnerValue

    return Triple(
        predictedWinner,
        predictedWinnerValue,
        predictedLoserValue
    )
}

/**
 * Purpose - is on win streak - determines if a team is on a win streak
 * @param streakCode: The teams current streak code
 * @return Boolean: True if streak code == "W"; else false
 */
private fun isOnWinStreak(streakCode: String): Boolean {
    return streakCode == "W"
}