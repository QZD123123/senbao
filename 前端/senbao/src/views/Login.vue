<script setup>
import { User, Lock } from '@element-plus/icons-vue';
import { ref } from 'vue';
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';
import { useTokenStore } from '@/stores/token.js';
import { useUserStore } from '@/stores/auth.js';
import { userLoginService } from '@/stores/modules/worker.js';

// 定义数据模型
const loginData = ref({
  phone: '12312312312',
  password: '123123'
});

// 登录函数
const router = useRouter();
const tokenStore = useTokenStore();
const userStore = useUserStore();

// 表单校验规则
const rules = {
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { min: 11, max: 11, message: '请输入11位数的手机号', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 5, max: 16, message: '请输入5-16个字符', trigger: 'blur' }
  ]
};

const login = async () => {
  try {
    const result = await userLoginService(loginData.value);
    ElMessage.success(result.data.tip || '登录成功');
    tokenStore.setToken(result.data.token); // 存储 token
    userStore.login(result.data.user); // 保存用户信息
    router.push('/'); // 跳转到首页
  } catch (error) {
    ElMessage.error('登录失败');
  }
};

// 清空数据模型的数据
const clearLoginData = () => {
  loginData.value = {
    phone: '',
    password: ''
  };
};
</script>

<template>
    <el-row class="login-page">
      <el-col :span="12" class="bg"></el-col>
      <el-col :span="6" :offset="3" class="form">
        <!-- 登录表单 -->
        <el-form ref="form" size="large" autocomplete="off" :model="loginData" :rules="rules">
          <el-form-item>
            <h1>登录</h1>
          </el-form-item>
          <el-form-item prop="phone">
            <el-input :prefix-icon="User" placeholder="请输入手机号" v-model="loginData.phone"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input :prefix-icon="Lock" type="password" placeholder="请输入密码" v-model="loginData.password"></el-input>
          </el-form-item>
          <!-- 登录按钮 -->
          <el-form-item>
            <el-button class="button" type="primary" @click="login">登录</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </template>

<style lang="scss" scoped>
/* 样式 */
.login-page {
  height: 100vh;
  background-color: #fff;

  .bg {
    background: 
      url('@/assets/xiang.png') no-repeat center / cover;
    border-radius: 0 20px 20px 0;
  }

  .form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    user-select: none;

    .title {
      margin: 0 auto;
    }

    .button {
      width: 100%;
    }
  }
}
</style>
