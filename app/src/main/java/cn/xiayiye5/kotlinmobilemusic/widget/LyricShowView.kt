package cn.xiayiye5.kotlinmobilemusic.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import cn.xiayiye5.kotlinmobilemusic.R
import cn.xiayiye5.kotlinmobilemusic.module.LyricBean
import cn.xiayiye5.kotlinmobilemusic.util.LyricLoaders
import cn.xiayiye5.kotlinmobilemusic.util.LyricUtils

/*
 * Copyright (c) 2020, smuyyh@gmail.com All Rights Reserved.
 * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG            #
 * #                                                   #
 */

/**
 * @author 下一页5（轻飞扬）
 * 创建时间：2020/2/28 16:24
 * 个人小站：http://yhsh.wap.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 项目名称：KotlinMobileMusic
 * 文件包名：cn.xiayiye5.kotlinmobilemusic.widget
 * 文件说明：自定义歌词显示控件
 */
class LyricShowView : View {
    //懒加载抗锯齿的文字
    private val paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    val list by lazy { ArrayList<LyricBean>() }
    private val bounds by lazy { Rect() }
    //默认居中行为第十行
    var centerLine = 1
    //view的高度
    private var viewHeight = 0
    //view的宽度
    private var viewWidth = 0
    //当前播放的大字体显示
    private var bigSize = 0f
    //当前播放的小字体显示
    private var smallSize = 0f
    //白色
    private var white = 0
    //绿色
    private var green = 0
    //每行歌词之间的行高
    private var lineHeight = 0
    //歌曲总时间
    private var duraction = 0;
    //歌曲当前播放进度
    private var progress = 0;
    //是否可以通过进度更新歌词
    private var updateByProgress = true;

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        bigSize = resources.getDimension(R.dimen.bigSize)
        smallSize = resources.getDimension(R.dimen.smallSize)
        lineHeight = resources.getDimensionPixelOffset(R.dimen.lineHeight)
        white = resources.getColor(R.color.white)
        green = resources.getColor(R.color.green)
        //添加此标识后绘制文本X轴坐标是以文本中间为准
        paint.textAlign = Paint.Align.CENTER
        //增加歌词数据
        /*for (i in 0 until 120) {
            list.add(LyricBean(i * 2000, "第 $i 行歌词"))
        }*/
        paint.textSize = bigSize
        paint.color = Color.RED
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (list.size == 0) {
            drawSingleLine(canvas)
        } else {
            drawMultiLine(canvas)
        }
    }

    /**
     * 绘制多行文本
     */
    private fun drawMultiLine(canvas: Canvas?) {
        if (updateByProgress) {
            //求居中行的偏移量
            //行可用时间
            var lineTime = 0
            if (centerLine == list.size - 1) {
                //最后一行,当前行时间减去最后一行时间
                lineTime = duraction - list.get(centerLine).startTime
            } else {
                //行可用时间= 下一行减去居中行的时间
                lineTime = list.get(centerLine + 1).startTime - list.get(centerLine).startTime
            }
            //偏移时间 = progress - 居中行开始时间
            val offsetTime = progress - list.get(centerLine).startTime
            //偏移百分比 = 偏移时间/行可用时间
            val offsetPercent = offsetTime / (lineTime.toFloat())
            //偏移量 = 行高 * 偏移百分比
            offsetY = lineHeight * offsetPercent
        }

        val contentText = list.get(centerLine).content
        paint.getTextBounds(contentText, 0, contentText.length, bounds)
        val textH = bounds.height()
        //居中行Y,减去偏移量
        val centerY = viewHeight / 2 + textH / 2 - offsetY
        for ((index, value) in list.withIndex()) {
            if (index == centerLine) {
                //居中行
                paint.color = green
                paint.textSize = bigSize
            } else {
                //其它行
                paint.color = white
                paint.textSize = smallSize
            }
            val curX = viewWidth / 2
            val curY = centerY + (index - centerLine) * lineHeight
            //判断超出view上面和最下面部分歌词不绘制
            if (curY < 0) continue
            if (curY > viewHeight + lineHeight) break
            //开始绘制
            canvas?.drawText(value.content, curX.toFloat(), curY.toFloat(), paint)
        }
    }

    /***
     * 绘制单行文本
     */
    private fun drawSingleLine(canvas: Canvas?) {
        val str = "正在加载更新……"
        //此方法只能获取文字的宽
        val measureTextWidth = paint.measureText(str, 0, str.length)
        paint.getTextBounds(str, 0, str.length, bounds)
        //bounds可以获取文字的宽和高
        val y = viewHeight / 2 + bounds.height() / 2
        //      val  x = viewWidth / 2 - bounds.width() / 2
        //        val x = (viewWidth - measureTextWidth).toInt() / 2
        val x = viewWidth / 2
        canvas?.drawText(str, x.toFloat(), y.toFloat(), paint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //获取此view的宽高，下面两种方法都可以
        viewHeight = h
        viewWidth = w
//        viewHeight = measuredHeight
//        viewWidth = measuredWidth
    }

    /**
     * 传递当前播放进度实现歌词播放
     */
    fun updateProgress(progress: Int) {
        if (!updateByProgress) return
        this.progress = progress
        Log.e("打印歌曲进度", progress.toString())
        //判断是否为最后一行
        if (progress >= list.get(list.size - 1).startTime) {
            //当前行就为最后一行
            centerLine = list.size - 1
        } else {
            for (index in 0 until list.size - 1) {
                if (progress >= list.get(index).startTime && progress < list.get(index + 1).startTime) {
                    centerLine = index
                    //跳出循环
                    break
                }
            }
        }
        //刷新UI
        //刷新onDraw方法里面的
        invalidate()
        // 刷新onDraw方法里面的，并且可以在子线程刷新
//        postInvalidate()
        //刷新view的布局参数
//        requestLayout()
    }

    //拿到歌曲的总时长
    fun setSongDuration(songDuration: Int) {
        duraction = songDuration
    }

    /**
     * 设置歌曲播放名称
     * 解析歌词，并显示歌词
     */
    fun setSongName(songName: String) {
        this.list.clear()
        list.addAll(LyricUtils.parseLyric(LyricLoaders.loadLyricFile(songName)))
    }

    var offsetY = 0f
    var downY = 0f
    var markY = 0f
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    updateByProgress = false
                    downY = event.y
                    //记录按下之前的偏移高度
                    markY = this.offsetY
                }
                MotionEvent.ACTION_MOVE -> {
                    val endY = event.y
                    val offsetY = downY - endY
                    this.offsetY = offsetY + markY
                    //判断偏移高度是否大于行高
                    if (Math.abs(this.offsetY) >= lineHeight) {
                        //当前播放歌词进行偏移偏移出的行高
                        val offsetLine = (this.offsetY / lineHeight).toInt()
                        centerLine += offsetLine
                        //对拖动歌词超出边界处理
                        if (centerLine < 0) centerLine =
                            0 else if (centerLine > list.size - 1) centerLine = list.size - 1
                        this.downY = endY
                        //重新确定偏移量Y
                        this.offsetY = offsetY % lineHeight
                        //重新记录Y的偏移量
                        markY = this.offsetY
                        //更新歌曲进度.下面两种方法都可以
                        listener?.let {
                            it(list.get(centerLine).startTime)
                        }
//                    listener?.invoke(list.get(centerLine).startTime)
                    }
                    //重新绘制
                    invalidate()
                }
                MotionEvent.ACTION_UP -> updateByProgress = true
            }
        }
        return true
    }

    //拖动歌词更新歌曲的播放进度的回调方法
    private var listener: ((progress: Int) -> Unit)? = null

    fun setProgressListener(listener: (progress: Int) -> Unit) {
        this.listener = listener
    }
}