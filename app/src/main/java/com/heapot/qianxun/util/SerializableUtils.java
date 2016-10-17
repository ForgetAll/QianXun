package com.heapot.qianxun.util;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Karl on 2016/9/12.
 * 序列化工具
 */
public class SerializableUtils {
    /**
     * 序列化数据
     * @param fileName  缓存文件名
     * @param t
     * @param <T>
     */
    public static <T> void setSerializable(Context context, String fileName, T t){
        ObjectOutputStream oos = null;
        try {
            FileOutputStream fos =
                    new FileOutputStream(new File(context.getFilesDir()+fileName));
            oos = new ObjectOutputStream(fos);
            oos.writeObject(t);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 反序列化数据
     * @param context
     * @param fileName
     * @param <T>
     * @return
     */
    public static <T> T getSerializable(Context context,String fileName){
        ObjectInputStream ois = null;
        try {
            File file = new File(context.getFilesDir()+fileName);
            if (!file.exists()){
                file.createNewFile();
            }
            if (file.length() == 0){
                return null;
            }
            FileInputStream fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            return (T)ois.readObject();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  null;
    }

    /**
     * 删除文件
     * @param context
     * @param fileName
     * @return
     */
    public static boolean deleteSerializable(Context context,String fileName){
        File file  = new File(context.getFilesDir()+fileName);
        if (file.exists()){
            return file.delete();
        }
        return false;
    }

}
