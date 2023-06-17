import request from '@/utils/request'


export function listChanne(query) {
  return request({
    url: '/iotos/channel/list',
    method: 'post',
    data: query
  })
}

export function getChanne(map) {
  return request({
    url: '/iotos/channel/find',
    method: 'post',
    data: map
  })
}

export function saveChanne(map) {
  return request({
    url: '/iotos/channel/save' ,
    method: 'post',
    data: map
  })
}


export function editChanne(data) {
  return request({
    url: '/iotos/channel/edit',
    method: 'post',
    data: data
  })
}

export function delChanne(map) {
  return request({
    url: '/iotos/channel/delete',
    method: 'post',
    data: map
  })
}

export function getNameOpen(map) {
  return request({
    url: '/iotos/channel/getNameOpen',
    method: 'post',
    data: map
  })
}

export function getName(map) {
  return request({
    url: '/iotos/channel/getName',
    method: 'post',
    data: map
  })
}






