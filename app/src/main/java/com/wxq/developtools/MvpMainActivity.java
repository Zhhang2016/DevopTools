package com.wxq.developtools;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.juziwl.uilibrary.niceplayer.NiceVideoPlayer;
import com.juziwl.uilibrary.tools.SharedPreferencesUtils;
import com.wxq.commonlibrary.constant.GlobalContent;
import com.wxq.commonlibrary.retrofit.download.DownloadHelper;
import com.wxq.commonlibrary.retrofit.download.DownloadListener;
import com.wxq.commonlibrary.util.BarUtils;
import com.wxq.commonlibrary.util.FileUtils;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.mvplibrary.base.BaseActivity;
import com.wxq.mvplibrary.baserx.Event;
import com.wxq.mvplibrary.baserx.RxBus;
import com.wxq.mvplibrary.baserx.RxBusManager;
import com.wxq.mvplibrary.dbmanager.DbManager;
import com.wxq.mvplibrary.model.User;
import com.wxq.mvplibrary.router.RouterContent;

import java.io.File;
import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * Created by wxq on 2018/6/28.
 */

public class MvpMainActivity extends BaseActivity<MvpMainContract.Presenter> implements MvpMainContract.View {


    @BindView(R.id.iv_test_pic)
    ImageView ivTestPic;
    @BindView(R.id.player)
    NiceVideoPlayer player;
    @BindView(R.id.tv_hello)
    TextView tvHello;
    @BindView(R.id.tv_hello2)
    TextView tv_hello2;


    @Override
    protected void initViews() {
        tvHello.setText("1111111111111111111");


        for (User user : DbManager.getInstance().getDaoSession().getUserDao().queryBuilder().list()) {

            com.orhanobut.logger.Logger.e("token",user.getAccessToken());
        }

        tvHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getData(1);
                User user = new User();
                user.setUserName("wxq");
                user.setFlag(0);
                user.setAccessToken("token");
                DbManager.getInstance().getDaoSession().getUserDao().save(user);

                int size = DbManager.getInstance().getDaoSession().getUserDao().queryBuilder().list().size();

                showToast(size + "数据裤中数据");

                Event event = new Event(2, "wxq");

                RxBusManager.getInstance().post(event);


                SharedPreferencesUtils.setStringContent(MvpMainActivity.this,"wxqshare","name","wxq");



                try {
                    ARouter.getInstance()
                            .build(RouterContent.UI_MAIN)
                            .withString("name","name")
                            .withString("wxq","wxq")
                            .navigation();
//                    startActivity(new Intent(MvpMainActivity.this,MainActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                FileUtils.createOrExistsDir(GlobalContent.logPath);

                FileUtils.appendToFile("wxqdsfasfasdfasd", GlobalContent.logPath+System.currentTimeMillis()+".text");

            }
        });



        tv_hello2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
//                    ARouter.getInstance().build(RouterContent.NETTEST_MAIN).navigation();
//                    startActivity(new Intent(MvpMainActivity.this,MainActivity.class));

//                    、、下载

                    DownloadHelper helper=new DownloadHelper("http://www.baidu.com", new DownloadListener() {
                        @Override
                        public void onStartDownload() {

                        }

                        @Override
                        public void onProgress(int progress) {
                            tvHello.setText(progress+"");
                        }

                        @Override
                        public void onFinishDownload(File file) {

                            tv_hello2.setText(file.getAbsolutePath());
                        }

                        @Override
                        public void onFail(Throwable ex) {

                        }
                    });
                    helper.downloadFile("https://dfsres-1254059237.cos.ap-shanghai.myqcloud.com/apppackage/testpad/ipadteacher.apk", GlobalContent.SAVEFILEPATH,"test.apk");



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //请求权限全部结果
        rxPermissions.request(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (!granted) {
                          showToast("App未能获取全部需要的相关权限，部分功能可能不能正常使用.");
                        }
                        //不管是否获取全部权限，进入主页面
//                        initCountDown();
                    }
                });


    }

    @Override
    public void dealWithRxEvent(int action, Event event) {
        super.dealWithRxEvent(action, event);
        if (action == 2) {
//            showToast(event.getObject() + "rxgetsuccess");

        }

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected MvpMainContract.Presenter initPresent() {
        return new MvpMainPresent(this);
    }


    @Override
    public void showRx() {

    }
}
