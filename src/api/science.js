import request from '@/utils/request'

export const getScienceList = () => {
  return request({
    url: '/science/list',
    method: 'get'
  })
}

export const getScienceDetail = (id) => {
  return request({
    url: `/science/detail/${id}`,
    method: 'get'
  })
}

export const searchScience = (keyword) => {
  return request({
    url: '/science/search',
    method: 'get',
    params: { keyword }
  })
}
