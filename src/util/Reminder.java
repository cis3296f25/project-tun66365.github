
package util;

import java.util.Timer;
import java.util.TimerTask;

public class Reminder {
    public static void schedule(long delayMs, Runnable task) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() { task.run(); }
        }, delayMs);
    }
}
