package entity.mapper;

import entity.ResServicingFacilitiesKey;

public interface ResServicingFacilitiesMapper {
    int deleteByPrimaryKey(ResServicingFacilitiesKey key);

    int insert(ResServicingFacilitiesKey record);

    int insertSelective(ResServicingFacilitiesKey record);
}