import {
    createRouter,
    createWebHistory
} from 'vue-router'

//导入组件
import LoginVue from '@/views/Login.vue'
import LayoutVue from '@/views/Layout.vue'

import HomeVue from '@/views/home/homeVue.vue'
import OrderVue from '@/views/order/orderVue.vue'
import InventoryVue from '@/views/inventory/inventoryVue.vue'
import ProductionCostsVue from '@/views/productionCosts/productionCostsVue.vue'
import SupplyVue from '@/views/supply/supplyVue.vue'
import SupplierVue from '@/views/supplier/supplierVue.vue'
import UserAvatarVue from '@/views/user/UserAvatar.vue'
import WorkerVue from '@/views/worker/workerVue.vue'


import testVue from '@/views/user/test.vue'

//定义路由关系
const routes = [{
    path: '/login',
    component: LoginVue
},
{
    path: '/',
    component: LayoutVue,
    redirect: '/home',
    //子路由
    children: [{
            path: '/home',
            component: HomeVue
        },
        {
            path: '/worker',
            component: WorkerVue
        },
        {
            path: '/order',
            component: OrderVue
        },
        {
            path: '/inventory',
            component: InventoryVue
        },
        {
            path: '/productionCosts',
            component: ProductionCostsVue
        },
        {
            path: '/supply',
            component: SupplyVue
        },
        {
            path: '/supplier',
            component: SupplierVue
        },
        {
            path: '/user/info',
            component: testVue
        },
        {
            path: '/user/avatar',
            component: UserAvatarVue
        },
        {
            path: '/user/resetPassword',
            component: testVue
        },
    ]
}
]

//创建路由器
const router = createRouter({
history: createWebHistory(),
routes: routes
})

//导出路由
export default router