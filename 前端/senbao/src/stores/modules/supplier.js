// 导入 request.js 请求工具
import request from '@/utils/request.js';

// 获取供应商列表，支持分页
export const fetchSuppliers = (page = 1, pageSize = 10) => {
    return request.get('/supplier', {
        params: { page, pageSize }
    }).then(response => {
        if (response.code === 200) {
            return response.data; // 返回 data 对象
        } else {
            throw new Error(response.message || '获取供应商信息失败');
        }
    });
};

// 添加供应商
export const AddSupplierService = (supplierData) => {
    return request.post('/supplier', JSON.stringify(supplierData), {
        headers: { 'Content-Type': 'application/json' }
    });
};

// 编辑供应商
export const EditSupplierService = (id, supplierData) => {
    return request.patch(`/supplier/${id}`, JSON.stringify(supplierData), {
        headers: { 'Content-Type': 'application/json' }
    });
};

// 删除供应商
export const DeleteSupplierService = (id) => {
    return request.delete(`/supplier/${id}`);
};