package com.gzgy.EmailService.modules.notice.mapper;

import com.gzgy.EmailService.modules.notice.entity.NotifyHwIdentificationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * <p>
  * 推送设备标识表（标识各业务系统与被推送手机的对应关系） Mapper 接口
 * </p>
 *
 * @author huangziping
 * @since 2020-03-24
 */
@Mapper
public interface NotifyHwIdentificationMapper extends BaseMapper<NotifyHwIdentificationEntity> {



}
