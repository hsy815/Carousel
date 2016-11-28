package hsy.com.carousel.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hsy on 16/9/21.
 */

public class ThreadUtil {
    public static ExecutorService threadPool = Executors.newFixedThreadPool(6);
}
