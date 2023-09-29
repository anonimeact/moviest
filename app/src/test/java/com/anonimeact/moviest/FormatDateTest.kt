package com.anonimeact.moviest

import com.anonimeact.moviest.utils.formatLongDate
import com.anonimeact.moviest.utils.formatShortDate
import org.junit.Assert.assertEquals
import org.junit.Test

class FormatDateTest {
    @Test
    fun testFormatLongDate() {
        val date = "2023-08-30T08:36:28.719Z"
        val formattedDate = date.formatLongDate()
        assertEquals("30 Aug 2023 08:36", formattedDate)
    }
    @Test
    fun testFormatShortDate() {
        val date = "2023-08-30"
        val formattedDate = date.formatShortDate()
        assertEquals("30 August 2023", formattedDate)
    }
}