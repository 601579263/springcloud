package entity.mapper;

import entity.MerBbsPublish;

public interface MerBbsPublishMapper {
    int deleteByPrimaryKey(Long bbsId);

    int insert(MerBbsPublish record);

    int insertSelective(MerBbsPublish record);

    MerBbsPublish selectByPrimaryKey(Long bbsId);

    int updateByPrimaryKeySelective(MerBbsPublish record);

    int updateByPrimaryKey(MerBbsPublish record);
}