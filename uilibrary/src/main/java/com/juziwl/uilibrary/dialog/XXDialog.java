package com.juziwl.uilibrary.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.juziwl.uilibrary.R;

/**
 * @author null
 * @modify Neil
 */
public abstract class XXDialog {
    private Dialog mDialog;
    private Window mDialogWindow;
    private DialogViewHolder dilaogVh;
    private View mRootView;

    public XXDialog(Context context, int layoutId) {
        dilaogVh = DialogViewHolder.get(context, layoutId);
        mRootView = dilaogVh.getConvertView();
        mDialog = new Dialog(context, R.style.common_dialog);
        mDialog.setContentView(mRootView);
        mDialogWindow = mDialog.getWindow();
        convert(dilaogVh);
    }

    /**
     * 把弹出框view holder传出去
     *
     * @param holder
     */
    public abstract void convert(DialogViewHolder holder);

    public static AlertDialog.Builder creatNormalDialogBuilder(Context context, String title, String message) {
        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message);
    }

    /**
     * 显示dialog
     */
    public XXDialog showDialog() {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
        return this;
    }

    /**
     * @param light 弹出时背景亮度 值为0.0~1.0    1.0表示全黑  0.0表示全白
     * @return
     */
    public XXDialog backgroundLight(double light) {
        if (light < 0.0 || light > 1.0) {
            return this;
        }
        WindowManager.LayoutParams lp = mDialogWindow.getAttributes();
        lp.dimAmount = (float) light;
        mDialogWindow.setAttributes(lp);
        return this;
    }

    /**
     * 从底部一直弹到中间
     */
    @SuppressLint("NewApi")
    public XXDialog fromBottomToMiddle() {
        mDialogWindow.setWindowAnimations(R.style.common_window_bottom_in_bottom_out);
        return this;
    }

    /**
     * 从底部弹出
     */
    public XXDialog fromBottom() {
        fromBottomToMiddle();
        mDialogWindow.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        return this;
    }

    /**
     * 从左边一直弹到中间退出也是到左边
     */
    public XXDialog fromLeftToMiddle() {
        mDialogWindow.setWindowAnimations(R.style.common_window_left_in_left_out);
        mDialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mDialogWindow.setGravity(Gravity.CENTER | Gravity.LEFT);
        return this;
    }

    /**
     * 从右边一直弹到中间退出也是到右边
     *
     * @return
     */
    public XXDialog fromRightToMiddle() {
        mDialogWindow.setWindowAnimations(R.style.common_window_right_in_right_out);
        mDialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mDialogWindow.setGravity(Gravity.RIGHT);
//
        return this;
    }

    /**
     * 从顶部弹出 从顶部弹出  保持在顶部
     *
     * @return
     */
    public XXDialog fromTop() {
        fromTopToMiddle();
        mDialogWindow.setGravity(Gravity.CENTER | Gravity.TOP);
        return this;
    }

    /**
     * 从顶部谈到中间  从顶部弹出  保持在中间
     *
     * @return
     */
    public XXDialog fromTopToMiddle() {
        mDialogWindow.setWindowAnimations(R.style.common_window_top_in_top_out);
        mDialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return this;
    }

    /**
     * @param style 显示一个Dialog自定义一个弹出方式  具体怎么写 可以模仿上面的
     * @return
     */
    public XXDialog showDialog(@StyleRes int style) {
        mDialogWindow.setWindowAnimations(style);
        mDialog.show();
        return this;
    }

    /**
     * @param isAnimation 如果为true 就显示默认的一个缩放动画
     * @return
     */
    public XXDialog showDialog(boolean isAnimation) {
        mDialogWindow.setWindowAnimations(R.style.common_dialog_scale_animstyle);
        mDialog.show();
        return this;
    }

    /**
     * 全屏显示
     */
    public XXDialog fullScreen() {
        WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
        wl.height = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mDialog.onWindowAttributesChanged(wl);
        return this;
    }


    public XXDialog setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        mDialog.setOnKeyListener(onKeyListener);
        return this;
    }

    /**
     * 全屏宽度
     */
    public XXDialog fullWidth() {
        WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mDialog.onWindowAttributesChanged(wl);
        return this;
    }

    /**
     * 全屏高度
     */
    public XXDialog fullHeight() {
        WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
        wl.height = ViewGroup.LayoutParams.MATCH_PARENT;
        mDialog.onWindowAttributesChanged(wl);
        return this;
    }

    /**
     * @param width  自定义的宽度
     * @param height 自定义的高度
     * @return
     */
    public XXDialog setWidthAndHeight(int width, int height) {
        WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
        wl.width = width;
        wl.height = height;
        mDialog.onWindowAttributesChanged(wl);
        return this;
    }

    /**
     * cancel dialog
     */
    public void cancelDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            dismiss();
        }
    }

    /**
     * cancel dialog
     */
    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    /**
     * 设置监听
     */
    public XXDialog setDialogDismissListener(OnDismissListener listener) {
        mDialog.setOnDismissListener(listener);
        return this;
    }

    /**
     * 设置监听
     */
    public XXDialog setOnCancelListener(OnCancelListener listener) {
        mDialog.setOnCancelListener(listener);
        return this;
    }

    /**
     * 设置是否能取消
     */
    public XXDialog setCancelAble(boolean cancel) {
        mDialog.setCancelable(cancel);
        return this;
    }


    /**
     * 设置触摸其他地方是否能取消
     */
    public XXDialog setCanceledOnTouchOutside(boolean cancel) {
        mDialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

}

//使用方法
//
//
//public void click(View view) {
//        XXDialog xxDialog = new XXDialog(this, R.layout.dialog) {
//@Override
//public void convert(DialogViewHolder holder) {  //holder 很重要避免多次创建
//        holder.setOnClick(R.id.tv, new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//        Toast.makeText(MainActivity.this, "点了", Toast.LENGTH_SHORT).show();
//        }
//        });
//        }
//        }.fromBottom().fullWidth().showDialog().setCanceledOnTouchOutside(true);
//
//        }