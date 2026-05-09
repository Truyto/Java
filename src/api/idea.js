import request from '@/utils/request'

export const getIdeaList = () => {
  return request({
    url: '/idea/list',
    method: 'get'
  })
}

export const addIdea = (data) => {
  return request({
    url: '/idea/add',
    method: 'post',
    data
  })
}
