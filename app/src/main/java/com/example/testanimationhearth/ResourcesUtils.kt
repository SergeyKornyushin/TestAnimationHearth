package com.example.testanimationhearth

import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.Window
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import java.util.*

/**
 * Вспомогательный класс для работы с ресурсами
 */
object ResourcesUtils {

    private val DEFAULT_APP_LOCALE: Locale = Locale("ru")

    /**
     * Возвращает строковый ресурс по его идентификатору
     */
    fun getString(@StringRes stringResId: Int): String {
        return getResources().getString(stringResId)
    }

    /**
     * Возвращает строковый ресурс с параметрами по его идентификатору
     */
    fun getString(@StringRes stringResId: Int, vararg parameters: Any): String {
        return getResources().getString(stringResId, *parameters)
    }

    /**
     * Возвращает цвет по его идентификатору в ресурсах
     */
    fun getColor(@ColorRes colorResId: Int): Int {
        return ContextCompat.getColor(App.context, colorResId)
    }

    /**
     * Возвращает отформатированную строку грамматически правильную для множественного числа
     *
     * @param pluralResId идентификатор текста множества
     * @param quantity количество для которого необходимо отформатировать строку
     * @return отформатированную строку
     */
    fun getQuantityString(@PluralsRes pluralResId: Int, quantity: Number): String {
        return getQuantityString(pluralResId, quantity, quantity)
    }

    /**
     * Возвращает отформатированную строку грамматически правильную для множественного числа
     *
     * @param pluralResId идентификатор текста множества
     * @param quantity количество для которого необходимо отформатировать строку
     * @param value подставляемое значение в отформатированную строку
     * @return отформатированную строку
     */
    fun getQuantityString(@PluralsRes pluralResId: Int, quantity: Number, value: Any): String {
        val localizedResources = getLocalizedResources(DEFAULT_APP_LOCALE)
        val integerQuantity = quantity.toLong().let {
            if (it > Int.MAX_VALUE) it % 100 else it
        }.toInt()
        return localizedResources.getQuantityString(pluralResId, integerQuantity, value)
    }

    /**
     * Возвращает количество pixels из ресурсов через ссылку на dimension resource
     *
     * @param dimenResId идентификатор dimension resource
     * @return количество pixels
     * @see getPxByDp
     */
    fun getDimensionPixels(@DimenRes dimenResId: Int): Int {
        return getResources().getDimensionPixelSize(dimenResId)
    }

    /**
     * Возвращает ширину экрана в pixels
     */
    fun getScreenWidthInPixels(): Int {
        return getDisplayMetrics().widthPixels
    }

    /**
     * Возвращает высоту экрана в pixels
     */
    fun getScreenHeightInPixels(): Int {
        val displayHeight = getDisplayMetrics().heightPixels
        val statusBarHeight = getStatusBarHeight()
        return displayHeight - statusBarHeight
    }

    /**
     * Возвращает высоту экрана в pixels
     */
    fun getScreenHeightInPixels(window: Window): Int {
        val decorView = window.decorView
        val rect = Rect()
        decorView.getWindowVisibleDisplayFrame(rect)
        val statusBarHeight = getStatusBarHeight()
        return if (rect.top == 0) rect.bottom else rect.bottom - statusBarHeight
    }

    /**
     * Конвертирует значение dp (density-independent pixels) в значение pixels
     * @see getPxBySp
     */
    fun getPxByDp(dp: Number): Int {
        return convertToPx(TypedValue.COMPLEX_UNIT_DIP, dp).toInt()
    }

    /**
     * Конвертирует значение sp (scalable pixels) в значение pixels
     * @see getPxByDp
     */
    fun getPxBySp(sp: Number): Float {
        return convertToPx(TypedValue.COMPLEX_UNIT_SP, sp)
    }

    /**
     * Возвращает целое число по его идентификатору в ресурсах
     */
    fun getInteger(@IntegerRes integerResId: Int): Int {
        return getResources().getInteger(integerResId)
    }

    /**
     * Возвращает массив строк по его идентификатору в ресурсах
     */
    fun getStringArray(@ArrayRes arrayResId: Int): Array<String> {
        return getResources().getStringArray(arrayResId)
    }

    /**
     * Возвращает [Drawable] по его идентификатору
     *
     * @param drawableResId идентификатор [Drawable]
     */
    fun getDrawable(@DrawableRes drawableResId: Int): Drawable? {
        return ContextCompat.getDrawable(App.context, drawableResId)
    }

    /**
     * Возвращается [Typeface] по его идентификатору
     *
     * @param fontRes идентификатор [Typeface]
     */
    fun getFont(@FontRes fontRes: Int): Typeface? {
        return ResourcesCompat.getFont(App.context, fontRes)
    }

    /**
     * Возвращает высоту StatusBar'а в pixels
     */
    fun getStatusBarHeight(): Int {
        val resourceName = "status_bar_height"
        val resourceType = "dimen"
        val resourcePackage = "android"

        val resourceId = getResources().getIdentifier(resourceName, resourceType, resourcePackage)

        if (resourceId == 0) {
            /* Если идентификатор ресурса равен 0, то значит, что ресурс не был найден */
            return 0
        }
        return getResources().getDimensionPixelSize(resourceId)
    }

    /**
     * Возвращает локализированный экземпляр класса [Resources] на основе [языковой локали][Locale]
     */
    private fun getLocalizedResources(locale: Locale): Resources {
        var conf = getResources().configuration
        conf = Configuration(conf)
        conf.setLocale(locale)
        return App.context.createConfigurationContext(conf).resources
    }

    /**
     * Конвертирует переданное значение в pixels
     *
     * @param unit [тип передаваемого значения][TypedValue]
     * @param value передаваемое значение
     * @return количество pixels
     * @see getPxByDp
     * @see getPxBySp
     */
    private fun convertToPx(unit: Int, value: Number): Float {
        val metrics = getResources().displayMetrics
        return TypedValue.applyDimension(unit, value.toFloat(), metrics)
    }

    private fun getDisplayMetrics() = getResources().displayMetrics

    private fun getResources() = App.context.resources
}