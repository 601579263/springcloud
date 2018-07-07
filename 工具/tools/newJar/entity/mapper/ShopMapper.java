package entity.mapper;

import entity.Shop;

public interface ShopMapper {
    int deleteByPrimaryKey(Long sId);

    int insert(Shop record);

    int insertSelective(Shop record);

    Shop selectByPrimaryKey(Long sId);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKey(Shop record);
}