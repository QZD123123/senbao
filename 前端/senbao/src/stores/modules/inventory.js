// 导入request.js请求工具
import request from '@/utils/request.js';

// 获取库存列表，支持分页和筛选
export const InventoryListService = (page = 1, pageSize = 10, materialId = '', warehouseId = '') => {
    return request.get('/inventory', {
        params: {
            page,
            pageSize,
            materialId,
            warehouseId
        }
    });
};

// 添加库存
export const addInventoryService = (inventoryData) => {
    return request.post('/inventory', JSON.stringify(inventoryData), {
        headers: {
            'Content-Type': 'application/json'
        }
    });
};

// 删除库存
export const deleteInventoryService = (id) => {
    return request.delete(`/inventory/${id}`);
};

// 编辑库存信息
export const updateInventoryService = async (id, inventoryData) => {
    try {
        const response = await request.patch(`/inventory/${id}`, JSON.stringify(inventoryData), {
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

// 获取物料列表
export const fetchMaterialsService = () => {
    return request.get('/material/getall');
};

// 获取仓库列表
export const fetchWarehousesService = () => {
    return request.get('/warehouse/getall');
};
