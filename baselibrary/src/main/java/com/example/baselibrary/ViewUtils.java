package com.example.baselibrary;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * author: created by wentaoKing
 * date: created in 2019-12-29
 * description:
 */
public class ViewUtils {

    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);
    }

    public static void inject(View view) {
        inject(new ViewFinder(view), view);
    }

    /**
     * @param viewFinder view
     * @param object     反射需要执行的类
     */
    private static void inject(ViewFinder viewFinder, Object object) {
        injectField(viewFinder, object);
        injectClick(viewFinder,object);
    }

    /**
     * 注入变量
     *
     * @param viewFinder
     * @param object
     */
    private static void injectField(ViewFinder viewFinder, Object object) {
        //1. 获取类的所有属性
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        //2. 获取ViewById里的value
        for (Field field : fields) {
            ViewById viewById = field.getAnnotation(ViewById.class);
            if (viewById != null) {
                //获取注解的id
                int viewId = viewById.value();
                //3. findViewById 找到View
                View view = viewFinder.findViewById(viewId);
                //可以注入所有修饰符
                field.setAccessible(true);
                //4. 动态注入找到的view
                try {
                    field.set(object, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 点击事件的注入
     */
    private static void injectClick(ViewFinder viewFinder, Object object) {
        //1. 获取类的所有属性
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        //2.获取OnClick里的value值
        for (Method method : methods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null){
                //3.找到OnClick的viewId
                int[] viewIds = onClick.value();
                for (int viewId : viewIds){
                    View view = viewFinder.findViewById(viewId);
                    if ((view != null)){
                        view.setOnClickListener(new DeclaredOnClickListener(method,object));
                    }
                }
            }
        }
    }

    public static class DeclaredOnClickListener implements View.OnClickListener{

        private Method mMethod;
        private Object mObject;

        public DeclaredOnClickListener(Method method,Object object){
            this.mMethod = method;
            this.mObject = object;
        }

        @Override
        public void onClick(View v) {
            try {
                mMethod.setAccessible(true);
                //反射调用方法
                mMethod.invoke(mObject,v);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

}
