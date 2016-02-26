package fr.esgi.iam.uefa.singleton;

import android.content.Context;

/**
 * Created by MichaelWayne on 12/02/2016.
 *
 * because when you come back to the first activity, the onCreate method is called, so your textView is a new textView. Take a look to Activity Lifecycle, here is explained much better.
 */
public class MySingleton {

    private static MySingleton INSTANCE = null;
    private String textViewInformation;
    private int imageViewInformation;

    private MySingleton() {

    }

    public static MySingleton getInstance(Context context) {
        synchronized (MySingleton.class) {
            if (INSTANCE == null) {
                synchronized (MySingleton.class) {
                    if (INSTANCE == null)
                        INSTANCE = new MySingleton();
                }
            }
        }
        return INSTANCE;
    }

    public String getTextViewInformation(){
        return textViewInformation;
    }

    public void setTextViewInformation(String textViewInfo){
        textViewInformation = textViewInfo;
    }

    public int getImageViewInformation(){
        return imageViewInformation;
    }

    public void setImageViewInformation(int imageViewInfo){
        imageViewInformation = imageViewInfo;
    }




}
