import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'

const LgCode = 'LgCode'


export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function getLgCode(lgCode) {
  return Cookies.get(LgCode)
}

export function setLgCode(lgCode) {
  return Cookies.set(LgCode, lgCode)
}

export function removeLgCode() {
  return Cookies.remove(LgCode)
}
