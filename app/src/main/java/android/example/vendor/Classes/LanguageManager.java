package android.example.vendor.Classes;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageManager {

    private Context context;

    public LanguageManager(Context context){
        this.context=context;
    }

    public void updateResource(String langcode){
        Locale locale=new Locale(langcode);
        Locale.setDefault(locale);
        Resources resources=context.getResources();
        Configuration configuration=resources.getConfiguration();
        configuration.setLocale(locale);
//        configuration.setLayoutDirection(locale);
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
    }
}
