// 导入request.js请求工具
import request from '@/utils/request.js';

// 获取生产成本列表，支持分页和筛选
export const fetchProductionCostsService = (page = 1, pageSize = 10, materialId = '', warehouseId = '') => {
  return request.get('/consumption', {
    params: {
      page,
      pageSize,
      materialId,
      warehouseId
    }
  });
};

// 添加生产成本
export const addProductionCostService = (costData) => {
  return request.post('/consumption', JSON.stringify(costData), {
    headers: {
      'Content-Type': 'application/json'
    }
  });
};

// 删除生产成本
export const deleteProductionCostService = (id) => {
  return request.delete(`/consumption/${id}`);
};

// 编辑生产成本信息
export const updateProductionCostService = async (id, costData) => {
  try {
    const response = await request.patch(`/consumption/${id}`, JSON.stringify(costData), {
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
