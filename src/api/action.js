import request from '@/utils/request'

export const getActionList = () => {
  return request({
    url: '/action/list',
    method: 'get'
  })
}

export const getActionDetail = (id) => {
  return request({
    url: `/action/detail/${id}`,
    method: 'get'
  })
}

export const getActionByPart = (part) => {
  return request({
    url: '/action/part',
    method: 'get',
    params: { part }
  })
}
