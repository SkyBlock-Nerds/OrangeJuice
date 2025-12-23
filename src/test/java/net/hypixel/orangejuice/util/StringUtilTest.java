package net.hypixel.orangejuice.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringUtilTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("isNullOrEmpty should return true for null or empty strings")
    void isNullOrEmpty_ShouldReturnTrue_ForNullOrEmpty(String input) {
        assertTrue(StringUtil.isNullOrEmpty(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "abc"})
    @DisplayName("isNullOrEmpty should return false for whitespace or text")
    void isNullOrEmpty_ShouldReturnFalse_ForWhitespaceOrText(String input) {
        assertFalse(StringUtil.isNullOrEmpty(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    @DisplayName("isNullOrBlank should return true for null, empty, or whitespace")
    void isNullOrBlank_ShouldReturnTrue_ForNullEmptyOrWhitespace(String input) {
        assertTrue(StringUtil.isNullOrBlank(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {" abc ", "a"})
    @DisplayName("isNullOrBlank should return false for actual text")
    void isNullOrBlank_ShouldReturnFalse_ForActualText(String input) {
        assertFalse(StringUtil.isNullOrBlank(input));
    }
}