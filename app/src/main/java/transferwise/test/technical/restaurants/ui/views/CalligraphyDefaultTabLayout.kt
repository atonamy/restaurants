package transferwise.test.technical.restaurants.ui.views

import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.support.design.widget.TabLayout
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView

import io.github.inflationx.calligraphy3.TypefaceUtils
import transferwise.test.technical.restaurants.R


/**
 * TabLayout for use with the default Calligraphy font
 */
class CalligraphyDefaultTabLayout : TabLayout {

    private var calligraphyTypeface: Typeface? = null

    constructor(context: Context) : super(context) {
        initCalligraphyTypeface("")
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initCalligraphyTypeface(pullFontPathFromView(context, attrs, intArrayOf(R.attr.fontPath)))
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initCalligraphyTypeface(pullFontPathFromView(context, attrs, intArrayOf(R.attr.fontPath)))
    }

    override fun addTab(tab: TabLayout.Tab, position: Int, setSelected: Boolean) {
        super.addTab(tab, position, setSelected)
        if (calligraphyTypeface != null) {
            val mainView = getChildAt(0) as ViewGroup
            val tabView = mainView.getChildAt(tab.position) as ViewGroup
            val tabChildCount = tabView.childCount
            for (i in 0 until tabChildCount) {
                val tabViewChild = tabView.getChildAt(i)
                if (tabViewChild is TextView) {
                    tabViewChild.typeface = calligraphyTypeface
                }
            }
        }
    }

    private fun initCalligraphyTypeface(fontPath: String) {
        calligraphyTypeface = TypefaceUtils.load(resources.assets, fontPath)
    }

    fun pullFontPathFromView(context: Context, attrs: AttributeSet?, attributeId: IntArray): String {
        if (attrs == null) return ""

        val attributeName: String
        try {
            attributeName = context.resources.getResourceEntryName(attributeId[0])
        } catch (e: Resources.NotFoundException) {
            return ""
        }

        val stringResourceId = attrs.getAttributeResourceValue(null, attributeName, -1)
        return if (stringResourceId > 0)
            context.getString(stringResourceId)
        else
            attrs.getAttributeValue(null, attributeName)
    }
}