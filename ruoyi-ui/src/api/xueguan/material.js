import request from '@/utils/request'

export function listMaterial(query) {
  return request({
    url: '/xg/material/list',
    method: 'get',
    params: query
  })
}

export function getMaterial(materialId) {
  return request({
    url: '/xg/material/' + materialId,
    method: 'get'
  })
}

export function addMaterial(data) {
  return request({
    url: '/xg/material',
    method: 'post',
    data
  })
}

export function updateMaterial(data) {
  return request({
    url: '/xg/material',
    method: 'put',
    data
  })
}

export function delMaterial(materialId) {
  return request({
    url: '/xg/material/' + materialId,
    method: 'delete'
  })
}

export function auditMaterial(materialId, auditStatus, rejectReason) {
  return request({
    url: `/xg/material/${materialId}/audit`,
    method: 'put',
    params: { auditStatus, rejectReason }
  })
}

