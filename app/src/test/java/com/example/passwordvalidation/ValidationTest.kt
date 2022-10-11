package com.example.passwordvalidation

import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * 1. minLength - Минимальная длина
 * 2. upperCaseLetterCount - Верхний регистр
 * 3. lowerCaseLetterCount - Нижний регистр
 * 4. numberCount - Количество цифр
 * 5. specialSignsCount - Спец символы
 */

class ValidationTest {

    //region no arguments into constructor
    @Test
    fun test_default() {
        val validation: Validation = Validation.Password()
        val sourceList = listOf(
            "A", "G", "O", "U", "F",
            "f", "h", "i", "l",
            "#", "!", "5", "7",
            "12G", "12K3", "1T234", "12O345",
            "asK", "asLd", "asUdf", "aOsdfg",
            "AS", "ASD", "ASDF", "ASDFG",
            "!E@", "!U@#", "!@K#$", "!@D#$%",
            "As", "As3", "sA@3", "Fs1%l",
            "dj12GkJ#@Kf^"
        )
        val actualLiat: List<Validation.Result> = sourceList.map {
            validation.isValid(it)
        }
        actualLiat.forEach {
            assertEquals(Validation.Result.Valid, it)
        }
    }

    @Test
    fun test_empty() {
        val validation: Validation = Validation.Password()
        val actual = validation.isValid("")
        assertEquals(Validation.Result.MinLengthInsufficient(minLength = 1), actual)
    }
    //endregion

    //region min Length
    @Test
    fun test_min_length_valid() {
        val validation: Validation = Validation.Password(minLength = 2)
        val sourceList = listOf(
            "12", "123", "1234", "12345",
            "as", "asd", "asdf", "asdfg",
            "AS", "ASD", "ASDF", "ASDFG",
            "!@", "!@#", "!@#$", "!@#$%",
            "As", "As3", "sA@3", "Fs1%l",
            "dj12GkJ#@Kf^"
        )
        val actualLiat: List<Validation.Result> = sourceList.map {
            validation.isValid(it)
        }
        actualLiat.forEach {
            assertEquals(Validation.Result.Valid, it)
        }
    }

    @Test
    fun test_min_length_invalid() {
        val validation: Validation = Validation.Password(minLength = 2)
        val sourceList = listOf("", "a", "D", "%", "4")
        val actualLiat: List<Validation.Result> = sourceList.map {
            validation.isValid(it)
        }
        actualLiat.forEach {
            assertEquals(Validation.Result.MinLengthInsufficient(minLength = 2), it)
        }
    }

    @Test(expected = IllegalStateException::class)
    fun test_min_length_zero() {
        Validation.Password(minLength = 0)
    }

    @Test(expected = IllegalStateException::class)
    fun test_min_length_negative() {
        Validation.Password(minLength = -1)
    }
    //endregion

    //region upper case letter count
    @Test
    fun test_upperCaseLetterCount_valid() {
        val validation: Validation = Validation.Password(upperCaseLetterCount = 1)
        val sourceList = listOf(
            "A", "G", "O", "U", "F",
            "12G", "12K3", "1T234", "12O345",
            "asK", "asLd", "asUdf", "aOsdfg",
            "AS", "ASD", "ASDF", "ASDFG",
            "!E@", "!U@#", "!@K#$", "!@D#$%",
            "As", "As3", "sA@3", "Fs1%l",
            "dj12GkJ#@Kf^"
        )
        val actualLiat: List<Validation.Result> = sourceList.map {
            validation.isValid(it)
        }
        actualLiat.forEach {
            assertEquals(Validation.Result.Valid, it)
        }
    }

    @Test
    fun test_upperCaseLetterCount_invalid() {
        val validation: Validation = Validation.Password(upperCaseLetterCount = 1)
        val sourceList = listOf(
            "g", "1", " ", "^"
        )
        val actualLiat: List<Validation.Result> = sourceList.map {
            validation.isValid(it)
        }
        actualLiat.forEach {
            assertEquals(
                Validation.Result.UpperCaseLetterCountInsufficient
                    (upperCaseLetterCount = 1), it
            )
        }
    }

    @Test(expected = IllegalStateException::class)
    fun test_upperCaseLetterCount_negative() {
        Validation.Password(upperCaseLetterCount = -1)
    }
    //endregion

    //region lower case letters count
    @Test
    fun test_lowerCaseLetterCount_valid() {
        val validation: Validation = Validation.Password(lowerCaseLetterCount = 1)
        val sourceList = listOf(
            "i", "g", "o", "u", "f",
            "12G", "12K3", "1T234", "12O345",
            "asK", "asLd", "asUdf", "aOsdfg",
            "AaS", "AfSD", "ASjDF", "ASDrFG",
            "!E@", "!U@#", "!@K#$", "!@D#$%",
            "As", "As3", "sA@3", "Fs1%l",
            "dj12GkJ#@Kf^"
        )
        val actualLiat: List<Validation.Result> = sourceList.map {
            validation.isValid(it)
        }
        actualLiat.forEach {
            assertEquals(Validation.Result.Valid, it)
        }
    }

    @Test
    fun test_lowerCaseLetterCount_invalid() {
        val validation: Validation = Validation.Password(lowerCaseLetterCount = 1)
        val sourceList = listOf(
            "K", "1", " ", "^"
        )
        val actualLiat: List<Validation.Result> = sourceList.map {
            validation.isValid(it)
        }
        actualLiat.forEach {
            assertEquals(
                Validation.Result.LowerCaseLetterCountInsufficient
                    (lowerCaseLetterCount = 1), it
            )
        }
    }


    @Test(expected = IllegalStateException::class)
    fun test_lowerCaseLetterCount_negative() {
        Validation.Password(lowerCaseLetterCount = -1)
    }
    //endregion

    //region number count
    @Test
    fun test_numberCount_valid() {
        val validation: Validation = Validation.Password(numberCount = 1)
        val sourceList = listOf(
            "1", "2", "0", "9", "7",
            "12G", "12K3", "1T234", "12O345",
            "a4sK", "a5sLd", "asU5df", "aOsdfg",
            "A7aS", "AfS4D", "AS6jDF", "ASD5rFG",
            "!E1@", "!U4@#", "!@K2#$", "!@3D#$%",
            "A5s", "As3", "sA@3", "Fs1%l",
            "dj12GkJ#@Kf^"
        )
        val actualLiat: List<Validation.Result> = sourceList.map {
            validation.isValid(it)
        }
        actualLiat.forEach {
            assertEquals(Validation.Result.Valid, it)
        }
    }

    @Test
    fun test_numberCount_invalid() {
        val validation: Validation = Validation.Password(numberCount = 1)
        val sourceList = listOf(
            "K", "g", " ", "^"
        )
        val actualLiat: List<Validation.Result> = sourceList.map {
            validation.isValid(it)
        }
        actualLiat.forEach {
            assertEquals(
                Validation.Result.NumberCountInsufficient
                    (numberCount = 1), it
            )
        }
    }

    @Test(expected = IllegalStateException::class)
    fun test_numberCountCount_negative() {
        Validation.Password(numberCount = -1)
    }
    //endregion

    //region special signs count
    @Test
    fun test_specialSignsCount_valid() {
        val validation: Validation = Validation.Password(specialSignsCount = 1)
        val sourceList = listOf(
            "!", "@", "#", "^",
            "1#", "g&", "H*",
            "!E1", "r4@", "Ky2#",
            "!@3D#$%", "dj12GkJ#@Kf^"
        )
        val actualLiat: List<Validation.Result> = sourceList.map {
            validation.isValid(it)
        }
        actualLiat.forEach {
            assertEquals(Validation.Result.Valid, it)
        }
    }

    @Test
    fun test_specialSignsCount_invalid() {
        val validation: Validation = Validation.Password(specialSignsCount = 1)
        val sourceList = listOf(
            "K", "g", " ", "1"
        )
        val actualLiat: List<Validation.Result> = sourceList.map {
            validation.isValid(it)
        }
        actualLiat.forEach {
            assertEquals(
                Validation.Result.SpecialSignsCountInsufficient
                    (specialSignsCount = 1), it
            )
        }
    }

    @Test(expected = IllegalStateException::class)
    fun test_specialSignsCount_negative() {
        Validation.Password(specialSignsCount = -1)
    }
    //endregion

}


