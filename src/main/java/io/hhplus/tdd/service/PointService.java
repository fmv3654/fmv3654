package io.hhplus.tdd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hhplus.tdd.dto.PointHistoryResDTO;
import io.hhplus.tdd.dto.UserPointResDTO;
import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.point.TransactionType;


@Service
public class PointService {
	
    private final UserPointTable userPointTable;
    private final PointHistoryTable pointHistoryTable;
	

    @Autowired
    public PointService(UserPointTable userPointTable, PointHistoryTable pointHistoryTable) {
    	this.userPointTable = userPointTable;
	this.pointHistoryTable = pointHistoryTable;
    }
    
     
    //유저 포인트 정보 조회
    public UserPointResDTO getUserPoint(Long userId) throws InterruptedException {
        
    	UserPoint userPoint = userPointTable.selectById(userId);
		
   		return UserPointResDTO.of(userPoint);
    }

    //유저 포인트 이력조회
    public List<PointHistoryResDTO> getUserPointHistories(Long userId) {
		List<PointHistory> userPointHistories = pointHistoryTable.selectAllByUserId(userId);
		List<PointHistoryResDTO> pointHistoryResDTOs = new ArrayList<>();
		    
		for (PointHistory pointHistory : userPointHistories) {
			 PointHistoryResDTO userPointHistoryResDTO = PointHistoryResDTO.of(pointHistory);
			 pointHistoryResDTOs.add(userPointHistoryResDTO);
		    }
		    
		return pointHistoryResDTOs;
    }
	
    //유저 포인트 충전
    public UserPoint chargeUserPoint(Long id, Long amount) throws InterruptedException {
		 
		 UserPoint userPoint = userPointTable.selectById(id);
	     Long result = userPoint.point() + amount;
	    
	     
	     pointHistoryTable.insert(id, result, TransactionType.CHARGE, System.currentTimeMillis());
	     return userPointTable.insertOrUpdate(userPoint.id(), result);
    }
	
     
    //유저 포인트 사용
    public UserPoint useUserPoint(Long userId, Long amount) throws InterruptedException {
 		
 		UserPoint originUserPoint = userPointTable.selectById(userId);
 		
 		
 		if (originUserPoint.point() < amount) {
			throw new RuntimeException("Not enough points to use");
		}
 		
 		Long result = originUserPoint.point() - amount;
 		pointHistoryTable.insert(userId, result, TransactionType.USE, System.currentTimeMillis());
	     
 		return userPointTable.insertOrUpdate(originUserPoint.id(), result);
		
     }



}
