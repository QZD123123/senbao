// 导入request.js请求工具
import request from '@/utils/request.js';

// 获取仓库列表，支持分页
export const WarehouseListService = (page = 1, pageSize = 10) => {
    return request.get('/warehouse', {
        params: {
            page,
            pageSize,
        }
    });
};