package com.gzgy.EmailService.modules.notice.service.impl;

import com.gzgy.EmailService.modules.notice.entity.NotifyRecordEntity;
import com.gzgy.EmailService.modules.notice.mapper.NotifyRecordMapper;
import com.gzgy.EmailService.modules.notice.service.INotifyRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * App推送记录 服务实现类
 * </p>
 *
 * @author huangziping
 * @since 2020-03-24
 */
@Service
public class NotifyRecordServiceImpl extends ServiceImpl<NotifyRecordMapper, NotifyRecordEntity> implements INotifyRecordService {

	private static final Logger logger = LoggerFactory.getLogger(NotifyRecordServiceImpl.class);

	@Autowired
	private NotifyRecordMapper notifyRecordMapper;

}
