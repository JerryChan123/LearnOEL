package com.lin.proxymedia;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.list);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            list.add(i + "");
        }
        MainAdapter adapter = new MainAdapter(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new StickyItemDecoration<String>(list));
        mRecyclerView.setAdapter(adapter);
        intGL();
    }

    private GLSurfaceView mSurfaceView;

    @Override
    protected void onPause() {
        super.onPause();
        if (mSurfaceView != null) {
            mSurfaceView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSurfaceView != null) {
            mSurfaceView.onResume();
        }
    }


    private void intGL() {
        mSurfaceView = findViewById(R.id.gl);

        mSurfaceView.setEGLContextClientVersion(3);
        mSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        mSurfaceView.setRenderer(new MyRenderer());
        //RenderMode 有两种，RENDERMODE_WHEN_DIRTY 和 RENDERMODE_CONTINUOUSLY，
        // 前者是懒惰渲染，需要手动调用 glSurfaceView.requestRender() 才会进行更新，而后者则是不停渲染。
        mSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }


    private static class MyRenderer implements GLSurfaceView.Renderer {
        private Triangle mSquare;

        @Override
        public void onSurfaceCreated(GL10 unused, EGLConfig config) {
            mSquare = new Triangle();
        }

        @Override
        public void onSurfaceChanged(GL10 unused, int width, int height) {
            //设置 Screen space 的大小
            GLES30.glViewport(0, 0, width, height);
        }

        //绘制的过程其实就是为 shader 代码变量赋值，并调用绘制命令的过程
        @Override
        public void onDrawFrame(GL10 unused) {
            mSquare.onDraw();
        }
    }

}
