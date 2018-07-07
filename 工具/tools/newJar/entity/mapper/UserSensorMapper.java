package entity.mapper;

import entity.UserSensor;

public interface UserSensorMapper {
    int deleteByPrimaryKey(Integer sId);

    int insert(UserSensor record);

    int insertSelective(UserSensor record);

    UserSensor selectByPrimaryKey(Integer sId);

    int updateByPrimaryKeySelective(UserSensor record);

    int updateByPrimaryKey(UserSensor record);
}