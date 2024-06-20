package io.hhplus.tdd.dto;

import io.hhplus.tdd.point.UserPoint;

public record UserPointResDTO(
	Long id,
	Long point,
	Long updateMillis
) {
	public static UserPointResDTO of(UserPoint userPoint) {
		return new UserPointResDTO(
			userPoint.id(),
			userPoint.point(),
			userPoint.updateMillis()
		);
	}
}