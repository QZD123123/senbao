//导入request.js请求工具
// import request from '@/utils/request.js'
import api from '@/api/api.js'

//提供调用注册接口的函数
export const userRegisterService = (registerData) => {
    //借助UrlSearchParams完成传递
    const params = new URLSearchParams()
    for (let key in registerData) {
        params.append(key, registerData[key]);
    }
    return request.post('/auth/register', params);
}

//提供调用登陆接口的函数
export const userLoginService = (loginData) => {
    const params = new URLSearchParams()
    for (let key in loginData) {
        params.append(key, loginData[key]);
    }
    return request.post('/auth/login', params);
}





//员工列表查询
export const WorkerListService = () => {
    return request.get('/user')
}

