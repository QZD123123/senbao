// 导入request.js请求工具
import request from '@/utils/request.js';

// 获取订单列表，支持分页
export const OrderListService = (page = 1, pageSize = 10, progress = '') => {
    return request.get('/order', {
        params: {
            page,
            pageSize,
            progress
        }
    });
};

// 添加订单
export const addOrderService = (orderData) => {
    return request.post('/order', JSON.stringify(orderData), {
        headers: {
            'Content-Type': 'application/json'
        }
    });
};

// 删除订单
export const deleteOrderService = (id) => {
    return request.delete(`/order/${id}`);
};

// 编辑订单信息
export const updateOrderService = async (id, orderData) => {
    try {
        const response = await request.patch(`/order/${id}`, JSON.stringify(orderData), {
            headers: {
                'Content-Type': 'application/json'
            }
        });
        return response;
    } catch (error) {
        console.error("更新失败：", error);
        throw error;
    }
};
