package util;

import android.content.Context;
import android.content.SharedPreferences;


/*版本更新的 SharedPreferences*/
public class DjhSputils {
    private final String key_cancle_update = "cancel_update_time";


    private SharedPreferences sp;

    public DjhSputils(Context c) {
        sp = c.getApplicationContext().getSharedPreferences("djhTeacher",
                Context.MODE_PRIVATE);
    }

    public String getCancleUpdateTime() {
        return sp.getString(key_cancle_update, "");
    }

    public void setCancleUpdateTime(String cancel_update_time) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key_cancle_update, cancel_update_time);
        edit.commit();
    }


}
