import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
  state: () => ({
    isAuthenticated: false, // 用户的登录状态
    userInfo: {} // 存储用户的详细信息
  }),
  actions: {
    login(user) {
      this.isAuthenticated = true; // 设置为已认证
      this.userInfo = user; // 保存用户信息
    },
    logout() {
      this.isAuthenticated = false; // 设置为未认证
      this.userInfo = {}; // 清空用户信息
    }
  }
});
