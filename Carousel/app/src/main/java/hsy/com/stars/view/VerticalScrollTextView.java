package hsy.com.stars.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import hsy.com.carousel.R;


/**
 * 垂直滚动TextView
 */
public class VerticalScrollTextView extends TextView implements View.OnClickListener {

    private static int DEFAULT_SWITCH_DURATION = 500;
    private static int DEFAULT_IDLE_DURATION = 3000;

    private List<String> lists;//会循环显示的文本内容
    private int contentSize;
    private String outStr;//当前滑出的文本内容
    private String inStr;//当前滑入的文本内容
    private float textBaseY;//文本显示的baseline
    private int currentIndex = 0;//当前显示到第几个文本

    private int switchDuaration = DEFAULT_SWITCH_DURATION;//切换时间
    private int idleDuaration = DEFAULT_IDLE_DURATION;//间隔时间
    private int switchOrientation = 0;

    private Paint mPaint;
    private ValueAnimator animator;
    private float currentAnimatedValue = 0.0f;
    private int verticalOffset = 0;
    private int mWidth;
    private int mHeight;
    private int paddingLeft = 0;
    private int paddingBottom = 0;
    private int paddingTop = 0;
    private Context mContext;

    //回调接口，用来通知调用者控件当前的状态
    public VerticalSwitchTextViewCbInterface cbInterface;

    public VerticalScrollTextView(Context context) {
        super(context);
        init();
    }

    public VerticalScrollTextView(Context context, AttributeSet attr) {
        super(context, attr);
        init();
    }

    public VerticalScrollTextView(Context context, AttributeSet attr, int defStyleAttr) {
        super(context, attr, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attr, R.styleable.VerticalSwitchTextView);
        try {
            switchDuaration = array.getInt(R.styleable.VerticalSwitchTextView_switchDuaration, DEFAULT_SWITCH_DURATION);
            idleDuaration = array.getInt(R.styleable.VerticalSwitchTextView_idleDuaration, DEFAULT_IDLE_DURATION);
            switchOrientation = array.getInt(R.styleable.VerticalSwitchTextView_switchOrientation, 0);
        } finally {
            array.recycle();
        }
        init();
    }

    private void init() {
        setOnClickListener(this);

        mPaint = getPaint();
        mPaint.setColor(getCurrentTextColor());

        animator = ValueAnimator.ofFloat(0f, 1f).setDuration(switchDuaration);
        animator.setStartDelay(idleDuaration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentAnimatedValue = (float) animation.getAnimatedValue();
                if (currentAnimatedValue < 1.0f) {
                    invalidate();
                }
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                currentIndex = (++currentIndex) % contentSize;
                if (cbInterface != null) {
                    cbInterface.showNext(currentIndex);
                }
                outStr = lists.get(currentIndex);
                inStr = lists.get((currentIndex + 1) % contentSize);

                animator.setStartDelay(idleDuaration);
                animator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 设置循环显示的文本内容
     *
     * @param content 内容list
     */
    public void setTextContent(List<String> content) {
        lists = content;
        if (lists == null || lists.size() == 0) {
            return;
        }
        contentSize = lists.size();
        Log.e("Tag", "List.size==" + contentSize);

        outStr = lists.get(0);
        if (contentSize > 1) {
            inStr = lists.get(1);
        } else {
            inStr = lists.get(0);
        }

        if (contentSize > 0) {
            animator.start();
        }
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (contentSize <= 0) {
            return;
        }
        //直接使用mHeight控制文本绘制，会因为text的baseline的问题不能居中显示
        verticalOffset = Math.round(2 * textBaseY * (0.5f - currentAnimatedValue));
        if (switchOrientation == 0) {//向上滚动切换
            if (verticalOffset > 0) {
                outStr = TextUtils.ellipsize(outStr, (TextPaint) mPaint, mWidth, TextUtils.TruncateAt.END) + "";
                canvas.drawText(outStr, paddingLeft, verticalOffset, mPaint);
            } else {
                inStr = TextUtils.ellipsize(inStr, (TextPaint) mPaint, mWidth, TextUtils.TruncateAt.END) + "";
                canvas.drawText(inStr, paddingLeft, 2 * textBaseY + verticalOffset, mPaint);
            }
        } else {
            if (verticalOffset > 0) {//向下滚动切换
                outStr = TextUtils.ellipsize(outStr, (TextPaint) mPaint, mWidth, TextUtils.TruncateAt.END) + "";
                canvas.drawText(outStr, paddingLeft, 2 * textBaseY - verticalOffset, mPaint);
            } else {
                inStr = TextUtils.ellipsize(inStr, (TextPaint) mPaint, mWidth, TextUtils.TruncateAt.END) + "";
                canvas.drawText(inStr, paddingLeft, -verticalOffset, mPaint);
            }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        Rect bounds = new Rect();
        if (contentSize <= 0) {
            return;
        }
        String text = lists.get(0);
        mPaint.getTextBounds(text, 0, text.length(), bounds);
        int textHeight = bounds.height();
        //Log.e("Tag", "onMeasure height is " + mHeight);

        paddingLeft = getPaddingLeft();
        paddingBottom = getPaddingBottom();
        paddingTop = getPaddingTop();
        mHeight = textHeight + paddingBottom + paddingTop;

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        //计算文字高度
        float fontHeight = fontMetrics.bottom - fontMetrics.top;
        //计算文字的baseline
        textBaseY = mHeight - (mHeight - fontHeight) / 2 - fontMetrics.bottom;

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    public void onClick(View v) {
        if (contentSize > currentIndex) {
            if (cbInterface != null) {
                cbInterface.onItemClick(currentIndex);
            }
            //Toast.makeText(mContext, lists.get(currentIndex), Toast.LENGTH_SHORT).show();
        }
    }

    //回调接口，用来通知调用者控件当前的状态,index表示开始显示哪一个文本内容
    public interface VerticalSwitchTextViewCbInterface {
        void showNext(int index);

        void onItemClick(int index);
    }

    public void setCbInterface(VerticalSwitchTextViewCbInterface cb) {
        cbInterface = cb;
    }
}