import request from '@/utils/request'


export function listUsedRecord(query) {
  return request({
    url: '/iotos/usedRecord/list',
    method: 'post',
    data: query
  })
}
