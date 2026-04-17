import request from '@/utils/request'

export function getDashboard(query) {
  return request({
    url: '/xg/dashboard/overview',
    method: 'get',
    params: query
  })
}

