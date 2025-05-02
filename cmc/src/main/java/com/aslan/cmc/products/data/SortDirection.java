package com.aslan.cmc.products.data;

public enum SortDirection {
    ASC,
    DESC;

    public static SortDirection fromString(String value) {
        try {
            return value != null ? SortDirection.valueOf(value.toUpperCase()) : SortDirection.ASC;
        } catch (Exception e) {
            return SortDirection.DESC;
        }
    }
}
