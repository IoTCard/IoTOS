import request from '@/utils/request'


export function find(query) {
  return request({
    url: '/iotos/frontPage/find',
    method: 'post',
    data: query
  })
}


