package cn.net.xuefei.ijkplayer;

/***
 * 回调
 */
public interface MyPluginCallbackListener {

    // 加载完成可以播放了
    void OnPrepared();

    // 网络断开会调用此方法
    void OnCompletion();

    void OnVideoSizeChanged(int i,int i1,int i2,int i3);
}
