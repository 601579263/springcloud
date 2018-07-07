package entity.mapper;

import entity.Coupon;
import entity.CouponKey;

public interface CouponMapper {
    int deleteByPrimaryKey(CouponKey key);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    Coupon selectByPrimaryKey(CouponKey key);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKey(Coupon record);
}