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

// 添加仓库
export const AddWarehouseService = (warehouseData) => {
    return request.post('/warehouse', JSON.stringify(warehouseData), {
        headers: {
            'Content-Type': 'application/json'
        }
    });
};

// 编辑仓库
export const EditWarehouseService = async (id, warehouseData) => {
    try {
        const response = await request.patch(`/warehouse/${id}`, JSON.stringify(warehouseData), {
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

// 删除仓库
export const DeleteWarehouseService = (id) => {
    return request.delete(`/warehouse/${id}`);
};

// 获取管理员列表
// export const fetchAdmins = () => {
//     return request.get('/warehouse/manager');
// };
// 获取管理员列表
export const fetchAdmins = async () => {
    try {
        const response = await request.get('/warehouse/manager');
        if (response.code === 200) {
            return response.data.adminList || [];
        } else {
            console.error('获取管理员信息失败', response.data.message);
            return [];
        }
    } catch (error) {
        console.error('获取管理员信息失败', error);
        return [];
    }
};