<template>
  <el-card class="page-container">
      <template #header>
          <div class="header">
              <span>库存信息</span>
              <div class="extra">
                  <el-button type="primary" @click="openAddInventoryDialog">添加库存</el-button>
              </div>
          </div>
      </template>

      <!-- 筛选器 -->
      <div class="filter-container">
          <el-select v-model="selectedMaterial" placeholder="选择物料" @change="fetchInventoryData">
              <el-option label="全部" value=""></el-option>
              <el-option
                  v-for="(material, index) in materials"
                  :key="index"
                  :label="material.name"
                  :value="material.id">
              </el-option>
          </el-select>

          <el-select v-model="selectedWarehouse" placeholder="选择仓库" @change="fetchInventoryData">
              <el-option label="全部" value=""></el-option>
              <el-option
                  v-for="(warehouse, index) in warehouses"
                  :key="index"
                  :label="warehouse.location"
                  :value="warehouse.id">
              </el-option>
          </el-select>
      </div>

      <el-table :data="InventoryData.inventoryList" style="width: 100%" stripe>
          <el-table-column fixed prop="id" label="库存编号" width="120" />
          <el-table-column
              prop="materialId"
              label="物料名称"
              width="150">
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
              prop="warehouseId"
              label="仓库地址"
              width="150">
              <template #default="{ row }">
                  {{ getWarehouseLocation(row.warehouseId) }}
              </template>
          </el-table-column>
          <el-table-column prop="quantity" label="数量" width="120" />
          <el-table-column fixed="right" label="操作" min-width="150">
              <template #default="{ row }">
                  <el-button link type="primary" size="large" @click="openEditInventoryDialog(row)">编辑</el-button>
                  <el-button link type="danger" size="large" @click="deleteInventory(row.id)">删除</el-button>
              </template>
          </el-table-column>
      </el-table>

      <!-- 添加/编辑库存弹窗 -->
      <el-dialog v-model="dialogVisible" :title="isEditing ? '编辑库存' : '添加库存'" width="40%">
          <el-form :model="inventoryModel" :rules="rules" label-width="100px" style="padding-right: 30px">
              <el-form-item label="物料" prop="materialId">
                  <el-select v-model="inventoryModel.materialId">
                      <el-option
                          v-for="(material, index) in materials"
                          :key="index"
                          :label="material.name"
                          :value="material.id">
                      </el-option>
                  </el-select>
              </el-form-item>
              <el-form-item label="仓库" prop="warehouseId">
                  <el-select v-model="inventoryModel.warehouseId">
                      <el-option
                          v-for="(warehouse, index) in warehouses"
                          :key="index"
                          :label="warehouse.location"
                          :value="warehouse.id">
                      </el-option>
                  </el-select>
              </el-form-item>
              <el-form-item label="数量" prop="quantity">
                  <el-input v-model="inventoryModel.quantity" type="number" min="0"></el-input>
              </el-form-item>
          </el-form>
          <template #footer>
              <span class="dialog-footer">
                  <el-button @click="dialogVisible = false">取消</el-button>
                  <el-button type="primary" @click="isEditing ? updateInventory() : addInventory()">
                      {{ isEditing ? '更新' : '确认' }}
                  </el-button>
              </span>
          </template>
      </el-dialog>

      <!-- 分页控件 -->
      <div class="pagination-container">
          <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
              :current-page="currentPage" :page-size="pageSize" :total="InventoryData.inventoryTotal"
              layout="total, prev, pager, next, jumper">
          </el-pagination>
      </div>
  </el-card>
</template>

<script lang="ts" setup>
import { ElMessage } from 'element-plus';
import { ref, onMounted, watch } from 'vue';
import { InventoryListService, addInventoryService, deleteInventoryService, updateInventoryService, fetchMaterialsService, fetchWarehousesService } from '@/stores/modules/inventory.js';

// 分页相关
const currentPage = ref(1);
const pageSize = ref(10);
const InventoryData = ref({
  inventoryList: [],
  inventoryTotal: 0
});

// 筛选相关
const selectedMaterial = ref('');  // 当前选择的物料编号
const selectedWarehouse = ref('');  // 当前选择的仓库编号
const materials = ref([]); // 物料列表
const warehouses = ref([]); // 仓库列表

// 弹窗相关
const dialogVisible = ref(false);
const isEditing = ref(false);
const inventoryModel = ref({
  id: '',
  materialId: '',
  warehouseId: '',
  quantity: 0
});

// 验证规则
const rules = {
  materialId: [{ required: true, message: '请选择物料', trigger: 'change' }],
  warehouseId: [{ required: true, message: '请选择仓库', trigger: 'change' }],
  quantity: [{ required: true, message: '请输入数量', trigger: 'blur' }]
};

// 获取库存数据
const fetchInventoryData = async () => {
  try {
      let result = await InventoryListService(currentPage.value, pageSize.value, selectedMaterial.value, selectedWarehouse.value);
      if (result.code === 200) {
        ElMessage.success(result.data.tip || '成功获取库存信息');
        InventoryData.value = result.data;
      } else {
        ElMessage.error(result.message || '获取库存信息失败');
      }
  } catch (error) {
      ElMessage.error('获取库存信息失败');
  }
};

// 获取物料和仓库数据
const fetchMaterialsAndWarehouses = async () => {
  try {
      let materialsResult = await fetchMaterialsService();
      let warehousesResult = await fetchWarehousesService();
      if (materialsResult.code === 200 && warehousesResult.code === 200) {
        materials.value = materialsResult.data.list || [];
        warehouses.value = warehousesResult.data.list || [];
      } else {
        ElMessage.error('获取物料或仓库信息失败');
      }
  } catch (error) {
      ElMessage.error('获取物料或仓库信息失败');
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

// 获取仓库地址
const getWarehouseLocation = (warehouseId) => {
  const warehouse = warehouses.value.find(wh => wh.id === warehouseId);
  return warehouse ? warehouse.location : '未知地址';
};

// 处理分页大小变化
const handleSizeChange = (size) => {
  pageSize.value = size;
  fetchInventoryData();
};

// 处理当前页变化
const handleCurrentChange = (page) => {
  currentPage.value = page;
  fetchInventoryData();
};

// 打开添加库存弹窗
const openAddInventoryDialog = () => {
  isEditing.value = false;
  inventoryModel.value = {
      id: '',
      materialId: '',
      warehouseId: '',
      quantity: 0
  };
  dialogVisible.value = true;
};

// 打开编辑库存弹窗
const openEditInventoryDialog = (row) => {
  isEditing.value = true;
  inventoryModel.value = { ...row };
  dialogVisible.value = true;
};

// 添加库存
const addInventory = async () => {
  try {
      let result = await addInventoryService(inventoryModel.value);
      if (result.code === 200) {
          ElMessage.success(result.message || '库存添加成功');
          dialogVisible.value = false;
          fetchInventoryData();
      } else {
          ElMessage.error(result.message || '添加库存失败');
      }
  } catch (error) {
      ElMessage.error('发生错误，请稍后重试');
  }
};

// 更新库存
const updateInventory = async () => {
  try {
      if (!inventoryModel.value.id) {
          ElMessage.error('库存ID丢失，无法更新');
          return;
      }
      let result = await updateInventoryService(inventoryModel.value.id, inventoryModel.value);
      if (result.code === 200) {
          ElMessage.success(result.message || '库存信息更新成功');
          dialogVisible.value = false;
          fetchInventoryData();
      } else {
          ElMessage.error(result.message || '更新库存信息失败');
      }
  } catch (error) {
      ElMessage.error('发生错误，请稍后重试');
  }
};

// 删除库存
const deleteInventory = async (id) => {
  try {
      let result = await deleteInventoryService(id);
      if (result.code === 200) {
          ElMessage.success(result.message || '成功删除库存');
          fetchInventoryData();
      } else {
          ElMessage.error(result.message || '删除库存失败');
      }
  } catch (error) {
      ElMessage.error('发生错误，请稍后重试');
  }
};

// 监视筛选条件变化
watch([selectedMaterial, selectedWarehouse], fetchInventoryData);

// 初始化加载数据
onMounted(() => {
  fetchInventoryData();
  fetchMaterialsAndWarehouses(); // 加载物料和仓库数据
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
