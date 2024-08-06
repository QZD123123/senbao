import { createRouter, createWebHistory } from 'vue-router';
import { useUserStore } from '@/stores/auth.js';

// 导入组件
import LoginVue from '@/views/Login.vue';
import LayoutVue from '@/views/Layout.vue';
import HomeVue from '@/views/home/homeVue.vue';
import OrderVue from '@/views/order/orderVue.vue';
import WarehouseVue from '@/views/warehouse/warehouseVue.vue'
import InventoryVue from '@/views/inventory/inventoryVue.vue'; 
import MaterialVue from '@/views/material/materialVue.vue'
import ProductionCostsVue from '@/views/productionCosts/productionCostsVue.vue';
import SupplyVue from '@/views/supply/supplyVue.vue';
import SupplierVue from '@/views/supplier/supplierVue.vue';
import WorkerVue from '@/views/worker/workerVue.vue';

// 定义路由关系
const routes = [
  {
    path: '/login',
    component: LoginVue,
  },
  {
    path: '/',
    component: LayoutVue,
    redirect: '/home',
    children: [
      { path: '/home', component: HomeVue },
      { path: '/worker', component: WorkerVue },
      { path: '/order', component: OrderVue },
      { path: '/warehouse', component: WarehouseVue },
      { path: '/inventory', component: InventoryVue },
      { path: '/material', component: MaterialVue },
      { path: '/productionCosts', component: ProductionCostsVue },
      { path: '/supplier', component: SupplierVue },
      { path: '/supply', component: SupplyVue },
    ],
  },
];

// 创建路由器
const router = createRouter({
  history: createWebHistory(),
  routes,
});

// 导航守卫
router.beforeEach((to, from, next) => {
  const userStore = useUserStore();
  
  if (to.path !== '/login' && !userStore.isAuthenticated) {
    // 如果用户未登录且访问的不是登录页面，则重定向到登录页面
    next('/login');
  } else {
    // 否则，允许访问
    next();
  }
});

export default router;
