<script setup>
import {
    Management,
    Promotion,
    UserFilled,
    User,
    Crop,
    EditPen,
    SwitchButton,
    CaretBottom
} from '@element-plus/icons-vue'
import avatar from '@/assets/default.png'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

</script>

<template>
    <el-container class="layout-container" style="background-color: #ecf5ff; align-self: start; ">
        <el-space direction="horizontal" alignment="stretch" :size="small">
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
                    <el-menu-item index="/inventory">
                        <el-icon>
                            <HomeFilled />
                        </el-icon>
                        <span>仓库</span>
                    </el-menu-item>
                    <el-menu-item index="/productionCosts">
                        <el-icon>
                            <BrushFilled />
                        </el-icon>
                        <span>生产消耗记录</span>
                    </el-menu-item>
                    <el-menu-item index="/supply">
                        <el-icon>
                            <ShoppingCartFull />
                        </el-icon>
                        <span>供应记录</span>
                    </el-menu-item>
                    <el-menu-item index="/supplier">
                        <el-icon>
                            <User />
                        </el-icon>
                        <span>供应商</span>
                    </el-menu-item>
                    <el-sub-menu>
                        <template #title>
                            <el-icon>
                                <UserFilled />
                            </el-icon>
                            <span>个人中心</span>
                        </template>
                        <el-menu-item index="/user/info">
                            <el-icon>
                                <User />
                            </el-icon>
                            <span>基本资料</span>
                        </el-menu-item>
                        <el-menu-item index="/user/avatar">
                            <el-icon>
                                <Refresh />
                            </el-icon>
                            <span>更换头像</span>
                        </el-menu-item>
                        <el-menu-item index="/user/password">
                            <el-icon>
                                <EditPen />
                            </el-icon>
                            <span>重置密码</span>
                        </el-menu-item>
                    </el-sub-menu>
                </el-menu>
            </el-aside>
            <!-- 右侧主区域 -->
            <el-container>
                <el-space direction="vertical" alignment="stretch" :size="small">
                    <!-- 头部区域 -->
                    <el-header >
                        <div style="">用户：卡卡酱 </div>
                        <!-- <div>牛马程序员：<strong>{{userInfoStore.info.nickname}}</strong></div> -->
                        <!-- command:条目被点击后会触发，在事件函数上可以声明一个参数，接收条目对应的指令 -->
                        <el-dropdown placement="bottom-end" @command="handleCommand">
                            <span class="el-dropdown__box">
                                <!-- <el-avatar :src="userInfoStore.info.userPic?userInfoStore.info.userPic:avatar" /> -->
                                <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
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
                <el-main>
                    <!-- <div style="width: 1290px; height: 570px;border: 1px solid red;">
                        内容展示区
                    </div> -->
                    <router-view></router-view>
                </el-main>
                <!-- 底部区域 -->
                <el-footer>家装管理系统 ©2024 Created by 宇宙第一帅</el-footer>
            </el-container>
        </el-space>
    </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
    height: 100vh;

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
        font-size: 14px;
        color: #666;
    }
}
</style>