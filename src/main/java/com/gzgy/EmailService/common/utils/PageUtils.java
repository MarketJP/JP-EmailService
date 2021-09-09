package com.gzgy.EmailService.common.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gzgy.EmailService.common.base.BasePageRequest;
import org.apache.ibatis.session.RowBounds;

public class PageUtils {

    //分页
    @SuppressWarnings("rawtypes")
    public static Page buildPage(BasePageRequest request) {
        Boolean isPage = request.getIfPage();
        int current = 1;
        int size = 10;
        if (!isPage) {
            current = RowBounds.NO_ROW_OFFSET;
//            size = RowBounds.NO_ROW_LIMIT;
            size = 500;
        } else {
            Integer page = request.getPage();
            Integer limit = request.getLimit();

            current = page > 1 ? page : 1;
            size = limit > 0 ? limit : 10;
        }
        Page page = new Page(current, size);
        return page;

    }
}
