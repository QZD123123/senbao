import request from '@/utils/request';

// 获取物料列表
export const MaterialListService = (page = 1, pageSize = 10, name = '', type = '') => {
  return request.get('/material', {
    params: {
      page: page,         // 确保是数字
      pageSize: pageSize, // 确保是数字
      name: name,
      type: type
    }
  });
};

// 获取所有物料类型
export const fetchMaterialTypesService = () => {
  return request.get('/material/getall');
};

// 添加物料
export const addMaterialService = (material) => {
  return request.post('/material', material);
};

// 更新物料
export const updateMaterialService = (id, material) => {
  return request.patch(`/material/${id}`, material);
};

// 删除物料
export const deleteMaterialService = (id) => {
  return request.delete(`/material/${id}`);
};
