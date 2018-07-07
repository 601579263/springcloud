package entity.mapper;

import entity.SensorType;

public interface SensorTypeMapper {
    int deleteByPrimaryKey(Integer stId);

    int insert(SensorType record);

    int insertSelective(SensorType record);

    SensorType selectByPrimaryKey(Integer stId);

    int updateByPrimaryKeySelective(SensorType record);

    int updateByPrimaryKey(SensorType record);
}