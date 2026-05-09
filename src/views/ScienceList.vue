<template>
  <div class="science-page">
    <nav class="page-nav">
      <div class="nav-left">
        <button class="back-btn" @click="$router.back()">← 返回首页</button>
        <h2>健身科普 · 全部文章</h2>
      </div>
      <div class="nav-right">
        <div class="search-box">
          <input type="text" placeholder="搜索科普文章..." v-model="searchKeyword" />
          <button class="search-btn">🔍</button>
        </div>
      </div>
    </nav>

    <div class="science-list-container">
      <div
        class="science-card"
        v-for="item in filteredList"
        :key="item.id"
        @click="goToDetail(item.id)"
      >
        <div class="card-bg" :style="{ backgroundImage: `url(${item.imgUrl})` }"></div>
        <div class="card-content">
          <h3 class="card-title">{{ item.title }}</h3>
          <p class="card-desc">{{ item.desc }}</p>
          <div class="card-meta">
            <span class="meta-item">📚 {{ item.category }}</span>
            <span class="meta-item">⏱ {{ item.readTime }} 分钟阅读</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getScienceList, searchScience } from '@/api/science'
const router = useRouter()

const searchKeyword = ref('')
const scienceList = ref([])

const loadData = async () => {
  try {
    const res = await getScienceList()
    scienceList.value = res.data || []
  } catch (error) {
    console.error('加载数据失败:', error)
  }
}

const filteredList = computed(() => {
  if (!searchKeyword.value) return scienceList.value
  return scienceList.value.filter(item =>
    item.title.includes(searchKeyword.value) ||
    item.category.includes(searchKeyword.value)
  )
})

const goToDetail = (id) => {
  router.push(`/science/detail/${id}`)
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.science-page {
  width: 100%;
  min-height: 100vh;
  background: #f9fbfc;
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
  background: #f0f7f0;
  color: #6a994e;
  padding: 8px 16px;
  border-radius: 20px;
  cursor: pointer;
  transition: all 0.3s ease;
}
.back-btn:hover {
  background: #7fb069;
  color: #fff;
}
.page-nav h2 {
  font-size: 20px;
  color: #2c3e50;
}
.search-box {
  display: flex;
  align-items: center;
  border: 1px solid #e0e0e0;
  border-radius: 30px;
  overflow: hidden;
}
.search-box input {
  border: none;
  outline: none;
  padding: 8px 16px;
  width: 220px;
}
.search-btn {
  background: #7fb069;
  color: #fff;
  border: none;
  padding: 8px 12px;
  cursor: pointer;
}

.science-list-container {
  max-width: 1200px;
  margin: 40px auto;
  padding: 0 20px;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 24px;
}

.science-card {
  background: #fff;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(127,176,105,0.1);
  cursor: pointer;
  transition: all 0.3s ease;
}
.science-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(127,176,105,0.15);
}
.card-bg {
  width: 100%;
  height: 160px;
  background-size: cover;
  background-position: center;
}
.card-content {
  padding: 20px;
}
.card-title {
  font-size: 16px;
  color: #2d6a4f;
  margin-bottom: 8px;
}
.card-desc {
  font-size: 13px;
  color: #666;
  line-height: 1.5;
  margin-bottom: 12px;
}
.card-meta {
  display: flex;
  gap: 16px;
  font-size: 12px;
  color: #999;
}
</style>
