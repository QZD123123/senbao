//导入request.js请求工具
import request from '@/utils/request.js'

// 提供调用注册接口的函数
export const userRegisterService = (registerData) => {
    //借助UrlSearchParams完成传递
    const params = new URLSearchParams()
    for (let key in registerData) {
        params.append(key, registerData[key]);
    }
    return request.post('/auth/register', params);
}

//提供调用登陆接口的函数
// export const userLoginService = (loginData) => {
//     const params = new URLSearchParams()
//     for (let key in loginData) {
//         params.append(key, loginData[key]);
//     }
//     return request.post('/auth/login', params);
// }

export const userLoginService = (loginData) => {
    return request.post('/auth/login', JSON.stringify(loginData), {
        headers: {
            'Content-Type': 'application/json'
        }
    });
}

// 员工列表查询，支持分页
export const WorkerListService = (page = 1, pageSize = 10) => {
    return request.get('/user', {
        params: {
            page,
            pageSize
        }
    });
}

// 添加员工
export const addWorker = (WorkerData) => {
    return request.post('/auth/register', JSON.stringify(WorkerData), {
        headers: {
            'Content-Type': 'application/json'
        }
    });
}

// 删除员工
export const deleteWorker = (id) => {
    return request.delete(`/user/${id}`);
};

// 编辑员工信息
// export const updateWorker = async (id, workerData) => {
//     console.log("进到方法内部了");
//     return request.patch(`/user/${id}`, workerData);
// };
export const updateWorker = async (id, workerData) => {
    console.log("进到方法内部了");
    try {
        const response = await request.patch(`/user/${id}`, workerData, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
        console.log("更新成功：", response);
        return response;
    } catch (error) {
        console.error("更新失败：", error);
        throw error;
    }
};
