package com.kalida.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class CSV extends JSONObject{

    private final String[] columns;
    private final List<JSONArray> rows = new ArrayList<>();

    boolean countRows = true;

    public CSV(String... columns) {
        this.columns = columns;
        put("columns", columns);
        put("rows", rows);
    }

    public void addRow(Object... row) {
        addRow(true, row);
    }

    public void addRow(boolean validateRowsLenght, Object... row) {
        if (row.length != columns.length && validateRowsLenght) {
            throw new IllegalArgumentException(
                    "CSV row.lenght != colums.lenght: column: " + Arrays.toString(columns) + " rows: " + Arrays.toString(row));
        }
        JSONArray jArray = new JSONArray();
        jArray.addAll(Arrays.asList(row));
        rows.add(jArray);
    }
}
