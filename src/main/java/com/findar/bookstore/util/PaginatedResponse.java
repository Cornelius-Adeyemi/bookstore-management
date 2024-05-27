package com.findar.bookstore.util;

import java.util.HashMap;

public class PaginatedResponse {


    public static HashMap<String, Object> paginatedResponse(
            Object content, int currentPage, int totalPages, Long totalItems,
            boolean isFirstPage, boolean isLastPage){
        HashMap<String, Object> objectHashMap = new HashMap<>();
        objectHashMap.put("content", content);
        objectHashMap.put("currentPage", currentPage);
        objectHashMap.put("totalPages", totalPages);
        objectHashMap.put("totalItems", totalItems);
        objectHashMap.put("isFirstPage", isFirstPage);
        objectHashMap.put("isLastPage", isLastPage);


        return objectHashMap;
    }
}
