package com.aslan.cmc.products.data;

public enum SortDirection {
    asc,
    desc;

    public static SortDirection fromString(String value) {
        try {
            return value != null ? SortDirection.valueOf(value.toUpperCase()) : SortDirection.asc;
        } catch (Exception e) {
            return SortDirection.desc;
        }
    }
}
