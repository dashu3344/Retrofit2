package com.yztc.retrofit2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {


    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://10.0.164.217:8080/MyWeb/")
                .addConverterFactory(ScalarsConverterFactory.create())// 转换成 String
                .addConverterFactory(GsonConverterFactory.create())//JSON 转换器  将请求、返回值近行转换、解析
                .build();


         imageView = (ImageView) findViewById(R.id.image);

        //通过动态代理的方式实现接口中的方法
        final IApi iApi = retrofit.create(IApi.class);

        String url="http://pic.58pic.com/58pic/15/35/05/95258PICQnd_1024.jpg";
        final Call<ResponseBody> call = iApi.down(url);

        new Thread(){
            @Override
            public void run() {
                super.run();

                try {

                Response<ResponseBody> response = call.execute();
                InputStream inputStream = response.body().byteStream();
                File file = getFilesDir();
                File down=new File(file,"aaaa.jpg");

                    FileOutputStream out=new FileOutputStream(down);
                    BufferedInputStream buffIn=new BufferedInputStream(inputStream);
                    BufferedOutputStream buffOut=new BufferedOutputStream(out);

                    byte[] b=new byte[10*1024];
                    int len;
                    while ((len=buffIn.read(b))!=-1){
                        buffOut.write(b,0,len);
                    }
                    buffOut.flush();

                    buffOut.close();
                    buffIn.close();



                  final  Bitmap bitmap = BitmapFactory.decodeFile(down.getPath());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            imageView.setImageBitmap(bitmap);
                        }
                    });



                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();



    }

    private void url(IApi iApi) {
        String url="http://10.0.164.217:8080/MyWeb/RegServlet?username=aaa&password=bbb";

        Call<String> call = iApi.getResult2(url);//不进行 baseurl 拼接
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("=========",response.body());
                Toast.makeText(MainActivity.this, response.body(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void get(IApi iApi) {
        HashMap<String,String> params=new HashMap<>();
        params.put("username","q");
        params.put("password","b");

        Call<String> call = iApi.getResult2(params);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("============",response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void getRequest(IApi iApi) {
        Call<String> call = iApi.getResult("A", "V");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("========",response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void psotT(IApi iApi) {
        JSONObject object=new JSONObject();
        try {
            object.put("aaa","bbbb");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<BaseResult<String>> call = iApi.getJsonString(object);
        call.enqueue(new Callback<BaseResult<String>>() {
            @Override
            public void onResponse(Call<BaseResult<String>> call, Response<BaseResult<String>> response) {
                BaseResult<String> result=response.body();
                if(result.getStatus()==1){
                    //成功  逻辑
                    //网络返回的逻辑数据
                    Log.i("=========",response.body().getResult());
                }else {
                    //失败  逻辑

                }




            }

            @Override
            public void onFailure(Call<BaseResult<String>> call, Throwable t) {

            }
        });
    }

    private void getJson(IApi iApi) {
        JSONObject object=new JSONObject();
        try {
            object.put("aaa","bbbb");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<BaseResult> call = iApi.getJson(object);
        call.enqueue(new Callback<BaseResult>() {
            @Override
            public void onResponse(Call<BaseResult> call, Response<BaseResult> response) {
                BaseResult body = response.body();
                Log.i("===========",body.getResult().toString());
            }

            @Override
            public void onFailure(Call<BaseResult> call, Throwable t) {

            }
        });
    }

    private void postJson(IApi iApi) {
        JSONObject object=new JSONObject();
        try {
            object.put("aaa","bbbb");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Call<ResponseBody> call = iApi.postJson(object);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    Log.i("===========",string);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void post2(IApi iApi) {
        HashMap<String,String> params=new HashMap<>();
        params.put("username","qq"); //post请求  key  value
        params.put("password","ss");

        //同时 post 多个参数
        Call<ResponseBody> call = iApi.register1(params);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    Log.i("===========",string);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void post1(IApi iApi) {
        //调用方法
        Call<ResponseBody> call = iApi.register("a", "b");
        //执行
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               //UI 线程
                try {
                    String string = response.body().string();
                    Log.i("===========",string);
                    Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
