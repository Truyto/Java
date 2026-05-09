<template>
  <div class="action-page">
    <nav class="page-nav">
      <div class="nav-left">
        <button class="back-btn" @click="$router.back()">← 返回首页</button>
        <h2>健身动作 · 全部教程</h2>
      </div>
      <div class="nav-right">
        <div class="filter-box">
          <select v-model="selectedPart">
            <option value="all">全部部位</option>
            <option value="胸部">胸部</option>
            <option value="背部">背部</option>
            <option value="腿部">腿部</option>
            <option value="肩部">肩部</option>
            <option value="核心">核心</option>
          </select>
        </div>
      </div>
    </nav>

    <div class="action-list-container">
      <div
        class="action-card"
        v-for="item in filteredList"
        :key="item.id"
        @click="goToDetail(item.id)"
      >
        <div class="card-bg" :style="{ backgroundImage: `url(${item.imgUrl})` }" @error="handleImageError($event)"></div>
        <div class="card-content">
          <h3 class="card-title">{{ item.name }}</h3>
          <p class="card-part">锻炼部位：{{ item.part }}</p>
          <div class="card-level">难度：{{ item.level }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import { getActionList, getActionByPart } from '@/api/action'
const router = useRouter()

const selectedPart = ref('all')
const actionList = ref([])

const loadData = async () => {
  try {
    let res
    if (selectedPart.value === 'all') {
      res = await getActionList()
    } else {
      res = await getActionByPart(selectedPart.value)
    }
    actionList.value = res.data || []
  } catch (error) {
    console.error('加载数据失败:', error)
  }
}

const filteredList = computed(() => {
  return actionList.value
})

const goToDetail = (id) => {
  router.push(`/action/detail/${id}`)
}

const handleImageError = (event) => {
  event.target.style.backgroundImage = 'url("/images/bg1.jpg")'
}

onMounted(() => {
  loadData()
})

watch(selectedPart, () => {
  loadData()
})
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.action-page {
  width: 100%;
  min-height: 100vh;
  background: #fdf2f8;
  padding-top: 70px;
}

.page-nav {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: 70px;
  background: #fff;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 40px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.05);
  z-index: 999;
}
.nav-left {
  display: flex;
  align-items: center;
  gap: 20px;
}
.back-btn {
  border: none;
  background: #fef7fb;
  color: #e56b6f;
  padding: 8px 16px;
  border-radius: 20px;
  cursor: pointer;
  transition: all 0.3s ease;
}
.back-btn:hover {
  background: #e56b6f;
  color: #fff;
}
.page-nav h2 {
  font-size: 20px;
  color: #2c3e50;
}
.filter-box select {
  border: 1px solid #e0e0e0;
  border-radius: 20px;
  padding: 8px 16px;
  outline: none;
  color: #e56b6f;
}

.action-list-container {
  max-width: 1200px;
  margin: 40px auto;
  padding: 0 20px;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 24px;
}

.action-card {
  background: #fff;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(229,107,111,0.1);
  cursor: pointer;
  transition: all 0.3s ease;
}
.action-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(229,107,111,0.15);
}
.card-bg {
  width: 100%;
  height: 140px;
  background-size: cover;
  background-position: center;
}
.card-content {
  padding: 16px;
  text-align: center;
}
.card-title {
  font-size: 16px;
  color: #e56b6f;
  margin-bottom: 6px;
}
.card-part {
  font-size: 13px;
  color: #666;
  margin-bottom: 8px;
}
.card-level {
  font-size: 12px;
  color: #fff;
  background: #e56b6f;
  padding: 3px 10px;
  border-radius: 12px;
  display: inline-block;
}
</style>
