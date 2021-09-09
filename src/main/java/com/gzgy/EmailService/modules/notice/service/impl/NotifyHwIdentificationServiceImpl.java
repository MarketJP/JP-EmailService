package com.gzgy.EmailService.modules.notice.service.impl;

import com.gzgy.EmailService.modules.notice.entity.NotifyHwIdentificationEntity;
import com.gzgy.EmailService.modules.notice.mapper.NotifyHwIdentificationMapper;
import com.gzgy.EmailService.modules.notice.service.INotifyHwIdentificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 推送设备标识表（标识各业务系统与被推送手机的对应关系） 服务实现类
 * </p>
 *
 * @author huangziping
 * @since 2020-03-24
 */
@Service
public class NotifyHwIdentificationServiceImpl extends ServiceImpl<NotifyHwIdentificationMapper, NotifyHwIdentificationEntity> implements INotifyHwIdentificationService {

	private static final Logger logger = LoggerFactory.getLogger(NotifyHwIdentificationServiceImpl.class);

	@Autowired
	private NotifyHwIdentificationMapper notifyHwIdentificationMapper;

}
