<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>仓库信息</span>
            </div>
        </template>

        <el-table :data="WarehouseData.orderList" style="width: 100%" stripe>
            <el-table-column prop="id" label="仓库编号" width="120" />
            <el-table-column prop="location" label="仓库地址" width="150" />
            <el-table-column prop="username" label="管理员姓名" width="150" />
            <el-table-column prop="phone" label="电话" width="120" />
        </el-table>

        <!-- 分页控件 -->
        <div class="pagination-container">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="currentPage" :page-size="pageSize" :total="WarehouseData.orderTotal"
                layout="total, prev, pager, next, jumper">
            </el-pagination>
        </div>
    </el-card>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { WarehouseListService } from '@/stores/modules/warehouse.js';

// 分页相关
const currentPage = ref(1);
const pageSize = ref(10);
const WarehouseData = ref({
    orderList: [],  // 修改为 orderList
    orderTotal: 0   // 修改为 orderTotal
});

// 获取仓库数据
const fetchWarehouseData = async () => {
    try {
        let result = await WarehouseListService(currentPage.value, pageSize.value);
        ElMessage.success(result.data.tip || '成功获取仓库信息');
        WarehouseData.value = {
            orderList: result.data.orderList,  // 修改为 orderList
            orderTotal: result.data.orderTotal // 修改为 orderTotal
        };
    } catch (error) {
        console.error('获取仓库信息失败', error);  // 打印错误信息
        ElMessage.error('获取仓库信息失败');
    }
};

// 处理分页大小变化
const handleSizeChange = (size) => {
    pageSize.value = size;
    fetchWarehouseData();
};

// 处理当前页变化
const handleCurrentChange = (page) => {
    currentPage.value = page;
    fetchWarehouseData();
};

// 初始化加载数据
onMounted(() => {
    fetchWarehouseData();
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
</style>
