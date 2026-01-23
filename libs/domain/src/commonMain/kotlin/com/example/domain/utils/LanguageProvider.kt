package com.example.domain.utils

/**
 * Platform-specific language provider.
 * Each platform should provide their own implementation.
 */
interface LanguageProvider {
    fun getLanguage(): String
}

/**
 * Default implementation that returns "en" as fallback.
 */
class DefaultLanguageProvider : LanguageProvider {
    override fun getLanguage(): String = "en"
}
