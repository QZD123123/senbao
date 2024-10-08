<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>供应记录</span>
        <div class="extra">
          <el-button type="primary" @click="openAddSupplyDialog">添加供应记录</el-button>
        </div>
      </div>
    </template>

    <!-- 筛选器 -->
    <div class="filter-container">
      <el-select v-model="selectedMaterial" placeholder="选择物料" @change="fetchSupplyData">
        <el-option label="全部" value=""></el-option>
        <el-option
          v-for="(material, index) in materials"
          :key="index"
          :label="material.name"
          :value="material.id">
        </el-option>
      </el-select>

      <el-select v-model="selectedSupplier" placeholder="选择供应商" @change="fetchSupplyData">
        <el-option label="全部" value=""></el-option>
        <el-option
          v-for="(supplier, index) in suppliers"
          :key="index"
          :label="supplier.name"
          :value="supplier.id">
        </el-option>
      </el-select>
    </div>

    <el-table :data="supplyData.supplyList" style="width: 100%" stripe>
      <el-table-column fixed prop="id" label="供应记录编号" width="150" />
      <el-table-column
        prop="materialId"
        label="物料名称"
        width="200">
        <template #default="{ row }">
          {{ getMaterialName(row.materialId) }}
        </template>
      </el-table-column>
      <el-table-column
        prop="materialId"
        label="物料类型"
        width="150">
        <template #default="{ row }">
          {{ getMaterialType(row.materialId) }}
        </template>
      </el-table-column>
      <el-table-column
        prop="supplierId"
        label="供应商名称"
        width="200">
        <template #default="{ row }">
          {{ getSupplierName(row.supplierId) }}
        </template>
      </el-table-column>
      <el-table-column
        prop="warehouseId"
        label="仓库位置"
        width="200">
        <template #default="{ row }">
          {{ getWarehouseLocation(row.warehouseId) }}
        </template>
      </el-table-column>
      <el-table-column prop="quantity" label="数量" width="120" />
      <el-table-column prop="cost" label="花费" width="120" />
      <el-table-column prop="createTime" label="供应日期" width="150">
        <template #default="{ row }">
          {{ formatDate(row.createTime) }}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" min-width="150">
        <template #default="{ row }">
          <el-button link type="primary" size="large" @click="openEditSupplyDialog(row)">编辑</el-button>
          <el-button link type="danger" size="large" @click="deleteSupply(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加/编辑供应记录弹窗 -->
    <el-dialog v-model="dialogVisible" :title="isEditing ? '编辑供应记录' : '添加供应记录'" width="50%">
      <el-form :model="supplyModel" :rules="rules" label-width="120px" style="padding-right: 30px" ref="formRef">
        <el-form-item label="物料" prop="materialId">
          <el-select v-model="supplyModel.materialId">
            <el-option
              v-for="(material, index) in materials"
              :key="index"
              :label="material.name"
              :value="material.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="供应商" prop="supplierId">
          <el-select v-model="supplyModel.supplierId">
            <el-option
              v-for="(supplier, index) in suppliers"
              :key="index"
              :label="supplier.name"
              :value="supplier.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="仓库" prop="warehouseId">
          <el-select v-model="supplyModel.warehouseId">
            <el-option
              v-for="(warehouse, index) in warehouses"
              :key="index"
              :label="warehouse.location"
              :value="warehouse.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数量" prop="quantity">
          <el-input v-model="supplyModel.quantity" type="number" min="0"></el-input>
        </el-form-item>
        <el-form-item label="花费" prop="cost">
          <el-input v-model="supplyModel.cost" type="number" min="0" step="0.01"></el-input>
        </el-form-item>
        <el-form-item label="供应日期" prop="createTime">
          <el-date-picker v-model="supplyModel.createTime" type="date" placeholder="选择供应日期"></el-date-picker>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="isEditing ? updateSupply() : addSupply()">
            {{ isEditing ? '更新' : '确认' }}
          </el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 分页控件 -->
    <div class="pagination-container">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                      :current-page="currentPage" :page-size="pageSize" :total="supplyData.supplyTotal"
                      layout="total, prev, pager, next, jumper">
      </el-pagination>
    </div>
  </el-card>
</template>

<script lang="ts" setup>
import { ElMessage } from 'element-plus';
import { ref, onMounted, watch } from 'vue';
import { SupplyListService, addSupplyService, deleteSupplyService, updateSupplyService, fetchMaterialsService, fetchSuppliersService, fetchWarehousesService } from '@/stores/modules/supply.js';

// 分页相关
const currentPage = ref(1);
const pageSize = ref(10);
const supplyData = ref({
  supplyList: [],
  supplyTotal: 0
});

// 筛选相关
const selectedMaterial = ref('');  // 当前选择的物料编号
const selectedSupplier = ref('');  // 当前选择的供应商编号
const materials = ref([]); // 物料列表
const suppliers = ref([]); // 供应商列表
const warehouses = ref([]); // 仓库列表

// 弹窗相关
const dialogVisible = ref(false);
const isEditing = ref(false);
const supplyModel = ref({
  id: '',
  materialId: '',
  supplierId: '',
  warehouseId: '',
  quantity: 0,
  cost: 0,
  createTime: ''
});

// 表单引用
const formRef = ref(null);

// 验证规则
const rules = {
  materialId: [{ required: true, message: '请选择物料', trigger: 'change' }],
  supplierId: [{ required: true, message: '请选择供应商', trigger: 'change' }],
  warehouseId: [{ required: true, message: '请选择仓库', trigger: 'change' }],
  quantity: [{ required: true, message: '请输入数量', trigger: 'blur' }],
  cost: [{ required: true, message: '请输入花费', trigger: 'blur' }],
  createTime: [{ required: true, message: '请选择供应日期', trigger: 'change' }]
};

// 获取供应记录数据
const fetchSupplyData = async () => {
  try {
    const result = await SupplyListService(currentPage.value, pageSize.value, selectedMaterial.value, selectedSupplier.value);
    if (result.code === 200) {
      ElMessage.success(result.data.tip || '成功获取供应记录信息');
      supplyData.value = result.data;
    } else {
      ElMessage.error(result.data.tip || '获取供应记录信息失败');
    }
  } catch (error) {
    ElMessage.error('获取供应记录信息失败');
  }
};

// 获取物料、供应商和仓库数据
const fetchMaterialsAndSuppliersAndWarehouses = async () => {
  try {
    const materialsResult = await fetchMaterialsService();
    const suppliersResult = await fetchSuppliersService();
    const warehousesResult = await fetchWarehousesService();
    if (materialsResult.code === 200 && suppliersResult.code === 200 && warehousesResult.code === 200) {
      materials.value = materialsResult.data.list || [];
      suppliers.value = suppliersResult.data.list || [];
      warehouses.value = warehousesResult.data.list || [];
    } else {
      ElMessage.error('获取物料、供应商或仓库信息失败');
    }
  } catch (error) {
    ElMessage.error('获取物料、供应商或仓库信息失败');
  }
};

// 获取物料名称
const getMaterialName = (materialId) => {
  const material = materials.value.find(mat => mat.id === materialId);
  return material ? material.name : '未知物料';
};

// 获取物料类型
const getMaterialType = (materialId) => {
  const material = materials.value.find(mat => mat.id === materialId);
  return material ? material.type : '未知类型';
};

// 获取供应商名称
const getSupplierName = (supplierId) => {
  const supplier = suppliers.value.find(sup => sup.id === supplierId);
  return supplier ? supplier.name : '未知供应商';
};

// 获取仓库位置
const getWarehouseLocation = (warehouseId) => {
  const warehouse = warehouses.value.find(wh => wh.id === warehouseId);
  return warehouse ? warehouse.location : '未知位置';
};

// 格式化日期
const formatDate = (date) => {
  const d = new Date(date);
  return d.toLocaleDateString(); // 或者使用更精确的格式化
};

// 处理分页大小变化
const handleSizeChange = (size) => {
  pageSize.value = size;
  fetchSupplyData();
};

// 处理当前页变化
const handleCurrentChange = (page) => {
  currentPage.value = page;
  fetchSupplyData();
};

// 打开添加供应记录弹窗
const openAddSupplyDialog = () => {
  isEditing.value = false;
  supplyModel.value = {
    id: '',
    materialId: '',
    supplierId: '',
    warehouseId: '',
    quantity: 0,
    cost: 0,
    createTime: ''
  };
  dialogVisible.value = true;
};

// 打开编辑供应记录弹窗
const openEditSupplyDialog = (row) => {
  isEditing.value = true;
  supplyModel.value = { ...row, createTime: new Date(row.createTime).toISOString().split('T')[0] }; // 映射日期字段
  dialogVisible.value = true;
};

// 添加供应记录
const addSupply = async () => {
  try {
    // 进行表单验证
    const isValid = await formRef.value.validate();
    if (!isValid) {
      return; // 如果表单无效，则阻止进一步执行
    }

    const result = await addSupplyService(supplyModel.value);
    if (result.code === 200) {
      ElMessage.success(result.data.tip || '供应记录添加成功');
      dialogVisible.value = false;
      fetchSupplyData();
    } else {
      ElMessage.error(result.data.tip || '添加供应记录失败');
    }
  } catch (error) {
    ElMessage.error('发生错误，请稍后重试');
  }
};

// 更新供应记录
const updateSupply = async () => {
  try {
    // 进行表单验证
    const isValid = await formRef.value.validate();
    if (!isValid) {
      return; // 如果表单无效，则阻止进一步执行
    }

    if (!supplyModel.value.id) {
      ElMessage.error('供应记录ID丢失，无法更新');
      return;
    }
    const result = await updateSupplyService(supplyModel.value.id, supplyModel.value);
    if (result.code === 200) {
      ElMessage.success(result.data.tip || '供应记录信息更新成功');
      dialogVisible.value = false;
      fetchSupplyData();
    } else {
      ElMessage.error(result.data.tip || '更新供应记录信息失败');
    }
  } catch (error) {
    ElMessage.error('发生错误，请稍后重试');
  }
};

// 删除供应记录
const deleteSupply = async (id) => {
  try {
    const result = await deleteSupplyService(id);
    if (result.code === 200) {
      ElMessage.success(result.data.tip || '成功删除供应记录');
      fetchSupplyData();
    } else {
      ElMessage.error(result.data.tip || '删除供应记录失败');
    }
  } catch (error) {
    ElMessage.error('发生错误，请稍后重试');
  }
};

// 监视筛选器变化
watch([selectedMaterial, selectedSupplier], fetchSupplyData);

// 初始化加载数据
onMounted(() => {
  fetchSupplyData();
  fetchMaterialsAndSuppliersAndWarehouses();
});
</script>

<style scoped>
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.pagination-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  padding: 10px 0;
}

.filter-container {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}
</style>
