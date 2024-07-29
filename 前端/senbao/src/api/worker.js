//导入request.js请求工具
import request from '@/utils/request.js'

//员工列表查询
export const WorkerListService = () => {
    return request.get('/api/user')
}

