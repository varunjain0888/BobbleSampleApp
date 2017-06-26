package com.bobble.bobblesampleapp.util;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by varunjain on 27/06/17.
 */
public final class CloseUtil {

    private CloseUtil() {
        // This class is not publicly instantiable.
    }

    public static final void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {


            }
        }
    }


    public static final void close(Socket closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {


            }
        }
    }


    public static final void close(ServerSocket closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {


            }
        }
    }
}