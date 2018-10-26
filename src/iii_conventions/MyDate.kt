package iii_conventions

data class RepeatedTimeInterval(val interval: TimeInterval, val repeat : Int = 1)

enum class TimeInterval {
    DAY(),
    WEEK(),
    YEAR();

    operator fun times(i: Int): RepeatedTimeInterval {
        return RepeatedTimeInterval(this, i)
    }
}

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) {
    operator fun compareTo(date2: MyDate): Int {
        return when {
            this.year != date2.year -> this.year.compareTo(date2.year)
            this.month != date2.month -> this.month.compareTo(date2.month)
            else -> this.dayOfMonth.compareTo(date2.dayOfMonth)
        }
    }

    fun nextDay(days : Int = 1) : MyDate {

        var d = this.dayOfMonth
        var m = this.month
        var y = this.year
        val daysOfMonth = when (m) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            2 -> if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) 29 else 28
            else -> 30
        }

        var remainder = 0
        when {
            days <= daysOfMonth - this.dayOfMonth -> d += days
            days <= daysOfMonth - this.dayOfMonth + 28 -> {
                d += days - daysOfMonth
                m += 1
            }
            else -> {
                remainder = days - 28 - (daysOfMonth - this.dayOfMonth)
                d = 28
                m += 1
            }
        }
        if (m > 12) {
            m = 1
            y += 1
        }
        val result = MyDate(y, m, d)
        if (remainder > 0) {
            return result.nextDay(remainder)
        }
        return result
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)


class DateRange(val start: MyDate, val endInclusive: MyDate) {
    operator fun contains(d: MyDate) = start <= d && d <= endInclusive
    operator fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            var currentDate = start.copy()
            override fun hasNext(): Boolean {
                return currentDate <= endInclusive
            }

            override fun next(): MyDate {
                val retDate = currentDate
                currentDate = currentDate.nextDay()
                return retDate
            }
        }
    }
}
