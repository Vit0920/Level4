package com.vkunitsyn.level4.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.vkunitsyn.level4.R
import com.vkunitsyn.level4.utils.Constants.BUTTON_CORNER_RADIUS
import com.vkunitsyn.level4.utils.Constants.DEFAULT_GOOGLE_LOGO_SIZE
import com.vkunitsyn.level4.utils.Constants.DEFAULT_TEXT
import com.vkunitsyn.level4.utils.Constants.DEFAULT_TEXT_SIZE
import com.vkunitsyn.level4.utils.Constants.LOGO_TEXT_MARGIN
import com.vkunitsyn.level4.utils.Constants.PADDING_X
import com.vkunitsyn.level4.utils.Constants.PADDING_Y
import com.vkunitsyn.level4.utils.dpToPx


class GoogleCustomButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintText: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textBoundRect = Rect()
    private var logo = ResourcesCompat.getDrawable(resources, R.drawable.google_logo, null)
    private var googleLogoSize = 0F
    private var logoTextMargin = 0F
    private var text: String = DEFAULT_TEXT
    private var textSizeAttrs = DEFAULT_TEXT_SIZE
    private var textGoogleColor = Color.BLACK
    private var textWidth = 0F
    private var textHeight = 0F
    private var textAllCaps = true
    private var paddingHorizontal = 0F
    private var paddingVertical = 0F
    private var buttonColor = Color.WHITE


    init {
        paint.isAntiAlias = true
        if (attrs != null) initAttributes(attrs)
        initPaintText()

    }

    private fun initPaintText() {
        paintText.isAntiAlias = true
        text = capitalizeText(text)
        paintText.apply {
            textSize = textSizeAttrs.toFloat()
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            color = textGoogleColor
            //Gets text size
            getTextBounds(text, 0, text.length, textBoundRect)
            textHeight = textBoundRect.height().toFloat()
            textWidth = textBoundRect.width().toFloat()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val contentWidth =
            (googleLogoSize + logoTextMargin + textWidth + 2 * paddingHorizontal).toInt()
        val contentHeight = (googleLogoSize + 2 * paddingVertical).toInt()
        val measuredWidthSize = resolveSize(contentWidth, widthMeasureSpec)
        val measuredHeightSize = resolveSize(contentHeight, heightMeasureSpec)
        setMeasuredDimension(measuredWidthSize, measuredHeightSize)
        context
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas != null) {
            drawBackground(canvas)
            drawLogo(canvas)
            drawText(canvas)
        }
    }

    private fun initAttributes(attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs, R.styleable.GoogleCustomButton, 0, 0
        )
        text = typedArray.getString(R.styleable.GoogleCustomButton_text) ?: DEFAULT_TEXT
        textSizeAttrs = typedArray.getDimensionPixelSize(
            R.styleable.GoogleCustomButton_textSize, DEFAULT_TEXT_SIZE
        )
        textGoogleColor = typedArray.getColor(R.styleable.GoogleCustomButton_textColor, Color.BLACK)
        buttonColor =
            typedArray.getColor(R.styleable.GoogleCustomButton_backgroundTint, Color.WHITE)
        paddingHorizontal = typedArray.getDimension(
            R.styleable.GoogleCustomButton_paddingHorizontal, context.dpToPx(PADDING_X)
        )
        paddingVertical = typedArray.getDimension(
            R.styleable.GoogleCustomButton_paddingVertical, context.dpToPx(PADDING_Y)
        )
        googleLogoSize = typedArray.getDimension(
            R.styleable.GoogleCustomButton_logoSize, context.dpToPx(DEFAULT_GOOGLE_LOGO_SIZE)
        )
        logoTextMargin = typedArray.getDimension(
            R.styleable.GoogleCustomButton_logoTextMargin, context.dpToPx(LOGO_TEXT_MARGIN)
        )

        textAllCaps = typedArray.getBoolean(R.styleable.GoogleCustomButton_textAllCaps, true)

        logo = typedArray.getDrawable(R.styleable.GoogleCustomButton_logoDrawable)
            ?: ResourcesCompat.getDrawable(resources, R.drawable.google_logo, null)

        typedArray.recycle()
    }


    private fun drawBackground(canvas: Canvas) {
        paint.apply {
            style = Paint.Style.FILL
            isAntiAlias = true
            paint.color = buttonColor
        }
        canvas.drawRoundRect(
            0F,
            0F,
            width.toFloat(),
            height.toFloat(),
            BUTTON_CORNER_RADIUS,
            BUTTON_CORNER_RADIUS,
            paint
        )
    }

    private fun drawLogo(canvas: Canvas) {
        val logoLeft = ((width - textWidth - googleLogoSize - logoTextMargin) / 2F).toInt()
        val logoTop = ((height - googleLogoSize) / 2F).toInt()
        val logoRight = (logoLeft + googleLogoSize).toInt()
        val logoBottom = (logoTop + googleLogoSize).toInt()
        logo?.setBounds(logoLeft, logoTop, logoRight, logoBottom)


        logo?.draw(canvas)
    }

    private fun drawText(canvas: Canvas) {
        text.uppercase()
        canvas.drawText(
            text,
            (width - textWidth + googleLogoSize + logoTextMargin) / 2F,
            (height + textHeight) / 2F,
            paintText
        )
    }

    private fun capitalizeText(text: String): String {
        if (textAllCaps) {
            return text.uppercase()
        }
        return text
    }
}