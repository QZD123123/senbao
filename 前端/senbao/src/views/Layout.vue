<script setup>
import {
    Management,
    User,
    Crop,
    EditPen,
    SwitchButton,
    CaretBottom
} from '@element-plus/icons-vue'
import { useTokenStore } from '@/stores/token.js'
import { useUserStore } from '@/stores/auth.js'
import { useRouter } from 'vue-router'
import { computed } from 'vue'

const router = useRouter();
const tokenStore = useTokenStore();
const userStore = useUserStore();

// 获取用户名，提供默认值以避免 undefined 错误
const username = computed(() => userStore.userInfo?.username || '默认用户');

// 获取用户头像
const avatarUrl = computed(() => userStore.userInfo?.avatar || 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png');

// 处理下拉菜单的命令
const handleCommand = (command) => {
    switch (command) {
        case 'info':
            router.push('/user/info');
            break;
        case 'avatar':
            router.push('/user/avatar');
            break;
        case 'resetPassword':
            router.push('/user/resetPassword');
            break;
        case 'logout':
            logout();
            break;
        default:
            break;
    }
};

// 退出登录函数
const logout = () => {
    // 清除存储的 token
    tokenStore.removeToken(); // 或者使用 tokenStore.setToken('') 
    userStore.logout(); // 更新用户状态
    // 跳转到登录页面
    router.push('/login');
};
</script>

<template>
    <el-container class="layout-container" style="background-color: #ecf5ff; align-self: start;">
        <!-- 左侧菜单 -->
        <el-aside width="200px">
            <div class="el-aside__logo"></div>
            <el-menu active-text-color="#ffd04b" background-color="#a093cf" text-color="#fff" router>
                <el-menu-item index="/home">
                    <el-icon>
                        <Management />
                    </el-icon>
                    <span>主页</span>
                </el-menu-item>
                <el-menu-item index="/worker">
                    <el-icon><MessageBox /></el-icon>
                    <span>员工</span>
                </el-menu-item>
                <el-menu-item index="/order">
                    <el-icon>
                        <List />
                    </el-icon>
                    <span>订单</span>
                </el-menu-item>
                <el-menu-item index="/warehouse">
                    <el-icon>
                        <HomeFilled />
                    </el-icon>
                    <span>仓库</span>
                </el-menu-item>
                <el-menu-item index="/inventory">
                    <el-icon>
                        <DataAnalysis />
                    </el-icon>
                    <span>库存</span>
                </el-menu-item>
                <el-menu-item index="/material">
                    <el-icon>
                        <SwitchFilled />
                    </el-icon>
                    <span>物料</span>
                </el-menu-item>
                <el-menu-item index="/productionCosts">
                    <el-icon>
                        <BrushFilled />
                    </el-icon>
                    <span>生产消耗记录</span>
                </el-menu-item>
                <el-menu-item index="/supplier">
                    <el-icon>
                        <User />
                    </el-icon>
                    <span>供应商</span>
                </el-menu-item>
                <el-menu-item index="/supply">
                    <el-icon>
                        <ShoppingCartFull />
                    </el-icon>
                    <span>供应记录</span>
                </el-menu-item>
            </el-menu>
        </el-aside>
        <!-- 右侧主区域 -->
        <el-container style="width: 100%; height: 100%; overflow-x: hidden; overflow-y: auto;">
            <el-space direction="vertical" alignment="stretch" :size="small">
                <!-- 头部区域 -->
                <el-header>
                    <div>管理员：{{ username }} </div>
                    <el-dropdown placement="bottom-end" @command="handleCommand">
                        <span class="el-dropdown__box">
                            <el-avatar :src="avatarUrl" />
                            <el-icon>
                                <CaretBottom />
                            </el-icon>
                        </span>
                        <template #dropdown>
                            <el-dropdown-menu>
                                <el-dropdown-item command="info" :icon="User">基本资料</el-dropdown-item>
                                <el-dropdown-item command="avatar" :icon="Crop">更换头像</el-dropdown-item>
                                <el-dropdown-item command="resetPassword" :icon="EditPen">重置密码</el-dropdown-item>
                                <el-dropdown-item command="logout" :icon="SwitchButton">退出登录</el-dropdown-item>
                            </el-dropdown-menu>
                        </template>
                    </el-dropdown>
                </el-header>
            </el-space>
            <!-- 中间区域 -->
            <el-main style="width: 100%; height: 100%;">
                <router-view></router-view>
            </el-main>
            <!-- 底部区域 -->
            <el-footer>家装管理系统 ©2024 Created by 宇宙第一帅</el-footer>
        </el-container>
    </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
    box-sizing: border-box;
    width: 100%;
    height: 100%;
    
    .el-aside {
        background-color: #e696b6;

        &__logo {
            height: 120px;
            background: url('@/assets/xiaoxiang.png') no-repeat center / 200px auto;
        }

        .el-menu {
            border-right: none;
        }
    }

    .el-header {
        background-color: #a093cf;
        display: flex;
        align-items: center;
        justify-content: space-between;

        .el-dropdown__box {
            display: flex;
            align-items: center;

            .el-icon {
                color: #00f900;
                margin-left: 10px;
            }

            &:active,
            &:focus {
                outline: none;
            }
        }
    }

    .el-footer {
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 20px;
        color: #666;
    }
}
</style>
