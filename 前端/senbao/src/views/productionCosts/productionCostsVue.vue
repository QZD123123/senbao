<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>原料成本记录</span>
        <div class="extra">
          <el-button type="primary" @click="openAddCostDialog">添加原料成本</el-button>
        </div>
      </div>
    </template>

    <!-- 筛选器 -->
    <div class="filter-container">
      <el-select v-model="selectedMaterial" placeholder="选择原料" @change="fetchProductionCosts" clearable>
        <el-option label="全部" value=""></el-option>
        <el-option
          v-for="(material, index) in materials"
          :key="index"
          :label="material.name"
          :value="material.id">
        </el-option>
      </el-select>

      <el-select v-model="selectedWarehouse" placeholder="选择仓库" @change="fetchProductionCosts" clearable>
        <el-option label="全部" value=""></el-option>
        <el-option
          v-for="(warehouse, index) in warehouses"
          :key="index"
          :label="warehouse.location"
          :value="warehouse.id">
        </el-option>
      </el-select>
    </div>

    <el-table :data="productionCostsData.consumptionList" style="width: 100%" stripe>
      <el-table-column fixed prop="id" label="记录编号" width="120" />
      <el-table-column prop="orderId" label="订单编号" width="150" />
      <el-table-column
        prop="materialId"
        label="原料名称"
        width="150">
        <template #default="{ row }">
          {{ getMaterialName(row.materialId) }}
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
      <el-table-column prop="createTime" label="时间" width="200">
        <template #default="{ row }">
          {{ formatDate(row.createTime) }}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" min-width="150">
        <template #default="{ row }">
          <el-button link type="primary" size="large" @click="openEditCostDialog(row)">编辑</el-button>
          <el-button link type="danger" size="large" @click="deleteCost(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加/编辑原料成本弹窗 -->
    <el-dialog v-model="dialogVisible" :title="isEditing ? '编辑原料成本' : '添加原料成本'" width="40%">
      <el-form :model="costModel" :rules="rules" label-width="100px" style="padding-right: 30px">
        <el-form-item label="订单编号" prop="orderId">
          <el-input v-model="costModel.orderId" type="number"></el-input>
        </el-form-item>
        <el-form-item label="原料" prop="materialId">
          <el-select v-model="costModel.materialId">
            <el-option
              v-for="(material, index) in materials"
              :key="index"
              :label="material.name"
              :value="material.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="仓库" prop="warehouseId">
          <el-select v-model="costModel.warehouseId">
            <el-option
              v-for="(warehouse, index) in warehouses"
              :key="index"
              :label="warehouse.location"
              :value="warehouse.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数量" prop="quantity">
          <el-input v-model="costModel.quantity" type="number" min="1"></el-input>
        </el-form-item>
        <el-form-item label="时间" prop="createTime">
          <el-date-picker
            v-model="costModel.createTime"
            type="datetime"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd'T'HH:mm:ss"
            placeholder="选择时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="isEditing ? updateCost() : addCost()">
            {{ isEditing ? '更新' : '确认' }}
          </el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 分页控件 -->
    <div class="pagination-container">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="currentPage" :page-size="pageSize" :total="productionCostsData.consumptionTotal"
        layout="total, prev, pager, next, jumper">
      </el-pagination>
    </div>
  </el-card>
</template>

<script lang="ts" setup>
import { ElMessage } from 'element-plus';
import { ref, onMounted } from 'vue';
import { 
  fetchProductionCostsService, 
  addProductionCostService, 
  deleteProductionCostService, 
  updateProductionCostService, 
  fetchMaterialsService, 
  fetchWarehousesService 
} from '@/stores/modules/productionCosts.js';

// 分页相关
const currentPage = ref(1);
const pageSize = ref(10);
const productionCostsData = ref({
  consumptionList: [],
  consumptionTotal: 0
});

// 筛选相关
const selectedMaterial = ref('');  // 当前选择的原料编号
const selectedWarehouse = ref('');  // 当前选择的仓库编号
const materials = ref([]); // 原料列表
const warehouses = ref([]); // 仓库列表

// 弹窗相关
const dialogVisible = ref(false);
const isEditing = ref(false);
const costModel = ref({
  id: '',
  orderId: '',
  materialId: '',
  warehouseId: '',
  quantity: 0,
  createTime: new Date().toISOString()
});

// 验证规则
const rules = {
  orderId: [{ required: true, message: '请输入订单编号', trigger: 'blur' }],
  materialId: [{ required: true, message: '请选择原料', trigger: 'change' }],
  warehouseId: [{ required: true, message: '请选择仓库', trigger: 'change' }],
  quantity: [{ required: true, message: '请输入数量', trigger: 'blur' }],
  createTime: [{ required: true, message: '请选择时间', trigger: 'change' }] // 验证时间
};

// 获取原料成本数据
const fetchProductionCosts = async () => {
  try {
    let result = await fetchProductionCostsService(currentPage.value, pageSize.value, selectedMaterial.value, selectedWarehouse.value);
    if (result.code === 200) {
      productionCostsData.value = {
        consumptionList: result.data.consumptionList.map(item => ({
          ...item,
          createTime: formatDate(item.createTime) // 确保日期格式正确
        })) || [],
        consumptionTotal: result.data.consumptionTotal || 0 
      };
      ElMessage.success(result.data.tip || '成功获取原料成本信息');
    } else {
      ElMessage.error(result.data.tip || '获取原料成本信息失败');
    }
  } catch (error) {
    ElMessage.error('获取原料成本信息失败');
    console.error(error); // 调试输出
  }
};

// 获取原料和仓库数据
const fetchMaterialsAndWarehouses = async () => {
  try {
    let materialsResult = await fetchMaterialsService();
    let warehousesResult = await fetchWarehousesService();
    if (materialsResult.code === 200 && warehousesResult.code === 200) {
      materials.value = materialsResult.data.list || [];
      warehouses.value = warehousesResult.data.list || [];
    } else {
      ElMessage.error('获取原料或仓库信息失败');
    }
  } catch (error) {
    ElMessage.error('获取原料或仓库信息失败');
    console.error(error); // 调试输出
  }
};

// 获取原料名称
const getMaterialName = (materialId) => {
  const material = materials.value.find(mat => mat.id === materialId);
  return material ? material.name : '未知原料';
};

// 获取仓库地址
const getWarehouseLocation = (warehouseId) => {
  const warehouse = warehouses.value.find(wh => wh.id === warehouseId);
  return warehouse ? warehouse.location : '未知地址';
};

// 格式化日期为 YYYY-MM-DD HH:mm 格式
const formatDate = (createTime) => {
  if (!createTime) return '';
  const date = new Date(createTime);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0'); // 月份从 0 开始，所以要加 1
  const day = String(date.getDate()).padStart(2, '0');
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');
  return `${year}-${month}-${day} ${hours}:${minutes}`;
};

// 打开添加对话框
const openAddCostDialog = () => {
  isEditing.value = false;
  costModel.value = {
    id: '',
    orderId: '',
    materialId: '',
    warehouseId: '',
    quantity: 0,
    createTime: new Date().toISOString() // 设置为当前时间的 ISO 8601 格式
  };
  dialogVisible.value = true;
};

// 打开编辑对话框
const openEditCostDialog = (row) => {
  isEditing.value = true;
  costModel.value = {
    ...row,
    createTime: new Date(row.createTime).toISOString() // 确保日期格式正确
  };
  dialogVisible.value = true;
};

// 添加原料成本
const addCost = async () => {
  try {
    // 确保 costModel.createTime 为 ISO 8601 格式
    costModel.value.createTime = new Date(costModel.value.createTime).toISOString();
    let result = await addProductionCostService(costModel.value);
    if (result.code === 200) {
      ElMessage.success(result.data.tip || '原料成本添加成功');
      dialogVisible.value = false;
      fetchProductionCosts();
    } else {
      ElMessage.error(result.data.tip || '添加原料成本失败');
    }
  } catch (error) {
    ElMessage.error('发生错误，请稍后重试');
    console.error(error); // 调试输出
  }
};

// 更新原料成本
const updateCost = async () => {
  try {
    if (!costModel.value.id) {
      ElMessage.error('成本ID丢失，无法更新');
      return;
    }
    // 确保 costModel.createTime 为 ISO 8601 格式
    costModel.value.createTime = new Date(costModel.value.createTime).toISOString();
    let result = await updateProductionCostService(costModel.value.id, costModel.value);
    if (result.code === 200) {
      ElMessage.success(result.data.tip || '原料成本信息更新成功');
      dialogVisible.value = false;
      fetchProductionCosts();
    } else {
      ElMessage.error(result.data.tip || '更新原料成本信息失败');
    }
  } catch (error) {
    ElMessage.error('发生错误，请稍后重试');
    console.error(error); // 调试输出
  }
};

// 删除原料成本
const deleteCost = async (id) => {
  try {
    let result = await deleteProductionCostService(id);
    if (result.code === 200) {
      ElMessage.success(result.data.tip || '原料成本删除成功');
      fetchProductionCosts();
    } else {
      ElMessage.error(result.data.tip || '删除原料成本失败');
    }
  } catch (error) {
    ElMessage.error('发生错误，请稍后重试');
    console.error(error); // 调试输出
  }
};

// 处理页码变化
const handleCurrentChange = (page) => {
  currentPage.value = page;
  fetchProductionCosts();
};

// 处理每页数量变化
const handleSizeChange = (size) => {
  pageSize.value = size;
  fetchProductionCosts();
};

// 初始化数据
onMounted(() => {
  fetchProductionCosts();
  fetchMaterialsAndWarehouses();
});
</script>

<style scoped>
.page-container {
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.extra {
  display: flex;
  align-items: center;
}

.filter-container {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
}

.pagination-container {
    display: flex;
    justify-content: center;
    margin-top: 20px;
}

.dialog-footer {
  text-align: right;
}
</style>
