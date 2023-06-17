import request from '@/utils/request'


export function listPerformTasks(query) {
  return request({
    url: '/iotos/performTasks/list',
    method: 'post',
    data: query
  })
}

export function findFile(map) {
  return request({
    url: '/iotos/performTasks/findFile',
    method: 'post',
    data: map
  })
}


export function downloadList(map) {
  return request({
    url: '/iotos/performTasks/downloadList',
    method: 'post',
    data: map
  })
}

export function tasksDetailsList(map) {
  return request({
    url: '/iotos/performTasks/tasksDetailsList',
    method: 'post',
    data: map
  })
}

export function tasksDetailsExport(map) {
  return request({
    url: '/iotos/performTasks/tasksDetailsExport',
    method: 'post',
    data: map
  })
}
