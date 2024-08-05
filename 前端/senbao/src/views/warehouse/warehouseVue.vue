<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>仓库信息</span>
                <div class="extra">
                    <el-button type="primary" @click="openAddWarehouseDialog">添加仓库</el-button>
                </div>
            </div>
        </template>

        <el-table :data="WarehouseData.warehouseList" style="width: 100%" stripe>
            <el-table-column prop="id" label="仓库编号" width="200" />
            <el-table-column prop="location" label="仓库地址" width="200" />
            <el-table-column :label="'管理员姓名'" width="200">
                <template #default="{ row }">
                    <span>{{ getAdminName(row.managerId) }}</span>
                </template>
            </el-table-column>
            <el-table-column prop="phone" label="电话" width="200" />
            <el-table-column fixed="right" label="操作" min-width="165">
                <template #default="{ row }">
                    <el-button type="primary" size="small" @click="openEditWarehouseDialog(row)">编辑</el-button>
                    <el-button type="danger" size="small" @click="deleteWarehouse(row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 分页控件 -->
        <div class="pagination-container">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="currentPage" :page-size="pageSize" :total="WarehouseData.warehouseTotal"
                layout="total, prev, pager, next, jumper">
            </el-pagination>
        </div>

        <!-- 添加/编辑仓库弹窗 -->
        <el-dialog :title="isEditing ? '编辑仓库' : '添加仓库'" v-model="dialogVisible" width="30%">
            <el-form :model="warehouseModel" ref="form" :rules="rules" label-width="100px">
                <el-form-item label="仓库地址" prop="location">
                    <el-input v-model="warehouseModel.location"></el-input>
                </el-form-item>
                <el-form-item label="管理员姓名" prop="managerId">
                    <el-select v-model="warehouseModel.managerId" placeholder="请选择管理员">
                        <el-option
                            v-for="admin in adminOptions"
                            :key="admin.value"
                            :label="admin.label"
                            :value="admin.value">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <template #footer>
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="saveWarehouse">确定</el-button>
            </template>
        </el-dialog>
    </el-card>
</template>

<script lang="ts" setup>
import { ref, onMounted, computed } from 'vue';
import { ElMessage } from 'element-plus';
import { fetchAdmins, WarehouseListService, AddWarehouseService, EditWarehouseService, DeleteWarehouseService } from '@/stores/modules/warehouse.js';

// 分页相关
const currentPage = ref(1);
const pageSize = ref(10);
const WarehouseData = ref({
    warehouseList: [] as Array<{ id: number; location: string; managerId: string; phone: string }>,
    warehouseTotal: 0
});

// 管理员选项
const adminOptions = ref<{ value: string; label: string }[]>([]);

// 获取仓库数据
const fetchWarehouseData = async () => {
    try {
        const result = await WarehouseListService(currentPage.value, pageSize.value);
        if (result.code === 200) {
            ElMessage.success(result.data.tip || '成功获取仓库信息');
            WarehouseData.value = {
                warehouseList: result.data.warehouseList.map(warehouse => ({
                    ...warehouse,
                    managerId: adminOptions.value.find(admin => admin.label === warehouse.username)?.value || ''
                })),
                warehouseTotal: result.data.warehouseTotal || 0
            };
        } else {
            ElMessage.error(result.data.message || '获取仓库信息失败');
        }
    } catch (error) {
        console.error('获取仓库信息失败', error);
        ElMessage.error('获取仓库信息失败');
    }
};

// 处理分页大小变化
const handleSizeChange = (size: number) => {
    pageSize.value = size;
    fetchWarehouseData();
};

// 处理当前页变化
const handleCurrentChange = (page: number) => {
    currentPage.value = page;
    fetchWarehouseData();
};

// 初始化加载数据
onMounted(async () => {
    await loadAdmins();
    await fetchWarehouseData();
});

// 弹窗相关
const dialogVisible = ref(false);
const isEditing = ref(false);
const warehouseModel = ref({
    id: '',
    location: '',
    managerId: '',  // 确保与 el-select 的 value 属性一致
});

// 验证规则
const rules = {
    location: [{ required: true, message: '请输入仓库地址', trigger: 'blur' }],
    managerId: [{ required: true, message: '请选择管理员', trigger: 'change' }],
};

// 加载管理员数据
const loadAdmins = async () => {
    try {
        const admins = await fetchAdmins();
        adminOptions.value = admins.map(admin => ({
            value: String(admin.id),  // 确保 value 是字符串
            label: admin.username
        }));
        console.log('管理员选项:', adminOptions.value);
    } catch (error) {
        console.error('加载管理员信息失败', error);
    }
};

// 打开添加仓库弹窗
const openAddWarehouseDialog = () => {
    console.log('添加仓库按钮被点击');
    isEditing.value = false;
    warehouseModel.value = {
        id: '',
        location: '',
        managerId: '',  // 清空管理者 ID
    };
    dialogVisible.value = true;
};

// 打开编辑仓库弹窗
const openEditWarehouseDialog = (row) => {
    isEditing.value = true;
    warehouseModel.value = {
        id: row.id,
        location: row.location,
        managerId: adminOptions.value.find(admin => admin.label === row.username)?.value || '',  // 将 username 转换为 managerId
        phone: row.phone
    };
    console.log('编辑仓库数据:', warehouseModel.value);
    dialogVisible.value = true;
};

// 保存仓库信息
const saveWarehouse = async () => {
    try {
        if (isEditing.value) {
            await EditWarehouseService(warehouseModel.value.id, warehouseModel.value);
            ElMessage.success('编辑成功');
        } else {
            await AddWarehouseService(warehouseModel.value);
            ElMessage.success('添加成功');
        }
        dialogVisible.value = false;
        await fetchWarehouseData();  // 重新加载数据
    } catch (error) {
        console.error('操作失败', error);
        ElMessage.error('操作失败');
    }
};

// 删除仓库
const deleteWarehouse = async (id: number) => {
    try {
        await DeleteWarehouseService(id);
        ElMessage.success('删除成功');
        await fetchWarehouseData();  // 重新加载数据
    } catch (error) {
        console.error('删除失败', error);
        ElMessage.error('删除失败');
    }
};

// 根据 managerId 获取管理员名称
const getAdminName = (managerId) => {
    return adminOptions.value.find(admin => admin.value === managerId)?.label || '';
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
