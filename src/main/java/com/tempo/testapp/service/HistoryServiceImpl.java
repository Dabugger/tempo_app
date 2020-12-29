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
@Transactional
public class HistoryServiceImpl implements HistoryService{

    @Autowired
    HistoryRepository historyRepository;

    @Override
    public void addHistory(String endpoint, String username, int num1, int num2) {
        System.out.println("INSERT INTO HISTORY TABLE.");
        historyRepository.addHistoryQuery(endpoint, username, num1, num2);
    }

    @Override
    public List<HistoryQuery> getPagedHistory(int size, int page) {

        return historyRepository.getPagedHistory(size, page);

    }


    /*
        @Override
        public Page<HistoryQuery> getHistory() {

            Pageable firstPageWithTwoElements = PageRequest.of(0, 2);
            Page<HistoryQuery> allHistory = historyRepository.findAll((org.springframework.data.domain.Pageable) firstPageWithTwoElements);
            return allHistory;
        }

    */
    public List<HistoryQuery> findAll(Integer page, Integer size) {
        Page<HistoryQuery> logEntries = historyRepository.findAll(PageRequest.of(page, size));
        return logEntries.toList();
    }

}
