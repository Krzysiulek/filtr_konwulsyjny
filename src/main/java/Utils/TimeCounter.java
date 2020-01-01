package Utils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TimeCounter {
    private long startTimeMilis;
    private long stopTimeMilis;
    private long elapsedTimeMilis;

    public void start() {
        startTimeMilis = System.currentTimeMillis();
    }

    public void stop() {
        stopTimeMilis = System.currentTimeMillis();
    }

    public long getTimeMilis() {
        elapsedTimeMilis = stopTimeMilis - startTimeMilis;

        return elapsedTimeMilis;
    }
}
