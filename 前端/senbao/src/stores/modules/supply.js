// 导入request.js请求工具
import request from '@/utils/request.js';

// 获取供应记录列表，支持分页和筛选
export const SupplyListService = (page = 1, pageSize = 10, materialId = '', supplierId = '') => {
    return request.get('/supply', {
        params: {
            page,
            pageSize,
            materialId,
            supplierId
        }
    });
};

// 添加供应记录
export const addSupplyService = (supplyData) => {
    return request.post('/supply', JSON.stringify(supplyData), {
        headers: {
            'Content-Type': 'application/json'
        }
    });
};

// 删除供应记录
export const deleteSupplyService = (id) => {
    return request.delete(`/supply/${id}`);
};

// 编辑供应记录信息
export const updateSupplyService = async (id, supplyData) => {
    try {
        const response = await request.patch(`/supply/${id}`, JSON.stringify(supplyData), {
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

// 获取供应商列表
export const fetchSuppliersService = () => {
    return request.get('/supplier/getall');
};

// 获取仓库列表
export const fetchWarehousesService = () => {
    return request.get('/warehouse/getall');
};
