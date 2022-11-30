using System;
using UnityEngine;

namespace IJKPlayer
{
    /// <summary>
    /// Android实现
    /// </summary>
    public class IijkPlayerNativeAndroid : AndroidJavaProxy, IIJKPlayerNative
    {
        private const string ClassName = "cn.net.xuefei.ijkplayer.MyPlugin";

        private AndroidJavaObject _self;

        private Texture2D _texture;

        static IijkPlayerNativeAndroid()
        {
            new AndroidJavaClass(ClassName).CallStatic("__loadLibraries");
        }

        public float Speed
        {
            get => _self.Call<float>("getSpeed");

            set => _self.Call("setSpeed", value);
        }

        public long VideoCachedDuration => _self.Call<long>("getVideoCachedDuration");
        public Action OnPreparedAction { get; set; }
        public Action OnCompletionAction { get; set; }
        public Action<int, int, int, int> OnVideoSizeChangedAction { get; set; }
        #region Java代理方法

        public void OnPrepared() => OnPreparedAction();
        public void OnCompletion() => OnCompletionAction();
        public void OnVideoSizeChanged(int i, int i1, int i2, int i3) => OnVideoSizeChangedAction(i, i1, i2, i3);
        #endregion

        public Texture2D UnityExternalTexture
        {
            get
            {
                if (_texture) return _texture;

                var id = (IntPtr)_self.Call<int>("getUnityExternalTexture");
                if (id != IntPtr.Zero)
                {
                    _texture = Texture2D.CreateExternalTexture(1920, 1080, TextureFormat.RGB565, false, true, id);
                }

                return _texture;
            }
        }

        public IijkPlayerNativeAndroid(string url) : base("cn.net.xuefei.ijkplayer.MyPluginCallbackListener")
        {
            _self = new AndroidJavaObject(ClassName);
            _self.Call("initSDK", this);
            _self.Call("initWithString", url);
        }

        public void UpdateExternalTexture()
        {
            int id = _self.Call<int>("updateTexture");
            _texture.UpdateExternalTexture((IntPtr)id);
        }

        public void SetOptionValue(int category, string key, string value)
        {
            _self.Call("setOptionValue", category, key, value);
        }

        public void SetOptionIntValue(int category, string key, int value)
        {
            _self.Call("setOptionIntValue", category, key, value);
        }

        public void PrepareToPlay()
        {
            _self.Call("prepareToPlay");
        }

        public void Play()
        {
            _self.Call("play");
        }

        public void Dispose()
        {
            _self.Call("release");
            _self.Dispose();
        }
    }
}