package com.example.vilketvader

import com.example.domain.utils.LanguageProvider
import java.util.Locale

class AndroidLanguageProvider : LanguageProvider {
    override fun getLanguage(): String = Locale.getDefault().language
}
