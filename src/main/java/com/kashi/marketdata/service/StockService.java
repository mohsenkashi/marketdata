package com.kashi.marketdata.service;

import com.kashi.marketdata.exception.IntegrityViolationException;
import com.kashi.marketdata.model.Stock;
import com.kashi.marketdata.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(@Autowired StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Iterable<Stock> findAll() {
        return stockRepository.findAll();
    }

    public Optional<Stock> findById(Long id) {
        return stockRepository.findById(id);
    }

    public Stock save(Stock stock) {
        try {
            return stockRepository.save(stock);
        } catch (DataIntegrityViolationException e) {
            throw new IntegrityViolationException(e);
        }
    }

    public Optional<Stock> updatePrice(Long id, Double price) {
        return stockRepository.findById(id).map(stock -> {
            stock.setCurrentPrice(price);
            stock.setLastUpdate(new Timestamp(System.currentTimeMillis()));
            stockRepository.save(stock);
            return stock;
        });
    }
}
