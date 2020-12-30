package com.tempo.testapp.repositories;

import com.tempo.testapp.entity.HistoryQuery;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends PagingAndSortingRepository<HistoryQuery, Integer> {
    //void addHistoryQuery(String endpoint, String username, int num1, int num2);
    //List<HistoryQuery> getPagedHistory(int size, int page);
}
