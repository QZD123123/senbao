//定制请求的实例

//导入axios  npm install axios
import axios from 'axios';

import {
    ElMessage
} from 'element-plus'


//定义一个变量,记录公共的前缀  ,  baseURL
// const baseURL = 'http://localhost:3000';
const baseURL = '/api';
const instance = axios.create({
    baseURL
})

import {
    useTokenStore
} from '@/stores/token.js';
//添加请求拦截器
instance.interceptors.request.use(
    (config) => {

        // 不对特定路径进行 token 验证
        if (config.url === '/auth/login') {
            return config; // 直接返回，不添加 token
        }
        //请求前的回调
        //添加token
        const tokenStore = useTokenStore();
        //判断有无token
        if (tokenStore.token) {
            config.headers.Authorization = tokenStore.token
            console.log('Token:', config.headers.Authorization); // 输出 Token 到控制台
        }
        return config;
    },
    (err) => {
        //请求错误的回调
        return Promise.reject(err)
    }
)

import router from '@/router';

//添加响应拦截器
instance.interceptors.response.use(
    result => {
        //判断业务状态码
        if (result.data.code === 200) {
            return result.data;
        }

        //操作失败
        // alert(result.data.msg ? result.data.msg : '服务异常')
        ElMessage.error(result.data.data.tip ? result.data.data.tip : '服务异常1')
        //异步操作的状态转换为失败
        return Promise.reject(result.data)
    },
    err => {
        //判断响应状态码，如果为401，则证明未登录，提示请登录，并跳转到登录页面
        if(err.response.status===401){
            ElMessage.error('请先登录')
            router.push('/login')
        }else{
            ElMessage.error('服务异常2')
        }
        return Promise.reject(err); //异步的状态转化成失败的状态
    }
)

export default instance;