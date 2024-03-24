package com.kisahcode.fundamentalandroid.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Utility class for managing app execution threads.
 */
class AppExecutors {

    // Executor for disk I/O operations
    val diskIO: Executor = Executors.newSingleThreadExecutor()

    // Executor for network I/O operations
    val networkIO: Executor = Executors.newFixedThreadPool(3)

    // Executor for main thread operations
    val mainThread: Executor = MainThreadExecutor()

    /**
     * Custom executor for executing tasks on the main thread.
     */
    private class MainThreadExecutor : Executor {

        // Handler associated with the main thread
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        /**
         * Executes the given command on the main thread.
         * @param command The command to be executed
         */
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}