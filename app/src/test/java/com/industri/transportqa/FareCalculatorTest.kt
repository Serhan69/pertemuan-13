package com.industri.transportqa

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertThrows

class FareCalculatorTest {

    private lateinit var calculator: FareCalculator

    @Before
    fun setUp() {
        calculator = FareCalculator()
    }

    @Test
    fun isValidNik_valid16Digits_returnsTrue() {
        val result = calculator.isValidNik("3209123456780001")
        assertThat(result).isTrue()
    }

    @Test
    fun isValidNik_lessThan16Digits_returnsFalse() {
        val result = calculator.isValidNik("320912345")
        assertThat(result).isFalse()
    }

    @Test
    fun isValidNik_containsLetters_returnsFalse() {
        val result = calculator.isValidNik("320912345678000A")
        assertThat(result).isFalse()
    }

    @Test
    fun calculateTotalFare_seniorCitizen65_returnsDiscountedFarePlusBaggage() {
        val total = calculator.calculateTotalFare(500000.0, 65, 15)
        assertThat(total).isEqualTo(400000.0)
    }

    @Test
    fun calculateTotalFare_adult30_returnsBaseFarePlusExcessBaggage() {
        val total = calculator.calculateTotalFare(500000.0, 30, 25)
        assertThat(total).isEqualTo(625000.0)
    }

    @Test
    fun calculateTotalFare_negativeBaseFare_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException::class.java) {
            calculator.calculateTotalFare(-10000.0, 30, 10)
        }
    }

    @Test
    fun calculateTotalFare_toddlerAge2_returnsZeroFareForFreeBaggage() {
        val total = calculator.calculateTotalFare(500000.0, 2, 10)
        assertThat(total).isEqualTo(0.0)
    }

    @Test
    fun calculateTotalFare_boundaryAge3AndBaggage20_returnsExactBaseFare() {
        val total = calculator.calculateTotalFare(500000.0, 3, 20)
        assertThat(total).isEqualTo(500000.0)
    }
}
