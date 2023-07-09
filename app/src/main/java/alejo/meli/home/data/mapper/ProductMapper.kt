package alejo.meli.home.data.mapper

fun DataForecastDay.toDomainEntity(): ForecastDay = ForecastDay(
    forecastday.map { it.toDomainEntity() }.toMutableList()
)

fun ForecastDay.toDto(): DataForecastDay = DataForecastDay(
    forecastdayItems.map { it.toDto() }.toMutableList()
)