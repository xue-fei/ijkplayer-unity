package cn.net.xuefei.ijkplayer;

import android.opengl.GLES30;

public class FBO {
    private Texture2D mTexture2D;
    private int mFBOID;
    private int mDepthTextureID; // 新增：存储深度纹理ID，便于销毁

    public FBO(Texture2D texture2D) {
        mTexture2D = texture2D;
        int[] temps = new int[1];

        // 1. 创建深度纹理
        GLES30.glGenTextures(1, temps, 0);
        mDepthTextureID = temps[0];
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, mDepthTextureID);

        // 使用24位深度纹理（ES3支持）
        GLES30.glTexImage2D(
                GLES30.GL_TEXTURE_2D,
                0,
                GLES30.GL_DEPTH_COMPONENT24, // 24位深度
                texture2D.getWidth(),
                texture2D.getHeight(),
                0,
                GLES30.GL_DEPTH_COMPONENT,
                GLES30.GL_UNSIGNED_INT, // 与24位深度匹配
                null
        );

        // 纹理参数
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);

        // 2. 创建FBO
        GLES30.glGenFramebuffers(1, temps, 0);
        mFBOID = temps[0];
        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, mFBOID);

        // 3. 附加颜色附件
        GLES30.glFramebufferTexture2D(
                GLES30.GL_FRAMEBUFFER,
                GLES30.GL_COLOR_ATTACHMENT0,
                GLES30.GL_TEXTURE_2D,
                mTexture2D.getTextureID(),
                0
        );

        // 4. 附加深度附件
        GLES30.glFramebufferTexture2D(
                GLES30.GL_FRAMEBUFFER,
                GLES30.GL_DEPTH_ATTACHMENT,
                GLES30.GL_TEXTURE_2D,
                mDepthTextureID,
                0
        );

        // 5. 检查FBO完整性
        int status = GLES30.glCheckFramebufferStatus(GLES30.GL_FRAMEBUFFER);
        if (status != GLES30.GL_FRAMEBUFFER_COMPLETE) {
            throw new RuntimeException("Framebuffer not complete, status: " + status);
        }

        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, 0);
    }

    public void FBOBegin() {
        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, mFBOID);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, 0);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);

        // 设置视口与纹理尺寸一致
        GLES30.glViewport(0, 0, mTexture2D.getWidth(), mTexture2D.getHeight());

        // 可选：清除颜色和深度缓冲区
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);
    }

    public void FBOEnd() {
        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, 0);
    }

    public void destroy() {
        // 删除FBO
        GLES30.glDeleteFramebuffers(1, new int[]{mFBOID}, 0);

        // 删除深度纹理
        GLES30.glDeleteTextures(1, new int[]{mDepthTextureID}, 0);
    }
}