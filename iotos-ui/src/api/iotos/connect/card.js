import request from '@/utils/request'


export function listCard(query) {
  return request({
    url: '/iotos/card/list',
    method: 'post',
    data: query
  })
}

export function getCard(map) {
  return request({
    url: '/iotos/card/find',
    method: 'post',
    data: map
  })
}

export function saveCard(map) {
  return request({
    url: '/iotos/card/save' ,
    method: 'post',
    data: map
  })
}


export function editCard(data) {
  return request({
    url: '/iotos/card/edit',
    method: 'post',
    data: data
  })
}




export function delCard(map) {
  return request({
    url: '/iotos/card/delete',
    method: 'post',
    data: map
  })
}


export function divideCard(map) {
  return request({
    url: '/iotos/card/divideCard',
    method: 'post',
    data: map
  })
}



export function rollbackDivid(map) {
  return request({
    url: '/iotos/card/rollbackDivid',
    method: 'post',
    data: map
  })
}


export function cardExport(map) {
  return request({
    url: '/iotos/card/export',
    method: 'post',
    data: map
  })
}


export function getGrouping(map) {
  return request({
    url: '/iotos/card/getGrouping',
    method: 'post',
    data: map
  })
}

export function synInfo(map) {
  return request({
    url: '/iotos/card/synInfo',
    method: 'post',
    data: map
  })
}

export function synFlow(map) {
  return request({
    url: '/iotos/card/synFlow',
    method: 'post',
    data: map
  })
}

export function synStatus(map) {
  return request({
    url: '/iotos/card/synStatus',
    method: 'post',
    data: map
  })
}

export function synRealName(map) {
  return request({
    url: '/iotos/card/synRealName',
    method: 'post',
    data: map
  })
}

export function synSession(map) {
  return request({
    url: '/iotos/card/synSession',
    method: 'post',
    data: map
  })
}

export function synActivateDate(map) {
  return request({
    url: '/iotos/card/synActivateDate',
    method: 'post',
    data: map
  })
}

export function synImei(map) {
  return request({
    url: '/iotos/card/synImei',
    method: 'post',
    data: map
  })
}

export function getApiBusinessList(map) {
  return request({
    url: '/iotos/card/getApiBusinessList',
    method: 'post',
    data: map
  })
}


export function editCardPublic(map) {
  return request({
    url: '/iotos/card/editCardPublic',
    method: 'post',
    data: map
  })
}


