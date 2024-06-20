package io.hhplus.tdd.dto;

import java.util.Objects;

import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;

public record PointHistoryResDTO(
	    Long id,
	    Long userId,
	    TransactionType type,
	    Long amount,
	    Long timeMillis
	) {
	    public PointHistoryResDTO {
	        Objects.requireNonNull(id, "id must not be null");
	        Objects.requireNonNull(userId, "userId must not be null");
	        Objects.requireNonNull(type, "type must not be null");
	        Objects.requireNonNull(amount, "amount must not be null");
	        Objects.requireNonNull(timeMillis, "timeMillis must not be null");
	    }
	    
	    public static PointHistoryResDTO of(PointHistory pointHistory) {
	        return new PointHistoryResDTO(
	            pointHistory.id(),
	            pointHistory.userId(),
	            pointHistory.type(),
	            pointHistory.amount(),
	            pointHistory.updateMillis()
	        );
	    }
	}
