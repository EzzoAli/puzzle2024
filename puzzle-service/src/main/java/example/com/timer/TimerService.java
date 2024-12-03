package example.com.timer;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class TimerService {

    private final Map<Long, LocalDateTime> timers = new HashMap<>();

    /**
     * Starts a timer for the given ID (e.g., game or puzzle ID).
     *
     * @param id The ID for which the timer is started.
     */
    public void startTimer(Long id) {
        timers.put(id, LocalDateTime.now());
    }

    /**
     * Stops the timer for the given ID.
     *
     * @param id The ID for which the timer is stopped.
     * @return The duration for which the timer ran.
     */
    public Duration stopTimer(Long id) {
        LocalDateTime startTime = timers.remove(id);
        if (startTime == null) {
            throw new IllegalArgumentException("No timer found for ID: " + id);
        }
        return Duration.between(startTime, LocalDateTime.now());
    }

    /**
     * Checks if the timer for the given ID has expired.
     *
     * @param id       The ID for which to check the timer.
     * @param duration The duration limit for the timer.
     * @return true if the timer has expired, false otherwise.
     */
    public boolean isTimerExpired(Long id, Duration duration) {
        LocalDateTime startTime = timers.get(id);
        if (startTime == null) {
            throw new IllegalArgumentException("No timer found for ID: " + id);
        }
        return Duration.between(startTime, LocalDateTime.now()).compareTo(duration) > 0;
    }

    /**
     * Resets the timer for a specific ID.
     *
     * @param id The ID for which the timer is reset.
     */
    public void resetTimer(Long id) {
        if (!timers.containsKey(id)) {
            throw new IllegalArgumentException("No timer found for ID: " + id);
        }
        timers.put(id, LocalDateTime.now());
    }
}
