package com.ow.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author lavnote
 */
public class GsonUtil {

    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().serializeNulls().serializeSpecialFloatingPointValues().create();

    /**
     * @return
     */
    public static Gson gson() {
        return GSON;
    }
}
