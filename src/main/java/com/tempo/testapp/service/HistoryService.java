package com.tempo.testapp.service;

import com.tempo.testapp.entity.HistoryQuery;

import java.util.List;

public interface HistoryService {
    void addHistorySuma(String endpoint, String username, int num1, int num2);
    void addHistoryEndpoint(String endpoint, String username);

    // List<HistoryQuery> getPagedHistory(int size, int page);
    List<HistoryQuery> getHistory(Integer page, Integer size);
}
