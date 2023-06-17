// index.js
import Vue from 'vue'
import VueI18n from 'vue-i18n'
import Cookies from 'js-cookie'
import elementEnLocale from 'element-ui/lib/locale/lang/en' // element-ui lang
import elementZhLocale from 'element-ui/lib/locale/lang/zh-CN'// element-ui lang
import elementZhTwLocale from 'element-ui/lib/locale/lang/zh-TW'// element-ui lang



import enLocale from './en'
import zhLocale from './zh'
import zhTWLocale from './zh_tw'

Vue.use(VueI18n)

const messages = {
  en: {
    ...enLocale,
    ...elementEnLocale
  },
  zh: {
    ...zhLocale,
    ...elementZhLocale
  },
  zh_tw: {
    ...zhTWLocale,
    ...elementZhTwLocale
  }
}

const i18n = new VueI18n({
  // 设置语言 选项 en | zh
  locale: Cookies.get('language') || 'en',
  // 设置文本内容
  messages
})

export default i18n
