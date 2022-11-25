# ijkplayer-unity

#### 介绍
fork from https://github.com/Yinmany/ijkplayer-unity
bilibli ijkplayer播放器集成到Unity中用作RTMP的直播
仅支持Android和iOS

#### Android
已适配Unity 2021.3.12f1c2
classes.jar路径是C:\Program Files\Unity\Hub\Editor\2021.3.12f1c2\Editor\Data\PlaybackEngines\AndroidPlayer\Variations\mono\Release\Classes

UnityPlayerActivity.java路径是C:\Program Files\Unity\Hub\Editor\2021.3.12f1c2\Editor\Data\PlaybackEngines\AndroidPlayer\Source\com\unity3d\player

AndroidStudio Build-Make Module-MyApplication.ijkplayer

输出的ijkplayer-release.aar路径是ijkplayer-unity\NativeProject\Android\ijkplayer\build\outputs\aar

拷贝到Unity工程路径下 ijkplayer-unity\Assets\IJKPlayer\Plugins\Android
![输入图片说明](https://images.gitee.com/uploads/images/2021/0528/204952_f57f8616_80624.jpeg "Screenshot_20210528-204804.jpg")

rtmp://58.200.131.2:1935/livetv/hunantv 这个地址好像不行了
换成 http://hw-m-l.cztv.com/channels/lantian/channel05/720p.m3u8

之前网上搜了别人编译的so文件上传
现在换成自己编译的，编译步骤在这里https://blog.csdn.net/AWNUXCVBN/article/details/128017823