package com.example.cdc4512.vertifydemo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Toast;

import com.luozm.captcha.Captcha;


/**
 * @author : xu
 * date : 2019/2/22 11:26
 * description : 滑块验证dialog
 */
public class CaptchaDialog extends Dialog {
    private Context mContext;


    private Captcha captcha;
    private String bitmapUrl;

    private CaptchaCheckListener captchaCheckListener;

    public CaptchaDialog(@NonNull Context context, CaptchaCheckListener captchaCheckListener) {
        super(context, R.style.LemonDialog);
        this.captchaCheckListener = captchaCheckListener;
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layout = R.layout.captcha_dialog_layout;
        setContentView(layout);

        // 设置遮罩透明度
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.dimAmount = 0.7f;
        getWindow().setAttributes(params);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        captcha = (Captcha) findViewById(R.id.captCha);
        captcha.hideText();

        captcha.setCaptchaListener(new Captcha.CaptchaListener() {
            @Override
            public String onAccess(long time) {
//                Toast.makeText(mContext, "验证失败,请重试", Toast.LENGTH_SHORT).show();
                dismiss();
                if (null != captchaCheckListener) {
                    captchaCheckListener.checkSuccess();
                }
                return "验证通过";
            }

            @Override
            public String onFailed(int count) {
//                LemonToast.showToast("验证失败,失败次数" + count);
                Toast.makeText(mContext, "验证失败,请重试", Toast.LENGTH_SHORT).show();
                return "验证失败";
            }

            @Override
            public String onMaxFailed() {
//                LemonToast.showToast("验证超过次数，你的帐号被封锁");
                Toast.makeText(mContext, "验证失败,请重试", Toast.LENGTH_SHORT).show();
                return "可以走了";
            }
        });

        if (!TextUtils.isEmpty(bitmapUrl)) {
            captcha.setBitmap(bitmapUrl);
        }
    }

    /**
     * 设置验证背景图
     *
     * @param url
     */
    public void setBitmapUrl(String url) {
        bitmapUrl = url;
    }

    public interface CaptchaCheckListener {
        void checkSuccess();

//        void checkFail();
    }
}
