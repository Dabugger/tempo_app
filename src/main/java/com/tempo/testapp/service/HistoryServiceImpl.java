package com.tempo.testapp.service;

import com.tempo.testapp.entity.HistoryQuery;
import com.tempo.testapp.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService{

    @Autowired
    HistoryRepository historyRepository;

    @Override
    public void addHistorySuma(String endpoint, String username, int num1, int num2) {
        System.out.println("INSERT INTO HISTORY TABLE.");
        HistoryQuery historyQuery = new HistoryQuery(endpoint, username, num1, num2);
        historyRepository.save(historyQuery);
    }

    @Override
    public void addHistoryEndpoint(String endpoint, String username) {
        HistoryQuery historyQuery = new HistoryQuery(endpoint, username);
        historyRepository.save(historyQuery);
    }

    @Override
    public List<HistoryQuery> getHistory(Integer page, Integer size) {
        Page<HistoryQuery> history = historyRepository.findAll(PageRequest.of(page, size));
        return history.toList();
    }

}
