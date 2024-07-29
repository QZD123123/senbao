<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>员工信息</span>
                <div class="extra">
                    <el-button type="primary" @click="handleClick">添加分类</el-button>
                </div>
            </div>
        </template>
        <el-table :data="WorkerData" style="width: 100%" stripe>
            <el-table-column fixed prop="id" label="编号" width="120" />
            <el-table-column prop="username" label="姓名" width="120" />
            <el-table-column prop="phone" label="电话" width="120" />
            <el-table-column prop="role" label="职位" width="120" />
            <el-table-column prop="salary" label="薪水" width="120" />
            <el-table-column prop="address" label="地址" width="300" />
            <el-table-column prop="create_date" label="入职时间" width="120" />
            <el-table-column fixed="right" label="操作" min-width="120">
                <template #default>
                    <el-button link type="primary" size="large" @click="handleClick">编辑</el-button>
                    <el-button link type="primary" size="large">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 添加分类弹窗 -->
        <!-- <el-dialog v-model="dialogVisible" :title="title" width="30%">
            <el-form :model="categoryModel" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="分类名称" prop="categoryName">
                    <el-input v-model="categoryModel.categoryName" minlength="1" maxlength="10"></el-input>
                </el-form-item>
                <el-form-item label="分类别名" prop="categoryAlias">
                    <el-input v-model="categoryModel.categoryAlias" minlength="1" maxlength="15"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="title == '添加分类' ? addCategory() : updateCategory()"> 确认
                    </el-button>
                </span>
            </template>
        </el-dialog> -->

    </el-card>
</template>

<script lang="ts" setup>
const handleClick = () => {
    console.log('click')
}
import { ref } from 'vue'
const WorkerData = ref([
    {
        id : '12',
        username: 'Tom',
        phone: '12312312312',
        role: 'user',
        salary: '122',
        address: 'No. 189, Grove St, Los Angeles',
        create_date: '123123',
    },
    {
        id : '12',
        username: 'Tom',
        phone: '12312312312',
        role: 'user',
        salary: '122',
        address: 'No. 189, Grove St, Los Angeles',
        create_date: '123123',
    },
    {
        id : '12',
        username: 'Tom',
        phone: '12312312312',
        role: 'user',
        salary: '122',
        address: 'No. 189, Grove St, Los Angeles',
        create_date: '123123',
    },
    {
        id : '12',
        username: 'Tom',
        phone: '12312312312',
        role: 'user',
        salary: '122',
        address: 'No. 189, Grove St, Los Angeles',
        create_date: '123123',
    },
])

import {WorkerListService} from '@/api/worker.js'

const WorkerList = async () => {
    let result = await WorkerListService();
    WorkerData.value = result.data;
}
WorkerList();


</script>
<style scoped>
.header {
    display: flex;
    align-items: center;
    justify-content: space-between;
}
</style>