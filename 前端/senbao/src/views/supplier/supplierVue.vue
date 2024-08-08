<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>供应商信息</span>
        <div class="extra">
          <el-button type="primary" @click="openAddSupplierDialog">添加供应商</el-button>
        </div>
      </div>
    </template>

    <el-table v-if="supplierData.supplierList.length > 0" :data="supplierData.supplierList" style="width: 100%" stripe>
      <el-table-column prop="id" label="供应商编号" width="180" />
      <el-table-column prop="name" label="供应商名称" width="200" />
      <el-table-column prop="phone" label="联系电话" width="200" />
      <el-table-column prop="company" label="公司名称" width="200" />
      <el-table-column fixed="right" label="操作" min-width="165">
        <template #default="{ row }">
          <el-button type="primary" size="small" @click="openEditSupplierDialog(row)">编辑</el-button>
          <el-button type="danger" size="small" @click="deleteSupplier(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页控件 -->
    <div class="pagination-container">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                      :current-page="currentPage" :page-size="pageSize" :total="supplierData.supplierTotal"
                      layout="total, prev, pager, next, jumper">
      </el-pagination>
    </div>

    <!-- 添加/编辑供应商弹窗 -->
    <el-dialog :title="isEditing ? '编辑供应商' : '添加供应商'" v-model="dialogVisible" width="50%">
      <el-form :model="supplierModel" ref="form" :rules="rules" label-width="100px">
        <el-form-item label="供应商名称" prop="name">
          <el-input v-model="supplierModel.name"></el-input>
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="supplierModel.phone"></el-input>
        </el-form-item>
        <el-form-item label="公司名称" prop="company">
          <el-input v-model="supplierModel.company"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveSupplier">确定</el-button>
      </template>
    </el-dialog>
  </el-card>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { fetchSuppliers, AddSupplierService, EditSupplierService, DeleteSupplierService } from '@/stores/modules/supplier.js';

// 分页相关
const currentPage = ref(1);
const pageSize = ref(10);
const supplierData = ref({
  supplierList: [] as Array<{ id: number; name: string; phone: string; company: string }>,
  supplierTotal: 0
});

// 获取供应商数据
const fetchSupplierData = async () => {
    try {
        const result = await fetchSuppliers(currentPage.value, pageSize.value);
        // 确保数据结构符合预期
        if (result && result.supplierList) {
            supplierData.value = {
                supplierList: result.supplierList,
                supplierTotal: result.supplierTotal || 0
            };
            ElMessage.success(result.tip || '成功获取供应商信息');
        } else {
            throw new Error('数据结构不正确');
        }
    } catch (error) {
        console.error('获取供应商信息失败', error.message || error);
        ElMessage.error('获取供应商信息失败');
    }
};

// 处理分页大小变化
const handleSizeChange = (size: number) => {
    pageSize.value = size;
    fetchSupplierData();
};

// 处理当前页变化
const handleCurrentChange = (page: number) => {
    currentPage.value = page;
    fetchSupplierData();
};

// 初始化加载数据
onMounted(async () => {
    await fetchSupplierData();
});

// 弹窗相关
const dialogVisible = ref(false);
const isEditing = ref(false);
const supplierModel = ref({
    id: '',
    name: '',
    phone: '',
    company: ''
});

// 验证规则
const rules = {
    name: [{ required: true, message: '请输入供应商名称', trigger: 'blur' }],
    phone: [{ required: true, message: '请输入联系电话', trigger: 'blur' },{ pattern: /^1\d{10}$/, message: '手机号必须以1开头并且是11位数字', trigger: 'blur' }],
    company: [{ required: true, message: '请输入公司名称', trigger: 'blur' }],
};

// 打开添加供应商弹窗
const openAddSupplierDialog = () => {
    isEditing.value = false;
    supplierModel.value = {
        id: '',
        name: '',
        phone: '',
        company: ''
    };
    dialogVisible.value = true;
};

// 打开编辑供应商弹窗
const openEditSupplierDialog = (row) => {
    isEditing.value = true;
    supplierModel.value = { ...row };
    dialogVisible.value = true;
};

// 保存供应商信息
const saveSupplier = async () => {
    try {
        if (isEditing.value) {
            await EditSupplierService(supplierModel.value.id, supplierModel.value);
            ElMessage.success('编辑成功');
        } else {
            await AddSupplierService(supplierModel.value);
            ElMessage.success('添加成功');
        }
        dialogVisible.value = false;
        await fetchSupplierData();
    } catch (error) {
        console.error('操作失败', error.message || error);
        ElMessage.error('操作失败');
    }
};

// 删除供应商
const deleteSupplier = async (id: number) => {
    try {
        await DeleteSupplierService(id);
        ElMessage.success('删除成功');
        await fetchSupplierData();
    } catch (error) {
        console.error('删除失败', error.message || error);
        ElMessage.error('删除失败');
    }
};
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
    text-align: right;
}
</style>
