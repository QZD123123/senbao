<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>订单信息</span>
                <div class="extra">
                    <el-button type="primary" @click="openAddOrderDialog">添加订单</el-button>
                </div>
            </div>
        </template>
        <el-table :data="OrderData.orderList" style="width: 100%" stripe>
            <el-table-column fixed prop="id" label="订单编号" width="120" />
            <el-table-column prop="customerName" label="客户姓名" width="150" />
            <el-table-column prop="address" label="地址" width="150" />
            <el-table-column prop="phone" label="电话" width="120" />
            <el-table-column prop="totalPrice" label="总价" width="120" />
            <el-table-column prop="createTime" label="订单日期" width="150" />
            <el-table-column prop="progress" label="工作进度" width="150">
                <template #default="{ row }">
                    <el-tag :type="getProgressTagType(row.progress)" disable-transitions>
                        {{ getProgressText(row.progress) }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" min-width="150">
                <template #default="{ row }">
                    <el-button link type="primary" size="large" @click="openEditOrderDialog(row)">编辑</el-button>
                    <el-button link type="danger" size="large" @click="deleteOrder(row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 添加/编辑订单弹窗 -->
        <el-dialog v-model="dialogVisible" :title="isEditing ? '编辑订单' : '添加订单'" width="40%">
            <el-form :model="orderModel" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="客户姓名" prop="customerName">
                    <el-input v-model="orderModel.customerName" minlength="2" maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="安装地点" prop="address">
                    <el-input v-model="orderModel.address"></el-input>
                </el-form-item>
                <el-form-item label="电话" prop="phone">
                    <el-input v-model="orderModel.phone" type="number" min="1"></el-input>
                </el-form-item>
                <el-form-item label="总价" prop="totalPrice">
                    <el-input v-model="orderModel.totalPrice" type="number" min="0"></el-input>
                </el-form-item>
                <el-form-item label="订单日期" prop="createTime">
                    <el-date-picker v-model="orderModel.createTime" type="date" format="yyyy-MM-dd"></el-date-picker>
                </el-form-item>
                <el-form-item label="工作进度" prop="progress">
                    <el-select v-model="orderModel.progress" placeholder="选择进度">
                        <el-option
                            v-for="(status, index) in progressStatuses"
                            :key="index"
                            :label="status.text"
                            :value="status.value">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="isEditing ? updateOrder() : addOrder()">
                        {{ isEditing ? '更新' : '确认' }}
                    </el-button>
                </span>
            </template>
        </el-dialog>

        <!-- 分页控件 -->
        <div class="pagination-container">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="currentPage" :page-size="pageSize" :total="OrderData.orderTotal"
                layout="total, prev, pager, next, jumper">
            </el-pagination>
        </div>
    </el-card>
</template>
<script lang="ts" setup>
import { ElMessage } from 'element-plus';
import { ref, onMounted } from 'vue';
import { OrderListService, addOrderService, deleteOrderService, updateOrderService } from '@/stores/modules/order.js';

// 分页相关
const currentPage = ref(1);
const pageSize = ref(10);
const OrderData = ref({
    orderList: [],
    orderTotal: 0
});

// 弹窗相关
const dialogVisible = ref(false);
const isEditing = ref(false);
const orderModel = ref({
    id: '',
    customerName: '',
    address: '',
    phone: 0,
    totalPrice: 0,
    createTime: null,
    progress: '' // 工作进度
});

// 验证规则
const rules = {
    customerName: [
        { required: true, message: '请输入客户姓名', trigger: 'blur' },
        { min: 2, message: '姓名长度必须大于等于2个字符', trigger: 'blur' }
    ],
    address: [{ required: true, message: '请输入安装地点', trigger: 'blur' }],
    phone: [{ required: true, message: '请输入电话', trigger: 'blur' },{ pattern: /^1\d{10}$/, message: '电话必须是以1开头的11位数', trigger: 'blur' }],
    totalPrice: [{ required: true, message: '请输入总价', trigger: 'blur' }],
    createTime: [{ required: true, message: '请选择订单日期', trigger: 'change' }]
};

// 进度状态选项
const progressStatuses = [
    { value: '0', text: '测量中' },
    { value: '1', text: '测量完成' },
    { value: '2', text: '设计中' },
    { value: '3', text: '设计完成' },
    { value: '4', text: '生产中' },
    { value: '5', text: '生产完成' },
    { value: '6', text: '安装中' },
    { value: '7', text: '安装完成' },
];

// 获取进度标签类型
const getProgressTagType = (progress) => {
    switch (progress) {
        case 0:
        case 1:
            return 'info';
        case 2:
        case 3:
            return 'success';
        case 4:
        case 5:
            return 'warning';
        case 6:
        case 7:
            return 'danger';
        default:
            return 'default';
    }
};

// 获取进度文本
const getProgressText = (progress) => {
    const status = progressStatuses.find(item => item.value === String(progress)); // 将 progress 转换为字符串进行匹配
    return status ? status.text : '未知状态';
};

// 获取订单数据
const fetchOrderData = async (page = 1, pageSize = 10) => {
    try {
        let result = await OrderListService(page, pageSize);
        ElMessage.success(result.data.tip || '成功获取订单信息');
        OrderData.value = result.data;
    } catch (error) {
        ElMessage.error('获取订单信息失败');
    }
};

// 处理分页大小变化
const handleSizeChange = (size) => {
    pageSize.value = size;
    fetchOrderData(currentPage.value, size);
};

// 处理当前页变化
const handleCurrentChange = (page) => {
    currentPage.value = page;
    fetchOrderData(page, pageSize.value);
};

// 打开添加订单弹窗
const openAddOrderDialog = () => {
    isEditing.value = false;
    orderModel.value = {
        id: '',
        customerName: '',
        address: '',
        phone: 0,
        totalPrice: 0,
        createTime: null,
        progress: ''
    };
    dialogVisible.value = true;
};

// 打开编辑订单弹窗
const openEditOrderDialog = (row) => {
    isEditing.value = true;
    // 确保 progress 值是字符串
    orderModel.value = { ...row, progress: String(row.progress) }; 
    dialogVisible.value = true;
};

// 添加订单
const addOrder = async () => {
    try {
        let result = await addOrderService(orderModel.value);
        if (result.data.tip === '添加成功') {
            ElMessage.success(result.data.tip || '订单添加成功');
            dialogVisible.value = false;
            fetchOrderData(currentPage.value, pageSize.value);
        } else {
            ElMessage.error(result.data.tip || '添加订单失败');
        }
    } catch (error) {
        ElMessage.error('发生错误，请稍后重试');
    }
};

// 更新订单
const updateOrder = async () => {
    try {
        if (!orderModel.value.id) {
            ElMessage.error('订单ID丢失，无法更新');
            return;
        }
        let result = await updateOrderService(orderModel.value.id, orderModel.value);
        if (result.data.tip === '成功更新订单信息') {
            ElMessage.success(result.data.tip || '订单信息更新成功');
            dialogVisible.value = false;
            fetchOrderData(currentPage.value, pageSize.value);
        } else {
            ElMessage.error(result.data.tip || '更新订单信息失败');
        }
    } catch (error) {
        ElMessage.error('发生错误，请稍后重试');
    }
};

// 删除订单
const deleteOrder = async (id) => {
    try {
        let result = await deleteOrderService(id);
        if (result.data.tip === '成功删除订单') {
            ElMessage.success(result.data.tip || '订单删除成功');
            fetchOrderData(currentPage.value, pageSize.value);
        } else {
            ElMessage.error(result.data.message || '删除订单失败');
        }
    } catch (error) {
        ElMessage.error('发生错误，请稍后重试');
    }
};

// 初始化加载数据
onMounted(() => {
    fetchOrderData(currentPage.value, pageSize.value);
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
</style>
