<template>
    <el-card class="page-container">
      <template #header>
        <div class="header">
          <span>物料信息</span>
          <div class="extra">
            <el-button type="primary" @click="openAddMaterialDialog">添加物料</el-button>
          </div>
        </div>
      </template>
  
      <!-- 筛选器 -->
      <div class="filter-container">
        <!-- 筛选物料类型 -->
        <el-select v-model="filter.selectedType" placeholder="选择物料类型" @change="fetchMaterials">
          <el-option label="全部" value=""></el-option>
          <el-option
            v-for="(type, index) in materialTypes"
            :key="index"
            :label="type"
            :value="type">
          </el-option>
        </el-select>
      </div>
  
      <el-table :data="materials" style="width: 100%" stripe>
        <el-table-column prop="id" label="物料编号" width="120" />
        <el-table-column prop="name" label="物料名称" width="150" />
        <el-table-column prop="type" label="物料类型" width="150" />
        <el-table-column prop="description" label="描述" />
        <el-table-column fixed="right" label="操作" min-width="150">
          <template #default="{ row }">
            <el-button link type="primary" size="small" @click="openEditMaterialDialog(row)">编辑</el-button>
            <el-button link type="danger" size="small" @click="deleteMaterial(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <!-- 添加/编辑物料弹窗 -->
      <el-dialog v-model="dialogVisible" :title="isEditing ? '编辑物料' : '添加物料'" width="40%">
        <el-form :model="materialModel" :rules="rules" label-width="100px" style="padding-right: 30px">
          <el-form-item label="物料名称" prop="name">
            <el-input v-model="materialModel.name"></el-input>
          </el-form-item>
          <el-form-item label="物料类型" prop="type">
            <el-select v-model="materialModel.type">
              <el-option
                v-for="(type, index) in materialTypes"
                :key="index"
                :label="type"
                :value="type">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="描述" prop="description">
            <el-input v-model="materialModel.description"></el-input>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="isEditing ? updateMaterial() : addMaterial()">
              {{ isEditing ? '更新' : '确认' }}
            </el-button>
          </span>
        </template>
      </el-dialog>
  
      <!-- 分页控件 -->
      <div class="pagination-container">
        <el-pagination 
          @size-change="handleSizeChange" 
          @current-change="handleCurrentChange"
          :current-page="currentPage" 
          :page-size="pageSize" 
          :total="totalCount"
          layout="total, prev, pager, next, jumper">
        </el-pagination>
      </div>
    </el-card>
  </template>
  
  <script lang="ts" setup>
  import { ElMessage } from 'element-plus';
  import { ref, onMounted, watch } from 'vue';
  import { MaterialListService, addMaterialService, deleteMaterialService, updateMaterialService, fetchMaterialTypesService } from '@/stores/modules/material.js';
  
  // 分页相关
  const currentPage = ref(1);
  const pageSize = ref(10);
  const totalCount = ref(0);
  const materials = ref([]);
  
  // 筛选相关
  const filter = ref({
    selectedType: ''
  });
  const materialTypes = ref([]);
  
  // 弹窗相关
  const dialogVisible = ref(false);
  const isEditing = ref(false);
  const materialModel = ref({
    id: '',
    name: '',
    type: '',
    description: ''
  });
  
  // 验证规则
  const rules = {
    name: [{ required: true, message: '请输入物料名称', trigger: 'blur' }],
    type: [{ required: true, message: '请选择物料类型', trigger: 'change' }],
    description: [{ required: false }]
  };
  
  // 获取物料列表
  const fetchMaterials = async () => {
    try {
      const result = await MaterialListService(currentPage.value, pageSize.value, '', filter.value.selectedType);
      if (result.code === 200) {
        ElMessage.success(result.data.tip || '成功获取物料信息');
        materials.value = result.data.materialList || [];
        totalCount.value = result.data.materialTotal || 0; // 更新总条数
      } else {
        ElMessage.error(result.message || '获取物料信息失败');
      }
    } catch (error) {
      ElMessage.error('获取物料信息失败');
    }
  };
  
  // 获取物料类型
  const fetchMaterialTypes = async () => {
    try {
      const result = await fetchMaterialTypesService();
      if (result.code === 200) {
        const allMaterials = result.data.list || [];
        const types = [...new Set(allMaterials.map(mat => mat.type))];
        materialTypes.value = types;
      } else {
        ElMessage.error(result.message || '获取物料类型失败');
      }
    } catch (error) {
      ElMessage.error('获取物料类型失败');
    }
  };
  
  // 处理分页大小变化
  const handleSizeChange = (size) => {
    pageSize.value = size;
    fetchMaterials(); // 重新加载物料数据
  };
  
  // 处理当前页变化
  const handleCurrentChange = (page) => {
    currentPage.value = page;
    fetchMaterials(); // 重新加载物料数据
  };
  
  // 打开添加物料弹窗
  const openAddMaterialDialog = () => {
    isEditing.value = false;
    materialModel.value = {
      id: '',
      name: '',
      type: '',
      description: ''
    };
    dialogVisible.value = true;
  };
  
  // 打开编辑物料弹窗
  const openEditMaterialDialog = (row) => {
    isEditing.value = true;
    materialModel.value = { ...row };
    dialogVisible.value = true;
  };
  
  // 添加物料
  const addMaterial = async () => {
    try {
      const result = await addMaterialService(materialModel.value);
      if (result.code === 200) {
        ElMessage.success(result.data.tip || '物料添加成功');
        dialogVisible.value = false;
        fetchMaterials();
      } else {
        ElMessage.error(result.message || '添加物料失败');
      }
    } catch (error) {
      ElMessage.error('发生错误，请稍后重试');
    }
  };
  
  // 更新物料
  const updateMaterial = async () => {
    try {
      if (!materialModel.value.id) {
        ElMessage.error('物料ID丢失，无法更新');
        return;
      }
      const result = await updateMaterialService(materialModel.value.id, materialModel.value);
      if (result.code === 200) {
        ElMessage.success(result.data.tip || '物料信息更新成功');
        dialogVisible.value = false;
        fetchMaterials();
      } else {
        ElMessage.error(result.message || '更新物料信息失败');
      }
    } catch (error) {
      ElMessage.error('发生错误，请稍后重试');
    }
  };
  
  // 删除物料
  const deleteMaterial = async (id) => {
    try {
      const result = await deleteMaterialService(id);
      if (result.code === 200) {
        ElMessage.success(result.data.tip || '成功删除物料');
        fetchMaterials();
      } else {
        ElMessage.error(result.message || '删除物料失败');
      }
    } catch (error) {
      ElMessage.error('发生错误，请稍后重试');
    }
  };
  
  // 初始化数据
  onMounted(() => {
    fetchMaterials();
    fetchMaterialTypes(); // 加载物料类型数据
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
  