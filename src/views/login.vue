<template>
  <div class="auth-page">
    <div class="auth-card">
      <div class="auth-head">
        <h2>{{ isLoginMode ? '快速登录' : '注册账号' }}</h2>
        <p>{{ isLoginMode ? '欢迎回到健身交流社区' : '创建你的健身账号' }}</p>
      </div>

      <div class="auth-form">
        <div class="input-group">
          <span>账号</span>
          <input v-model="username" placeholder="请输入用户名" />
        </div>

        <div class="input-group">
          <span>密码</span>
          <input v-model="password" type="password" placeholder="请输入密码" />
        </div>

        <div v-if="!isLoginMode" class="input-group">
          <span>确认密码</span>
          <input v-model="confirmPassword" type="password" placeholder="请再次输入密码" />
        </div>

        <div v-if="!isLoginMode" class="input-group">
          <span>为什么要健身？（必填）</span>
          <textarea v-model="fitReason" rows="3" placeholder="输入你的健身初衷"></textarea>
          <div class="tip">该回答会随机投放在想法球中</div>
        </div>

        <button class="auth-btn" :class="{ disabled: !canSubmit }" @click="onSubmit">
          {{ isLoginMode ? '登录' : '注册' }}
        </button>
      </div>

      <div class="auth-switch" @click="isLoginMode = !isLoginMode">
        {{ isLoginMode ? '没有账号？立即注册' : '已有账号？立即登录' }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { login, register } from '@/api/user'
const router = useRouter()

const isLoginMode = ref(true)
const username = ref('')
const password = ref('')
const confirmPassword = ref('')
const fitReason = ref('')

const canSubmit = computed(() => {
  if (isLoginMode.value) {
    return username.value.trim() !== '' && password.value.trim() !== ''
  }
  return (
    username.value.trim() !== '' &&
    password.value.trim() !== '' &&
    confirmPassword.value.trim() !== '' &&
    fitReason.value.trim() !== ''
  )
})

const loginSuccess = (data) => {
  localStorage.setItem('token', data.token)
  localStorage.setItem('isLogin', 'true')
  localStorage.setItem('username', username.value)
  // 保存完整用户信息
  localStorage.setItem('user_' + username.value, JSON.stringify({
    motto: data.motto || '自律给我自由',
    fitReason: data.fitReason || '未设置',
    level: data.level || 1,
    exp: data.exp || 0
  }))
  router.push('/')
}

const registerSuccess = () => {
  alert('注册成功！')
  isLoginMode.value = true
}

const onSubmit = async () => {
  if (!canSubmit.value) {
    alert('请完善所有必填项！')
    return
  }

  const name = username.value.trim()
  const pwd = password.value.trim()

  if (name.length < 2) {
    alert('用户名至少 2 个字符')
    return
  }
  if (pwd.length < 6) {
    alert('密码至少 6 位')
    password.value = ''
    return
  }
  const illegal = /[^\u4e00-\u9fa5a-zA-Z0-9_]/
  if (illegal.test(name)) {
    alert('用户名不能包含特殊字符')
    username.value = ''
    password.value = ''
    return
  }

  try {
    if (isLoginMode.value) {
      const res = await login({ username: name, password: pwd })
      loginSuccess(res.data)
    } else {
      if (password.value !== confirmPassword.value) {
        alert('两次密码不一致')
        confirmPassword.value = ''
        return
      }
      await register({ username: name, password: pwd, fitReason: fitReason.value })
      registerSuccess()
    }
  } catch (error) {
    const errorMsg = error.response?.data?.message || '操作失败，请稍后重试'
    alert(errorMsg)
    // 如果是登录模式，清除用户名和密码
    if (isLoginMode.value) {
      username.value = ''
      password.value = ''
    }
  }
}
</script>

<style>
html,body{margin:0;}
</style>

<style scoped>
* {margin:0;padding:0;box-sizing:border-box}
.auth-page {width:100%;min-height:100vh;background:#080808;display:flex;align-items:center;justify-content:center;padding:20px}
.auth-card {width:100%;max-width:420px;background:#111;border-radius:20px;padding:44px 36px;border:1px solid #2a2a2a;box-shadow:0 8px 30px rgba(0,0,0,0.4)}
.auth-head {text-align:center;margin-bottom:36px}
.auth-head h2 {font-size:26px;color:#f1f1f1;margin-bottom:8px}
.auth-head p {font-size:14px;color:#888}
.auth-form {display:flex;flex-direction:column;gap:20px;margin-bottom:28px}
.input-group {display:flex;flex-direction:column;gap:8px}
.input-group span {font-size:13px;color:#ccc}
.input-group input, .input-group textarea {height:48px;background:#181818;border:1px solid #383838;border-radius:12px;padding:0 16px;color:#fff;font-size:15px;outline:none}
.input-group textarea {height:100px;padding-top:12px;resize:none}
.tip {font-size:12px;color:#888;margin-top:4px}
.auth-btn {height:50px;border-radius:12px;background:#fff;color:#111;font-size:15px;font-weight:500;border:none;cursor:pointer}
.auth-btn.disabled {background:#222;color:#777;cursor:not-allowed}
.auth-switch {text-align:center;font-size:13px;color:#777;cursor:pointer}
.auth-switch:hover {color:#fff}
</style>
