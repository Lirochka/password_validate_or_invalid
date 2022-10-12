package com.example.passwordvalidation

interface Validation {

    fun isValid(text: String): Result

    sealed class Result {

        object Valid : Result()

        data class MinLengthInsufficient(
            private val minLength: Int
        ) : Result()

        data class UpperCaseLetterCountInsufficient(
            private val upperCaseLetterCount: Int
        ) : Result()

        data class LowerCaseLetterCountInsufficient(
            private val lowerCaseLetterCount: Int
        ) : Result()

        data class NumberCountInsufficient(
            private val numberCount: Int
        ) : Result()

        data class SpecialSignsCountInsufficient(
            private val specialSignsCount: Int
        ) : Result()
    }

    class Password(
        private val minLength: Int = 1,
        private val upperCaseLetterCount: Int = 0,
        private val lowerCaseLetterCount: Int = 0,
        private val numberCount: Int = 0,
        private val specialSignsCount: Int = 0,
    ) : Validation {

        init {
            if (minLength < 1) throw IllegalStateException(
                "minLength should be positive!"
            )
            if (upperCaseLetterCount < 0)
                throw IllegalStateException(
                    "upperCase should be non-negative!"
                )
            if (lowerCaseLetterCount < 0)
                throw IllegalStateException(
                    "lowerCase should be non-negative!"
                )
            if (numberCount < 0)
                throw IllegalStateException(
                    "numberCount should be non-negative!"
                )
            if (specialSignsCount < 0)
                throw IllegalStateException(
                    "specialSignsCount should be non-negative!"
                )
        }

        override fun isValid(text: String): Result {
            if (text.length < minLength)
                return Result.MinLengthInsufficient(minLength)

            if (upperCaseLetterCount > 0) {
                var count = 0
                for (ch in text) {
                    if (ch.isUpperCase())
                        count++
                }
                if (count < upperCaseLetterCount)
                    return Result.UpperCaseLetterCountInsufficient(
                        upperCaseLetterCount
                    )
            }
            if (lowerCaseLetterCount > 0) {
                var count = 0
                for (ch in text) {
                    if (ch.isLowerCase())
                        count++
                }
                if (count < lowerCaseLetterCount)
                    return Result.LowerCaseLetterCountInsufficient(
                        lowerCaseLetterCount
                    )
            }
            if (numberCount > 0) {
                var count = 0
                for (ch in text) {
                    if (ch.isDigit())
                        count++
                }
                if (count < numberCount)
                    return Result.NumberCountInsufficient(
                        numberCount
                    )
            }
            if (specialSignsCount > 0) {
                var count = 0
                for (ch in text) {
                    if (ch.isLetterOrDigit())
                        count++
                }
                if (count == text.length)
                    return Result.SpecialSignsCountInsufficient(
                        specialSignsCount
                    )
            }
            return Result.Valid
        }
    }
}

