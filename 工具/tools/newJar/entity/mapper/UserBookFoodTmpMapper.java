package entity.mapper;

import entity.UserBookFoodTmp;

public interface UserBookFoodTmpMapper {
    int deleteByPrimaryKey(Long bId);

    int insert(UserBookFoodTmp record);

    int insertSelective(UserBookFoodTmp record);

    UserBookFoodTmp selectByPrimaryKey(Long bId);

    int updateByPrimaryKeySelective(UserBookFoodTmp record);

    int updateByPrimaryKey(UserBookFoodTmp record);
}