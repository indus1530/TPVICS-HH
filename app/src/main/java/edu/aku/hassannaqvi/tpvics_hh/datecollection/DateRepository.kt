package edu.aku.hassannaqvi.tpvics_hh.datecollection

import org.threeten.bp.DateTimeException
import org.threeten.bp.LocalDate

class DateRepository {

    companion object {

        private fun getDate(year: Int, month: Int, day: Int): LocalDate? {
            return try {
                run { LocalDate.of(year, month, day) }
            } catch (e: DateTimeException) {
                null
            }
        }

        fun dateValidator(year: Int, month: Int, day: Int): Boolean = getDate(year, month, day) != null

        fun isDateLessThenDOB(year: Int, month: Int, day: Int, dt: LocalDate? = null): Boolean? {
            val calculateDate = getDate(year, month, day)
                    ?: return null
            val localDT = dt ?: LocalDate.now()
            return calculateDate < localDT
        }

        @JvmOverloads
        fun getCalculatedAge(currentDate: LocalDate = LocalDate.now(), year: Int, month: Int, day: Int, allowCurrentAge: Boolean = true): AgeModel? {

            var curdate = currentDate.dayOfMonth
            var curmonth = currentDate.monthValue
            var curyear = currentDate.year

            val calculateDate = getDate(year, month, if (day == 0) curdate else day)
                    ?: return null
            if (allowCurrentAge) {
                if (calculateDate > currentDate) return null
            } else {
                if (calculateDate >= currentDate) return null
            }

            if (day > curdate) {
                curmonth -= 1
                curdate += calculateDate.lengthOfMonth()
            }

            if (month > curmonth) {
                curyear -= 1
                curmonth += 12
            }
            return AgeModel(curdate - day, curmonth - month, curyear - year)
        }

        @JvmOverloads
        fun getDOBFromAge(refDate: LocalDate = LocalDate.now(), year: Int, month: Int, day: Int): LocalDate {
            return run { refDate.minusYears(year.toLong()).minusMonths(month.toLong()).minusDays(day.toLong()) }
        }
    }
}