import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        // Handle the alarm trigger event here
        Toast.makeText(context, "Alarm Triggered!", Toast.LENGTH_SHORT).show()
        // You can also trigger any other action here when the alarm is received
    }
}
