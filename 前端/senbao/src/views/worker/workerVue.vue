<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>员工信息</span>
                <div class="extra">
                    <el-button type="primary" @click="openAddEmployeeDialog">添加员工</el-button>
                </div>
            </div>
        </template>

        <!-- 职位筛选器 -->
        <div class="filter-container">
            <el-select v-model="selectedRole" placeholder="请选择职位" @change="filterByRole">
                <el-option label="全部" value=""></el-option>
                <el-option label="管理员" value="admin"></el-option>
                <el-option label="会计" value="accountant"></el-option>
                <el-option label="设计师" value="designer"></el-option>
                <el-option label="木工" value="carpenter"></el-option>
                <el-option label="油漆工" value="painter"></el-option>
                <el-option label="五金工" value="ironman"></el-option>
                <el-option label="杂工" value="user"></el-option>
            </el-select>
        </div>
        
        <el-table :data="WorkerData.userList" style="width: 100%" stripe>
            <el-table-column fixed prop="id" label="编号" width="120" />
            <el-table-column prop="username" label="姓名" width="120" />
            <el-table-column prop="phone" label="电话" width="120" />
            <el-table-column prop="role" label="职位" width="120" :formatter="roleFormatter" />
            <el-table-column prop="salary" label="薪水" width="120" />
            <el-table-column prop="address" label="地址" width="300" />
            <el-table-column prop="joinedDate" label="入职时间" width="300">
                <template #default="{ row }">
                    {{ formatDate(row.joinedDate) }}
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" min-width="120">
                <template #default="{ row }">
                    <el-button link type="primary" size="large" @click="openEditEmployeeDialog(row)">编辑</el-button>
                    <el-button link type="danger" size="large" @click="deleteEmployee(row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 添加员工弹窗 -->
        <el-dialog v-model="dialogVisible" :title="isEditing ? '编辑员工' : '添加员工'" width="30%">
            <el-form :model="employeeModel" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="姓名" prop="username">
                    <el-input v-model="employeeModel.username" minlength="2" maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="电话" prop="phone">
                    <el-input v-model="employeeModel.phone" minlength="11" maxlength="11"
                        placeholder="请输入11位手机号码"></el-input>
                </el-form-item>
                <el-form-item label="职位" prop="role">
                    <el-radio-group v-model="employeeModel.role">
                        <el-radio value="admin">管理员</el-radio>
                        <el-radio value="accountant">会计</el-radio>
                        <el-radio value="designer">设计师</el-radio>
                        <el-radio value="carpenter">木工</el-radio>
                        <el-radio value="painter">油漆工</el-radio>
                        <el-radio value="ironman">五金工</el-radio>
                        <el-radio value="user">杂工</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="薪水" prop="salary">
                    <el-input v-model="employeeModel.salary" type="number" min="0"></el-input>
                </el-form-item>
                <el-form-item label="地址" prop="address">
                    <el-input v-model="employeeModel.address"></el-input>
                </el-form-item>
                <el-form-item label="入职时间" prop="joinedDate">
                    <el-date-picker v-model="employeeModel.joinedDate" type="date" format="yyyy-MM-dd" placeholder="选择入职时间"></el-date-picker>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="isEditing ? updateEmployee() : addEmployee()">
                        {{ isEditing ? '更新' : '确认' }}
                    </el-button>
                </span>
            </template>
        </el-dialog>

        <!-- 分页控件 -->
        <div class="pagination-container">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="currentPage" :page-size="pageSize" :total="WorkerData.userTotal"
                layout="total, prev, pager, next, jumper">
            </el-pagination>
        </div>
    </el-card>
</template>

<script lang="ts" setup>
import { ElMessage } from 'element-plus';
import { ref, onMounted } from 'vue';
import { WorkerListService, addWorker, deleteWorker, updateWorker } from '@/stores/modules/worker.js';

const currentPage = ref(1);
const pageSize = ref(10);
const WorkerData = ref({
    userList: [],
    userTotal: 0
});

const dialogVisible = ref(false);
const employeeModel = ref({
    id: '',
    username: '',
    phone: '',
    role: '',
    salary: 0,
    address: '翻斗花园',  // 默认值
    joinedDate: null
});

const rules = {
    username: [
        { required: true, message: '请输入姓名', trigger: 'blur' },
        { min: 2, message: '姓名长度必须大于等于2个字符', trigger: 'blur' }
    ],
    phone: [
        { required: true, message: '请输入电话', trigger: 'blur' },
        { pattern: /^1\d{10}$/, message: '电话格式不正确，应为11位手机号码且以1开头', trigger: 'blur' }
    ],
    role: [{ required: true, message: '请选择职位', trigger: 'change' }],
    salary: [{ required: true, message: '请输入薪水', trigger: 'blur' }],
    address: [{ required: true, message: '请输入地址', trigger: 'blur' }],
    joinedDate: [{ required: true, message: '请选择入职时间', trigger: 'change' }]
};

const fetchWorkerData = async (page = 1, pageSize = 10, role = '') => {
    let result = await WorkerListService(page, pageSize, role);  // 传递 role 参数
    ElMessage.success(result.data.tip || '成功获取员工信息');
    WorkerData.value = result.data;
};

const handleSizeChange = (size) => {
    pageSize.value = size;
    fetchWorkerData(currentPage.value, size, selectedRole.value);  // 传递 selectedRole
};

const handleCurrentChange = (page) => {
    currentPage.value = page;
    fetchWorkerData(page, pageSize.value, selectedRole.value);  // 传递 selectedRole
};

const selectedRole = ref('');  // 用于存储选中的职位

const filterByRole = (role) => {
    selectedRole.value = role;
    fetchWorkerData(currentPage.value, pageSize.value, role);  // 根据选中的职位重新获取数据
};

const isEditing = ref(false);

const openAddEmployeeDialog = () => {
    isEditing.value = false;
    employeeModel.value = {
        id: '',
        username: '',
        phone: '',
        role: '',
        salary: 0,
        address: '翻斗花园',  // 默认值
        joinedDate: null
    };
    dialogVisible.value = true;
};

const openEditEmployeeDialog = (row) => {
    isEditing.value = true;
    employeeModel.value = { ...row };
    dialogVisible.value = true;
};

const addEmployee = async () => {
    try {
        let result = await addWorker(employeeModel.value);
        if (result.data.tip === '员工添加成功') {
            ElMessage.success(result.data.tip || '员工添加成功');
            dialogVisible.value = false;
            fetchWorkerData(currentPage.value, pageSize.value);
        } else {
            ElMessage.error(result.data.tip || '添加员工失败');
        }
    } catch (error) {
        ElMessage.error('发生错误，请稍后重试');
    }
};

const updateEmployee = async () => {
    try {
        if (!employeeModel.value.id) {
            ElMessage.error('员工ID丢失，无法更新');
            return;
        }
        let result = await updateWorker(employeeModel.value.id, employeeModel.value);
        if (result.data.tip === '成功更新用户信息') {
            ElMessage.success(result.data.tip || '员工信息更新成功');
            dialogVisible.value = false;
            fetchWorkerData(currentPage.value, pageSize.value);
        } else {
            ElMessage.error(result.data.tip || '更新员工信息失败');
        }
    } catch (error) {
        ElMessage.error('发生错误，请稍后重试');
    }
};

const deleteEmployee = async (id) => {
    try {
        let result = await deleteWorker(id);
        if (result.data.tip === '成功删除用户') {
            ElMessage.success(result.data.tip || '员工删除成功');
            fetchWorkerData(currentPage.value, pageSize.value);
        } else {
            ElMessage.error(result.data.message || '删除员工失败');
        }
    } catch (error) {
        ElMessage.error('发生错误，请稍后重试');
    }
};

onMounted(() => {
    fetchWorkerData(currentPage.value, pageSize.value);
});

const formatDate = (date) => {
  if (!date) return '';
  const d = new Date(date);
  return d.toISOString().split('T')[0]; // 只取日期部分 (YYYY-MM-DD)
};

const editEmployee = (row) => {
    employeeModel.value = { ...row };
    dialogVisible.value = true;
};

const roleFormatter = (row, column, cellValue, index) => {
    const roleMap = {
        'admin': '管理员',
        'accountant': '会计',
        'designer': '设计师',
        'carpenter': '木工',
        'painter': '油漆工',
        'ironman': '五金工',
        'user': '杂工',
    };
    return roleMap[cellValue] || cellValue;
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
</style>
