package justsw.tonypeng.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReviewDialog {
    private static final String PREF_NAME = "ReviewDialog";
    private static final String PREF_KEY_NEED_ASK = "PREF_KEY_NEED_ASK";
    private static final String PREF_KEY_LAUNCH_TIMES = "PREF_KEY_LAUNCH_TIMES";
    private static final String PREF_KEY_LAST_ASK_TIME = "PREF_KEY_LAST_ASK_TIME";

    public static void show(Context context, int cowndown) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(!sharedPreferences.getBoolean(PREF_KEY_NEED_ASK, true)) return;

        long lastAskTime = sharedPreferences.getLong(PREF_KEY_LAST_ASK_TIME, 0);
        int launchTimes = sharedPreferences.getInt(PREF_KEY_LAUNCH_TIMES, 0);
        if(launchTimes >= cowndown)
        {
            if(lastAskTime != 0)
            {
                Date date = new Date(System.currentTimeMillis());

                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.DATE, -1);
                date = cal.getTime();

                if(date.getTime() > lastAskTime) {
                    showReviewDialog(context);
                }
            }
            else
            {
                showReviewDialog(context);
            }
        }
        else {
            editor.putInt(PREF_KEY_LAUNCH_TIMES, launchTimes + 1);
            editor.apply();
        }
    }

    private static void showReviewDialog(Context context) {
        final Context contextInDialog = context;
        final SharedPreferences sharedPreferences = contextInDialog.getSharedPreferences(PREF_NAME, 0);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        String app_name = contextInDialog.getApplicationInfo().loadLabel(contextInDialog.getPackageManager()).toString();
        String messageString;
        String rateString;
        String nothanksString;
        String remindString;
        editor.putLong(PREF_KEY_LAST_ASK_TIME, new Date(System.currentTimeMillis()).getTime());
        editor.apply();

        messageString = context.getString(R.string.str_message);
        rateString = context.getString(R.string.str_rate);
        nothanksString = context.getString(R.string.str_nothanks);
        remindString = context.getString(R.string.str_remind);

        if(!((AppCompatActivity)context).isFinishing()) {
            new AlertDialog.Builder(context)
                    .setMessage(String.format(Locale.getDefault(), messageString, app_name))
                    .setNegativeButton(nothanksString, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            editor.putBoolean(PREF_KEY_NEED_ASK, false);
                            editor.apply();
                            dialogInterface.dismiss();
                        }
                    })
                    .setNeutralButton(remindString, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .setPositiveButton(String.format(Locale.getDefault(), rateString, app_name), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            final String appPackageName = contextInDialog.getPackageName();
                            try {
                                contextInDialog.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            } catch (android.content.ActivityNotFoundException e) {
                                contextInDialog.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                            editor.putBoolean(PREF_KEY_NEED_ASK, false);
                            editor.apply();
                            dialogInterface.dismiss();
                        }
                    })
                    .show();

        }
    }
}
