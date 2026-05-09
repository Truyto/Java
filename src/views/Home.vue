<template>
	<div class="fitness-home">
		<!-- 1. 顶部导航栏（清新版） -->
		<nav class="navbar" :class="{ dark: isDark, scroll: isScroll }">
			<div class="nav-left">
				<div class="logo">💪 健身科普</div>
				<ul class="nav-menu">
					<li class="active">首页</li>
					<li class="dropdown">
						<span>科普 ▾</span>
						<div class="dropdown-menu">
							<div>健身知识</div>
							<div>饮食指南</div>
							<div>减脂原理</div>
							<div>增肌科普</div>
						</div>
					</li>
					<li class="dropdown">
						<span>动作 ▾</span>
						<div class="dropdown-menu">
							<div>胸部训练</div>
							<div>背部训练</div>
							<div>腿部训练</div>
							<div>肩部训练</div>
						</div>
					</li>
					<li>交流</li>
					<li>我的</li>
				</ul>
			</div>
			<div class="nav-right">
				<div class="search-box">
					<input type="text" placeholder="搜索健身知识、动作教程..." />
					<button class="search-btn">🔍</button>
				</div>
				<div class="theme-toggle" @click="toggleTheme">
					{{ isDark ? '☀️' : '💡' }}
				</div>
				<div class="avatar" @click="toggleUserCard">
					{{ isLogin ? '🧑' : '👤' }}
				</div>
				<div class="user-card" :class="{show: showUserCard}">
					<div class="card-item">
						<label>用户名</label>
						<div>{{ userInfo.username }}</div>
					</div>
					<div class="card-item">
						<label>健身格言</label>
						<div>{{ userInfo.motto }}</div>
					</div>
					<div class="card-item">
						<label>健身初衷</label>
						<div>{{ userInfo.fitReason }}</div>
					</div>
					<button class="logout-btn" @click="handleLogout">退出登录</button>
				</div>
				<div class="login-btn" @click="isLogin ? handleLogout() : goToLogin()">
					{{ isLogin ? '注销' : '登录' }}
				</div>
				<div class="user-level" v-if="isLogin">Lv.{{ userInfo.level }} {{ getLevelTitle(userInfo.level) }}</div>
			</div>
		</nav>

		<!-- 顶部固定 Banner -->
		<div class="top-banner" id="home">
			<img src="/public/images/definemoment.png" alt="健身封面" class="banner-img" />
		</div>

		<!-- 轮播图 -->
		<div class="swiper-container">
			<div class="swiper-wrapper" :style="{ transform: `translateX(-${currentIndex * 100}%)` }">
				<div class="swiper-slide"><img src="/public/images/bg1.jpg" alt="轮播1" /></div>
				<div class="swiper-slide"><img src="/public/images/bg1.jpg" alt="轮播2" /></div>
				<div class="swiper-slide"><img src="/public/images/bg1.jpg" alt="轮播3" /></div>
			</div>
			<div class="swiper-dots">
				<span class="dot" :class="{ active: currentIndex === 0 }"></span>
				<span class="dot" :class="{ active: currentIndex === 1 }"></span>
				<span class="dot" :class="{ active: currentIndex === 2 }"></span>
			</div>
		</div>

		<!-- 想法球模块 -->
		<div class="idea-ball-container">
			<canvas id="ideaCanvas"></canvas>
			<button v-if="isFullscreen" class="back-btn" @click="handleBack">返回</button>
		</div>

		<!-- 科普栏和动作栏 -->
		<div class="content-container">
			<div class="section" id="science">
				<div class="section-title">科普</div>
				<div class="card-list science-list">
					<div
					  class="card science-card"
					  v-for="item in scienceList"
					  :key="item.id"
					  @click="toScienceDetail(item.id)"
					>
					  <div class="card-bg" :style="{ backgroundImage: `url(${item.imgUrl})` }" @error="handleImageError($event)"></div>
					  <div class="card-content">
					    <p class="card-text">{{ item.title }}</p>
					  </div>
					</div>
					<router-link to="/sciencelistvue" class="more-btn">查看更多</router-link>
				</div>
			</div>
			<div class="section" id="action">
				<div class="section-title">动作</div>
				<div class="card-list action-list">
					<div
					  class="card action-card"
					  v-for="item in actionList"
					  :key="item.id"
					  @click="toActionDetail(item.id)"
					>
					  <div class="card-bg" :style="{ backgroundImage: `url(${item.imgUrl})` }" @error="handleImageError($event)"></div>
					  <div class="card-content">
					    <p class="card-text">{{ item.name }}</p>
					  </div>
					</div>
					<router-link to="/actionlistvue" class="more-btn">查看更多</router-link>
				</div>
			</div>
		</div>

		<!-- 左侧页面结构面板 -->
		<div class="page-structure-panel" :class="{ expanded: isExpanded }" @click="togglePanel">
			<div class="panel-trigger">
				<div class="trigger-arrow"></div>
			</div>
			<div class="panel-content" v-show="isExpanded">
				<div class="panel-title">页面结构</div>
				<div class="panel-list">
					<div class="panel-item" :class="{ active: currentNav === 'home' }" @click.stop="scrollToNav('home')">首页</div>
					<div class="panel-item" :class="{ active: currentNav === 'science' }" @click.stop="scrollToNav('science')">科普</div>
					<div class="panel-item" :class="{ active: currentNav === 'action' }" @click.stop="scrollToNav('action')">动作</div>
					<div class="panel-item" :class="{ active: currentNav === 'communicate' }" @click.stop="scrollToNav('communicate')">交流</div>
					<div class="panel-item" :class="{ active: currentNav === 'mine' }" @click.stop="scrollToNav('mine')">我的</div>
				</div>
			</div>
		</div>

		<!-- 回到顶部按钮 -->
		<div class="back-to-top" v-show="scrollPercent > 5" @click="goToTop">
			<svg class="progress-circle" viewBox="0 0 100 100">
				<defs>
					<linearGradient id="progress-gradient" x1="0%" y1="0%" x2="100%" y2="0%">
						<stop offset="0%" stop-color="#4ecdc4" />
						<stop offset="100%" stop-color="#2b7dfd" />
					</linearGradient>
				</defs>
				<circle cx="50" cy="50" r="45" fill="none" stroke="#333" stroke-width="4" />
				<circle class="progress-ring" cx="50" cy="50" r="45" fill="none" stroke="url(#progress-gradient)" stroke-width="4" stroke-linecap="round"
					:stroke-dasharray="283" :stroke-dashoffset="283 - (scrollPercent / 100) * 283" />
			</svg>
			<div class="arrow-icon">
				<svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
					<path d="M12 19V5M5 12l7-7 7 7" />
				</svg>
			</div>
		</div>

		<!-- 健身明星区 -->
		<div class="fitness-carousel-wrapper">
			<h3 class="fitness-carousel-title">健身明星区</h3>
			<div class="fitness-carousel-container">
				<button class="fitness-carousel-btn fitness-prev" id="fitnessPrevBtn">
					<svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="#fff" stroke-width="2">
						<path d="M15 18l-6-6 6-6" />
					</svg>
				</button>
				<div class="fitness-carousel-track" id="fitnessCarouselTrack">
					<div class="fitness-carousel-item" data-index="0">
						<img src="/public/images/bg1.jpg" alt="健身明星">
					</div>
					<div class="fitness-carousel-item" data-index="1">
						<img src="/public/images/definemoment.png" alt="健身明星">
					</div>
					<div class="fitness-carousel-item" data-index="2">
						<img src="/public/images/dragon.png" alt="健身明星">
					</div>
					<div class="fitness-carousel-item" data-index="3">
						<img src="/public/images/now.png" alt="健身明星">
					</div>
				</div>
				<button class="fitness-carousel-btn fitness-next" id="fitnessNextBtn">
					<svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="#fff" stroke-width="2">
						<path d="M9 18l6-6-6-6" />
					</svg>
				</button>
			</div>
		</div>

		<!-- 健身交流区 -->
		<section class="home-comm" id="communicate">
			<div class="comm-head">
				<h3>健身交流</h3>
				<p>分享经验 · 交流心得 · 共同进步</p>
			</div>
			<div class="comm-list">
				<div class="comm-item">
					<div class="user-avatar">健</div>
					<div class="user-content">
						<h4>新手求问：卧推总是不稳怎么办？</h4>
						<p>来自 健身爱好者</p>
					</div>
				</div>
				<div class="comm-item">
					<div class="user-avatar">身</div>
					<div class="user-content">
						<h4>坚持跑步30天，体态变化真的大</h4>
						<p>来自 减脂达人</p>
					</div>
				</div>
				<div class="comm-item">
					<div class="user-avatar">达</div>
					<div class="user-content">
						<h4>肩部训练三个王牌动作分享</h4>
						<p>来自 塑形教练</p>
					</div>
				</div>
			</div>
			<div class="comm-more">进入完整交流区</div>
		</section>

		<!-- 页脚 -->
		<footer class="site-footer" id="mine">
			<div class="footer-container">
				<div class="footer-section footer-quote">
					<p class="quote-text">“努力不是为了感动谁，而是为了不辜负自己”</p>
					<p class="quote-text">“趁着年轻，跌倒了还能爬起来，多吸取教训，学会重新站起来的方法也是很重要的。”</p>
					<p class="quote-text">“如果说我懂的道理比别人多一点，那是因为我犯的错误比别人多一点。”</p>
					<p class="quote-text">“同伴，不一定非要一起走到最后，某一段路上，对方给自己带来的朗朗笑声，那就已经足够。”</p>
				</div>
				<div class="footer-section footer-info">
					<div class="qrcode-wrapper">
						<div class="qrcode-box">
							<img src="/public/images/qrcode.jpg" alt="联系二维码" class="qrcode-img">
						</div>
					</div>
					<div class="info-list">
						<div class="info-item"><span class="info-label">Author:Truyto</span></div>
						<div class="info-item"><span class="info-label">Emai:2140262156@qq.com</span></div>
						<div class="info-item"><span class="info-label">Hobbes:修东西,散步</span></div>
						<div class="social-icons">
							<a href="#" class="social-icon">
								<svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
									<path d="M12 2C6.477 2 2 6.477 2 12c0 4.991 3.657 9.128 8.438 9.879V14.89h-2.54V12h2.54V9.797c0-2.506 1.492-3.891 3.777-3.891 1.094 0 2.238.195 2.238.195v2.46h-1.26c-1.243 0-1.63.771-1.63 1.562V12h2.773l-.443 2.89h-2.33v6.989C18.343 21.129 22 16.99 22 12c0-5.523-4.477-10-10-10z" />
								</svg>
							</a>
							<a href="#" class="social-icon">
								<svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
									<path d="M20.447 20.452h-3.554v-5.569c0-1.328-.027-3.037-1.852-3.037-1.853 0-2.136 1.445-2.136 2.939v5.667H9.351V9h3.414v1.561h.046c.477-.9 1.637-1.85 3.37-1.85 3.601 0 4.267 2.37 4.267 5.455v6.286zM5.337 7.433c-1.144 0-2.063-.926-2.063-2.065 0-1.138.92-2.063 2.063-2.063 1.14 0 2.064.925 2.064 2.063 0 1.139-.925 2.065-2.064 2.065zm1.782 13.019H3.555V9h3.564v11.452zM22.225 0H1.771C.792 0 0 .774 0 1.729v20.542C0 23.227.792 24 1.771 24h20.451C23.2 24 24 23.227 24 22.271V1.729C24 .774 23.2 0 22.222 0h.003z" />
								</svg>
							</a>
							<a href="#" class="social-icon">
								<svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
									<path d="M12 0C5.373 0 0 5.373 0 12s5.373 12 12 12 12-5.373 12-12S18.627 0 12 0zm5.894 8.221l-1.97 9.28c-.145.658-.537.818-1.084.508l-3-2.21-1.446 1.394c-.14.18-.357.295-.6.295-.002 0-.003 0-.005 0l.213-3.054 5.56-5.023c.242-.213-.054-.334-.373-.121l-6.869 4.326-2.96-.924c-.64-.203-.658-.64.135-.954l11.566-4.458c.538-.196 1.006.128.832.94z" />
								</svg>
							</a>
						</div>
					</div>
				</div>
				<div class="footer-section footer-nav">
					<h4 class="nav-title">快速导航</h4>
					<ul class="nav-list">
						<li class="nav-item"><a href="#" class="nav-link">首页</a></li>
						<li class="nav-item"><a href="#" class="nav-link">科普栏</a></li>
						<li class="nav-item"><a href="#" class="nav-link">动作栏</a></li>
						<li class="nav-item"><a href="#" class="nav-link">健身明星区</a></li>
						<li class="nav-item"><a href="#" class="nav-link">我的</a></li>
					</ul>
				</div>
			</div>
			<div class="footer-bottom">
				<p class="copyright-text">© 2026 GymIntroductionNet | All Rights Reserved</p>
			</div>
		</footer>
	</div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { getScienceList } from '@/api/science'
import { getActionList } from '@/api/action'
import { getFitReasons } from '@/api/user'

const router = useRouter()

// 状态管理
const isLogin = ref(false)
const isDark = ref(false)
const isScroll = ref(false)
const currentIndex = ref(0)
const isExpanded = ref(false)
const currentNav = ref('home')
const scrollPercent = ref(0)
const showUserCard = ref(false)
const userInfo = reactive({
	username: '',
	motto: '',
	fitReason: '',
	level: 1,
	exp: 0
})

const getLevelTitle = (level) => {
	const titles = [
		'健身小白',      // 1
		'健身新手',      // 2
		'健身爱好者',    // 3
		'健身达人',      // 4
		'健身教练',      // 5
		'金牌教练',      // 6
		'健身宗师',      // 7
		'健身传奇',      // 8
		'健身之神',      // 9
		'宇宙最强'       // 10+
	]
	const index = Math.min(level - 1, titles.length - 1)
	return titles[index]
}

// API数据
const scienceList = ref([])
const actionList = ref([])

// 想法球相关
const canvas = ref(null)
let ctx = null
let balls = []
let animationId = null
const isFullscreen = ref(false)
let fullscreenBall = null
let fitReasons = ref([])

// 定时器
let timer = null
let fitReasonsTimer = null

// 默认健身初衷（当数据库中没有足够数据时使用）
const defaultFitReasons = [
	"想瘦下来穿好看的衣服", "想变得更自信", "为了身体健康",
	"喜欢运动后的成就感", "想练出好看的线条", "为了不被欺负",
	"想拥有更好的精神状态", "喜欢流汗的感觉", "为了陪伴家人更久",
	"想挑战自己", "想变强壮保护爱的人", "为了摆脱emo情绪",
	"想体验自律的快乐", "想让生活更有掌控感", "想和过去的自己告别"
]

const ballColors = ['#FF6B6B', '#4ECDC4', '#45B7D1', '#96CEB4', '#FFEAA7', '#DDA0DD', '#98D8C8', '#F7DC6F']

// 加载数据
const loadData = async () => {
	try {
		const [scienceRes, actionRes] = await Promise.all([
			getScienceList(),
			getActionList()
		])
		scienceList.value = (scienceRes.data || []).slice(0, 4)
		actionList.value = (actionRes.data || []).slice(0, 4)
	} catch (error) {
		console.error('加载数据失败:', error)
	}
}

// 加载健身初衷数据
const loadFitReasons = async () => {
	try {
		const res = await getFitReasons(15)
		fitReasons.value = res.data || []
		// 如果数据库中没有足够的数据，使用默认数据
		if (fitReasons.value.length === 0) {
			fitReasons.value = [...defaultFitReasons]
		}
		// 更新想法球
		if (canvas.value && ctx) {
			initBalls()
		}
	} catch (error) {
		console.error('加载健身初衷失败:', error)
		fitReasons.value = [...defaultFitReasons]
	}
}

// 导航相关
const goToLogin = () => router.push('/login')

const toScienceDetail = (id) => router.push('/science/detail/' + id)

const toActionDetail = (id) => router.push('/action/detail/' + id)

const handleImageError = (event) => {
  event.target.style.backgroundImage = 'url("/images/bg1.jpg")'
}

const goToTop = () => window.scrollTo({ top: 0, behavior: 'smooth' })

const togglePanel = () => {
	isExpanded.value = !isExpanded.value
}

const scrollToNav = (navKey) => {
	const section = document.getElementById(navKey)
	if (section) {
		section.scrollIntoView({ behavior: 'smooth' })
	}
}

const checkActiveNav = () => {
	const navKeys = ['home', 'science', 'action', 'communicate', 'mine']
	for (let i = navKeys.length - 1; i >= 0; i--) {
		const sectionEl = document.getElementById(navKeys[i])
		if (!sectionEl) continue
		const rect = sectionEl.getBoundingClientRect()
		// 当板块顶部进入视口下方100px时，认为该板块处于活跃状态
		if (rect.top < window.innerHeight - 100 && rect.bottom > 100) {
			currentNav.value = navKeys[i]
			break
		}
	}
}

const updateScrollProgress = () => {
	const scrollTop = document.documentElement.scrollTop || document.body.scrollTop
	const scrollHeight = document.documentElement.scrollHeight - window.innerHeight
	scrollPercent.value = scrollHeight > 0 ? (scrollTop / scrollHeight) * 100 : 0
}

const debounce = (fn, delay = 100) => {
	let timer = null
	return () => {
		clearTimeout(timer)
		timer = setTimeout(fn, delay)
	}
}

// 主题切换
const toggleTheme = () => {
	isDark.value = !isDark.value
	document.documentElement.classList.toggle('dark-mode')
	localStorage.setItem('isDark', isDark.value ? 'true' : 'false')
}

// 登录状态管理
const handleLogout = () => {
	isLogin.value = false
	showUserCard.value = false
	localStorage.removeItem('isLogin')
	localStorage.removeItem('username')
	localStorage.removeItem('token')
}

const toggleUserCard = () => {
	if (!isLogin.value) {
		router.push('/login')
		return
	}
	showUserCard.value = !showUserCard.value
}

// 轮播图
const startAutoPlay = () => {
	timer = setInterval(() => {
		currentIndex.value = (currentIndex.value + 1) % 3
	}, 3000)
}

// 想法球实现
class Ball {
	constructor(x, y, text, color, isOwn = false) {
		this.originX = x
		this.originY = y
		this.x = x
		this.y = y
		this.radius = 30
		this.vx = (Math.random() - 0.5) * 1.5
		this.vy = (Math.random() - 0.5) * 2
		this.text = text
		this.color = color
		this.scale = 1
		this.isHover = false
		this.maxScale = 1.25
		this.fullScale = 1
		this.isOwn = isOwn
	}

	draw(ctx) {
		if (isFullscreen.value && fullscreenBall === this) {
			const cx = canvas.value.width / 2
			const cy = canvas.value.height / 2
			const maxR = Math.hypot(cx, cy)
			ctx.save()
			ctx.beginPath()
			ctx.arc(cx, cy, maxR * this.fullScale, 0, Math.PI * 2)
			ctx.fillStyle = this.color
			ctx.fill()
			ctx.restore()
			ctx.fillStyle = '#fff'
			ctx.font = 'bold 24px sans-serif'
			ctx.textAlign = 'center'
			ctx.textBaseline = 'middle'
			ctx.fillText(this.text, cx, cy)
			return
		}
		if (isFullscreen.value) return

		ctx.save()
		if (this.isHover) {
			ctx.shadowColor = this.color
			ctx.shadowBlur = 45
		} else if (this.isOwn) {
			// 用户自己的想法球高亮显示
			ctx.shadowColor = '#FFD700'
			ctx.shadowBlur = 30
		} else {
			ctx.shadowColor = 'rgba(0,0,0,0.1)'
			ctx.shadowBlur = 6
		}
		ctx.beginPath()
		ctx.arc(this.x, this.y, this.radius * this.scale, 0, Math.PI * 2)
		ctx.fillStyle = this.color
		ctx.fill()
		
		// 如果是用户自己的想法球，添加金色边框
		if (this.isOwn) {
			ctx.strokeStyle = '#FFD700'
			ctx.lineWidth = 3
			ctx.stroke()
		}
		ctx.restore()

		if (this.isHover) {
			ctx.fillStyle = '#fff'
			ctx.font = '13px sans-serif'
			ctx.textAlign = 'center'
			ctx.textBaseline = 'middle'
			const t = this.text.length > 8 ? this.text.slice(0, 8) + '…' : this.text
			ctx.fillText(t, this.x, this.y)
		}
	}

	update(canvas) {
		if (!isFullscreen.value && this.fullScale > 0) {
			this.fullScale += (0 - this.fullScale) * 0.09
		}
		if (isFullscreen.value && fullscreenBall === this) {
			this.fullScale += (1 - this.fullScale) * 0.08
			return
		}

		if (this.isHover) {
			this.scale += (this.maxScale - this.scale) * 0.12
			return
		}

		this.scale += (1 - this.scale) * 0.12
		this.x += this.vx
		this.y += this.vy

		const r = this.radius * this.scale
		if (this.x - r <= 0) { this.vx = Math.abs(this.vx) * 0.9; this.x = r + 2 }
		if (this.x + r >= canvas.width) { this.vx = -Math.abs(this.vx) * 0.9; this.x = canvas.width - r - 2 }
		if (this.y - r <= 0) { this.vy = Math.abs(this.vy) * 0.9; this.y = r + 2 }
		if (this.y + r >= canvas.height) { this.vy = -Math.abs(this.vy) * 0.9; this.y = canvas.height - r - 2 }
	}

	isPointInBall(px, py) {
		const dis = Math.hypot(px - this.x, py - this.y)
		return dis < this.radius * this.scale
	}
}

const initCanvas = () => {
	canvas.value = document.getElementById('ideaCanvas')
	ctx = canvas.value.getContext('2d')
	canvas.value.width = canvas.value.parentElement.clientWidth
	canvas.value.height = 400
	initBalls()
}

const initBalls = () => {
	// 使用健身初衷数据，如果没有则使用默认数据
	const texts = fitReasons.value.length > 0 ? fitReasons.value : defaultFitReasons
	// 获取当前用户的健身初衷
	const userFitReason = isLogin.value ? userInfo.fitReason : ''
	balls = texts.map((text, i) => new Ball(
		Math.random() * canvas.value.width,
		Math.random() * canvas.value.height,
		text,
		ballColors[i % ballColors.length],
		// 如果当前文本等于用户的健身初衷，标记为用户自己的球
		isLogin.value && userFitReason && text === userFitReason
	))
}

const animate = () => {
	ctx.clearRect(0, 0, canvas.value.width, canvas.value.height)
	if (!isFullscreen.value) {
		for (let i = 0; i < balls.length; i++) {
			for (let j = i + 1; j < balls.length; j++) {
				const dx = balls[i].x - balls[j].x
				const dy = balls[i].y - balls[j].y
				const distance = Math.sqrt(dx * dx + dy * dy)
				if (distance < 150) {
					ctx.beginPath()
					ctx.moveTo(balls[i].x, balls[i].y)
					ctx.lineTo(balls[j].x, balls[j].y)
					ctx.strokeStyle = `rgba(255, 255, 255, ${0.3 - (distance / 150) * 0.3})`
					ctx.lineWidth = 1
					ctx.stroke()
				}
			}
		}
	}
	balls.forEach(ball => {
		ball.update(canvas.value)
		ball.draw(ctx)
	})
	animationId = requestAnimationFrame(animate)
}

const handleMouseMove = (e) => {
	if (isFullscreen.value) return
	const rect = canvas.value.getBoundingClientRect()
	const mx = e.clientX - rect.left
	const my = e.clientY - rect.top
	balls.forEach(ball => ball.isHover = false)
	for (let ball of balls) {
		if (ball.isPointInBall(mx, my)) {
			ball.isHover = true
			break
		}
	}
}

const handleMouseDown = (e) => {
	if (isFullscreen.value) return
	const rect = canvas.value.getBoundingClientRect()
	const mx = e.clientX - rect.left
	const my = e.clientY - rect.top
	balls.forEach(ball => {
		if (ball.isPointInBall(mx, my)) {
			fullscreenBall = ball
			ball.fullScale = 0.01
			isFullscreen.value = true
		}
	})
}

const handleBack = () => {
	isFullscreen.value = false
	setTimeout(() => {
		if (fullscreenBall) fullscreenBall.fullScale = 0
		fullscreenBall = null
	}, 600)
}

// 滚动处理
const handleScroll = () => {
	const scrollTop = window.scrollY
	isScroll.value = scrollTop > window.innerHeight * 0.1
	if (!isDark.value && isScroll.value) {
		document.querySelector('.navbar')?.style.setProperty('background', 'var(--primary-color)')
	} else {
		document.querySelector('.navbar')?.style.setProperty('background', 'var(--card-bg)')
	}
}

// 轮播图DOM操作
const initCarousel = () => {
	const fitnessTrack = document.getElementById('fitnessCarouselTrack')
	const fitnessItems = document.querySelectorAll('.fitness-carousel-item')
	const fitnessPrevBtn = document.getElementById('fitnessPrevBtn')
	const fitnessNextBtn = document.getElementById('fitnessNextBtn')

	if (!fitnessTrack || fitnessItems.length === 0 || !fitnessPrevBtn || !fitnessNextBtn) {
		return
	}

	let fitnessCurrentIndex = 1
	const fitnessTotalItems = fitnessItems.length

	const fitnessUpdateCarousel = () => {
		fitnessItems.forEach(item => {
			item.classList.remove('fitness-active', 'fitness-left', 'fitness-right', 'fitness-out-left', 'fitness-out-right')
			item.style.transform = ''
			item.style.opacity = ''
			item.style.zIndex = ''
		})

		fitnessItems.forEach((item, index) => {
			const fitnessDiff = index - fitnessCurrentIndex
			if (fitnessDiff === 0) item.classList.add('fitness-active')
			else if (fitnessDiff === -1) item.classList.add('fitness-left')
			else if (fitnessDiff === 1) item.classList.add('fitness-right')
			else if (fitnessDiff <= -2) item.classList.add('fitness-out-left')
			else if (fitnessDiff >= 2) item.classList.add('fitness-out-right')
		})
	}

	fitnessPrevBtn.addEventListener('click', () => {
		fitnessCurrentIndex = (fitnessCurrentIndex - 1 + fitnessTotalItems) % fitnessTotalItems
		fitnessUpdateCarousel()
	})

	fitnessNextBtn.addEventListener('click', () => {
		fitnessCurrentIndex = (fitnessCurrentIndex + 1) % fitnessTotalItems
		fitnessUpdateCarousel()
	})

	fitnessUpdateCarousel()
}

// 生命周期
onMounted(() => {
	// 加载数据
	loadData()

	// 登录状态
	const logined = localStorage.getItem('isLogin') === 'true'
	isLogin.value = logined
	if (logined) {
		const name = localStorage.getItem('username')
		const user = JSON.parse(localStorage.getItem('user_' + name) || '{}')
		Object.assign(userInfo, {
			username: name || '用户',
			motto: user.motto || '自律给我自由',
			fitReason: user.fitReason || '未设置',
			level: user.level || 1,
			exp: user.exp || 0
		})
	}

	// 暗黑模式状态
	const savedIsDark = localStorage.getItem('isDark') === 'true'
	if (savedIsDark) {
		isDark.value = true
		document.documentElement.classList.add('dark-mode')
	}

	// 滚动监听
	window.addEventListener('scroll', handleScroll)
	window.addEventListener('scroll', debounce(checkActiveNav))
	window.addEventListener('scroll', debounce(updateScrollProgress))

	// 轮播图
	startAutoPlay()

	// 想法球
	initCanvas()
	animate()
	canvas.value?.addEventListener('mousemove', handleMouseMove)
	canvas.value?.addEventListener('mousedown', handleMouseDown)
	
	// 加载健身初衷数据
	loadFitReasons()
	
	// 每30秒刷新一次健身初衷数据
	fitReasonsTimer = setInterval(() => {
		loadFitReasons()
	}, 30000)

	// 健身明星轮播
	initCarousel()

	// 初始化导航高亮
	checkActiveNav()
	updateScrollProgress()
})

onUnmounted(() => {
	window.removeEventListener('scroll', handleScroll)
	clearInterval(timer)
	clearInterval(fitReasonsTimer)
	cancelAnimationFrame(animationId)
	canvas.value?.removeEventListener('mousemove', handleMouseMove)
	canvas.value?.removeEventListener('mousedown', handleMouseDown)
})
</script>

<style scoped>
* { margin: 0; padding: 0; box-sizing: border-box; }
html, body { margin: 0; padding: 0; background: linear-gradient(180deg, #f0f9ff 0%, #ffffff 100%); min-height: 100vh; transition: background 0.3s ease; }
.dark-mode html, .dark-mode body { background: linear-gradient(180deg, #2a3744 0%, #1f2932 100%); }

:root {
	--primary-color: #4ecdc4;
	--primary-light: #a8edea;
	--text-color: #2c3e50;
	--bg-color: #f8fafc;
	--card-bg: #ffffff;
	--shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

.fitness-home { width: 100%; background: var(--bg-color); min-height: 100vh; transition: all 0.3s ease; }

.navbar {
	position: fixed; top: 0; left: 0; right: 0; height: 72px;
	background: var(--card-bg); box-shadow: var(--shadow);
	display: flex; justify-content: space-between; align-items: center;
	padding: 0 48px; z-index: 999; 
	transition: all 0.35s cubic-bezier(0.4, 0, 0.2, 1);
}

.navbar.scroll {
	background: rgba(255, 255, 255, 0.95);
	backdrop-filter: blur(12px);
	box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
}

.nav-left { 
	display: flex; align-items: center; gap: 48px; 
}

.logo { 
	font-size: 22px; font-weight: 700; 
	color: var(--primary-color); 
	letter-spacing: 1.5px;
	display: flex; align-items: center; gap: 8px;
}

.nav-menu { 
	display: flex; gap: 36px; list-style: none; 
	font-size: 15px; color: var(--text-color); 
	font-weight: 500;
}

.nav-menu li { 
	cursor: pointer; 
	padding: 10px 16px; 
	position: relative; 
	transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
	border-radius: 10px;
}

.nav-menu li:hover { 
	color: var(--primary-color);
	background: rgba(78, 205, 196, 0.1);
	transform: translateY(-1px);
}

.nav-menu li.active { 
	color: var(--primary-color);
	background: rgba(78, 205, 196, 0.1);
}

.nav-menu li::after {
	content: ''; position: absolute; bottom: 6px; left: 50%;
	transform: translateX(-50%);
	width: 0; height: 3px; 
	background: linear-gradient(90deg, var(--primary-color), #38b2ac);
	border-radius: 2px;
	transition: width 0.35s cubic-bezier(0.4, 0, 0.2, 1);
}

.nav-menu li:hover::after, .nav-menu li.active::after { 
	width: 50%; 
}

.nav-right { 
	display: flex; align-items: center; gap: 18px; 
	position: relative; 
}

.search-box {
	display: flex; align-items: center;
	background: var(--bg-color);
	border: 2px solid transparent;
	border-radius: 32px; 
	padding: 6px; 
	transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
	min-width: 240px;
}

.search-box:focus-within {
	border-color: var(--primary-color);
	box-shadow: 0 0 0 3px rgba(78, 205, 196, 0.15);
	transform: translateY(-1px);
}

.search-box input {
	border: none; outline: none; 
	padding: 10px 20px;
	border-radius: 28px; 
	width: 100%; 
	background: transparent; 
	font-size: 14px;
	color: var(--text-color);
}

.search-box input::placeholder {
	color: #94a3b8;
}

.search-btn { 
	background: var(--primary-color); 
	border: none; 
	color: white; 
	font-size: 16px; 
	padding: 10px 14px; 
	cursor: pointer; 
	border-radius: 50%;
	transition: all 0.3s;
}

.search-btn:hover {
	background: #38b2ac;
	transform: scale(1.1);
	box-shadow: 0 4px 12px rgba(78, 205, 196, 0.4);
}

.theme-toggle {
	width: 42px; height: 42px; 
	border-radius: 50%;
	background: var(--bg-color); 
	display: grid; place-items: center;
	cursor: pointer; 
	font-size: 20px; 
	transition: all 0.35s cubic-bezier(0.4, 0, 0.2, 1);
	border: 2px solid transparent;
}

.theme-toggle:hover { 
	background: rgba(78, 205, 196, 0.15);
	border-color: var(--primary-color);
	transform: scale(1.08);
}

.avatar {
	width: 44px; height: 44px; 
	border-radius: 50%;
	background: linear-gradient(135deg, var(--primary-color), #38b2ac);
	display: grid; place-items: center; 
	color: white; font-size: 20px;
	cursor: pointer; 
	transition: all 0.35s cubic-bezier(0.4, 0, 0.2, 1);
	box-shadow: 0 4px 12px rgba(78, 205, 196, 0.3);
	border: 3px solid white;
}

.avatar:hover { 
	transform: scale(1.1);
	box-shadow: 0 6px 20px rgba(78, 205, 196, 0.4);
}

.login-btn {
	padding: 11px 28px; 
	background: linear-gradient(135deg, var(--primary-color), #38b2ac); 
	color: #fff;
	border-radius: 25px; 
	font-size: 14px; 
	font-weight: 600;
	cursor: pointer; 
	transition: all 0.35s cubic-bezier(0.4, 0, 0.2, 1);
	box-shadow: 0 4px 14px rgba(78, 205, 196, 0.3);
	border: none;
	position: relative;
	overflow: hidden;
}

.login-btn::before {
	content: ''; position: absolute; top: 0; left: -100%;
	width: 100%; height: 100%;
	background: linear-gradient(90deg, transparent, rgba(255,255,255,0.2), transparent);
	transition: left 0.5s;
}

.login-btn:hover::before {
	left: 100%;
}

.login-btn:hover {
	background: linear-gradient(135deg, #38b2ac, #2cb6ad);
	box-shadow: 0 6px 20px rgba(78, 205, 196, 0.45);
	transform: translateY(-2px);
}

.user-level { 
	font-size: 13px; 
	color: var(--primary-color); 
	font-weight: 600;
	padding: 6px 16px;
	background: rgba(78, 205, 196, 0.1);
	border-radius: 20px;
}

.user-card {
	position: absolute; top: calc(100% + 16px); right: 0;
	width: 280px; 
	background: #ffffff;
	border-radius: 20px; 
	box-shadow: 0 12px 40px rgba(0, 0, 0, 0.12);
	padding: 24px; 
	z-index: 1000;
	border: 1px solid #f0f0f0;
	transform: translateY(10px);
	opacity: 0;
	visibility: hidden;
	transition: all 0.35s cubic-bezier(0.4, 0, 0.2, 1);
}

.user-card.show {
	transform: translateY(0);
	opacity: 1;
	visibility: visible;
}

.dark-mode .user-card {
	background: #252540;
	border-color: #3a3a5a;
}

.user-card::before {
	content: ''; position: absolute; top: -10px; right: 28px;
	border-left: 10px solid transparent;
	border-right: 10px solid transparent;
	border-bottom: 10px solid #ffffff;
}

.dark-mode .user-card::before {
	border-bottom-color: #252540;
}

.card-item {
	display: flex; justify-content: space-between;
	align-items: center;
	padding: 14px 0;
	border-bottom: 1px solid #f0f0f0;
}

.dark-mode .card-item {
	border-bottom-color: #3a3a5a;
}

.card-item:last-of-type { border-bottom: none; }

.card-item label {
	font-size: 12px; 
	color: #999;
	min-width: 75px;
	font-weight: 600;
	text-transform: uppercase;
	letter-spacing: 0.5px;
}

.dark-mode .card-item label {
	color: #888;
}

.card-item div {
	font-size: 14px;
	color: #333;
	font-weight: 500;
	text-align: right;
	flex: 1;
	max-width: 160px;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.dark-mode .card-item div {
	color: #f1f1f1;
}

.logout-btn {
	width: 100%;
	margin-top: 20px;
	padding: 14px;
	background: linear-gradient(135deg, #ff6b6b, #ee5a5a);
	color: #fff;
	border: none;
	border-radius: 12px;
	font-size: 14px;
	font-weight: 600;
	cursor: pointer;
	transition: all 0.35s cubic-bezier(0.4, 0, 0.2, 1);
	box-shadow: 0 5px 16px rgba(238, 90, 90, 0.35);
	position: relative;
	overflow: hidden;
}

.logout-btn::before {
	content: ''; position: absolute; top: 0; left: -100%;
	width: 100%; height: 100%;
	background: linear-gradient(90deg, transparent, rgba(255,255,255,0.2), transparent);
	transition: left 0.5s;
}

.logout-btn:hover::before {
	left: 100%;
}

.logout-btn:hover {
	background: linear-gradient(135deg, #ee5a5a, #dc4949);
	transform: translateY(-2px);
	box-shadow: 0 8px 22px rgba(238, 90, 90, 0.45);
}

.dropdown { position: relative; cursor: pointer; }

.dropdown span { 
	display: flex; align-items: center; 
	gap: 6px; 
	font-weight: 500;
}

.dropdown-menu {
	position: absolute; 
	top: calc(100% + 12px); 
	left: 50%;
	transform: translateX(-50%) translateY(8px); 
	width: 180px;
	background: var(--card-bg); 
	border-radius: 16px;
	box-shadow: 0 8px 28px rgba(0, 0, 0, 0.1);
	padding: 8px 0; 
	opacity: 0; 
	visibility: hidden;
	transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1); 
	z-index: 1000;
	border: 1px solid rgba(0,0,0,0.06);
}

.dark-mode .dropdown-menu {
	border-color: rgba(255,255,255,0.08);
}

.dropdown:hover .dropdown-menu {
	opacity: 1; 
	visibility: visible;
	transform: translateX(-50%) translateY(0);
}

.dropdown-menu div {
	padding: 12px 22px; 
	font-size: 14px; 
	color: var(--text-color);
	transition: all 0.25s ease;
}

.dropdown-menu div:hover {
	background: rgba(78, 205, 196, 0.1); 
	color: var(--primary-color);
	padding-left: 28px;
	font-weight: 500;
}

.top-banner { margin-top: 72px; width: 100%; height: 340px; overflow: hidden; }
.banner-img { width: 100%; height: 100%; object-fit: cover; object-position: center center; }

.swiper-container {
	width: calc(100% - 40px); height: 300px; margin: 20px auto;
	border-radius: 16px; overflow: hidden; position: relative;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}
.swiper-wrapper { width: 100%; height: 100%; display: flex; transition: transform 0.4s ease; }
.swiper-slide { width: 100%; flex-shrink: 0; }
.swiper-slide img { width: 100%; height: 100%; object-fit: cover; object-position: center center; }

.swiper-dots {
	position: absolute; bottom: 12px; left: 50%;
	transform: translateX(-50%); display: flex; gap: 8px;
}
.dot {
	width: 8px; height: 8px; border-radius: 50%;
	background: rgba(255, 255, 255, 0.4); transition: all 0.3s;
}
.dot.active { background: var(--primary-color); width: 22px; border-radius: 4px; }

.idea-ball-container {
	width: calc(100% - 40px); height: 400px; margin: 20px auto;
	border-radius: 16px; overflow: hidden;
	background: linear-gradient(150deg, #1a2a3c 0%, #2c3e50 50%, #3a4a5c 100%);
	position: relative;
}
#ideaCanvas { width: 100%; height: 100%; cursor: pointer; }
.back-btn {
	position: absolute; top: 20px; left: 20px;
	padding: 8px 18px; background: rgba(255, 255, 255, 0.15);
	border: none; border-radius: 30px; color: #fff; z-index: 999;
	backdrop-filter: blur(8px);
}

.content-container {
	width: 95%; margin: 0 auto; padding: 25px;
	background-color: #f9fbfc; border-radius: 12px;
}

.section { margin-bottom: 20px; }
.section-title {
	font-size: 18px; font-weight: 600; margin-bottom: 12px;
	color: #4a7c59; border-left: 3px solid #7fb069;
	padding-left: 8px;
}

.card-list {
	display: flex; align-items: flex-start; gap: 20px; flex-wrap: wrap;
	position: relative; padding-bottom: 10px; padding-top: 40px;
}

.more-btn {
	position: absolute; top: 0; right: 0; z-index: 10;
	font-size: 14px; color: #6a994e; cursor: pointer;
	padding: 6px 12px; background-color: #f0f7f0;
	border-radius: 20px; transition: all 0.3s ease;
	transform: translateY(-5px);
	text-decoration: none;
}
.more-btn:hover {
	color: #fff; background-color: #7fb069;
	transform: scale(1.05);
}

.card {
	width: 200px; height: 120px; border-radius: 15px;
	position: relative; overflow: hidden; cursor: pointer;
	transition: all 0.3s ease;
	box-shadow: 0 2px 8px rgba(127, 176, 105, 0.1);
	border: none;
}
.card:hover {
	transform: translateY(-5px);
	box-shadow: 0 5px 15px rgba(127, 176, 105, 0.15);
}

.card-bg {
	position: absolute; top: 0; left: 0;
	width: 100%; height: 100%;
	background-size: cover; background-position: center;
	opacity: 0.18; filter: blur(0.5px);
}

.card-content {
	position: relative; z-index: 2;
	width: 100%; height: 100%; padding: 15px;
	display: flex; align-items: center; justify-content: center;
	text-align: center;
}
.card-text { font-size: 14px; margin: 0; line-height: 1.5; font-weight: 500; }

.science-card { background-color: #e8f4f8; }
.science-card .card-text { color: #2d6a4f; }
.science-card:first-child { box-shadow: 0 0 0 2px #95d5b2; }

.action-card { background-color: #fdf2f8; }
.action-card .card-text { color: #e56b6f; }
.action-card:hover { background-color: #fef7fb; }

.page-structure-panel {
	position: fixed; left: 0; top: 50%;
	transform: translateY(-50%); display: flex;
	align-items: center; cursor: pointer; z-index: 999;
}

.panel-trigger {
	width: 36px; height: 56px;
	background: #ffffff; border-radius: 0 8px 8px 0;
	box-shadow: 2px 0 8px rgba(0, 0, 0, 0.05);
	display: flex; align-items: center; justify-content: center;
}

.trigger-arrow {
	width: 8px; height: 8px;
	border: 2px solid #666; border-top: none; border-right: none;
	transform: rotate(45deg); transition: transform 0.3s ease;
}
.expanded .trigger-arrow { transform: rotate(-135deg); }

.panel-content {
	width: 220px; background: #ffffff;
	border-radius: 0 12px 12px 0;
	box-shadow: 2px 0 12px rgba(0, 0, 0, 0.08);
	padding: 20px 16px;
}

.panel-title {
	font-size: 18px; font-weight: 600; color: #1d2129;
	margin-bottom: 16px; padding-bottom: 12px;
	border-bottom: 1px solid #eee;
}

.panel-list { display: flex; flex-direction: column; gap: 10px; }

.panel-item {
	font-size: 16px; color: #4e5969;
	padding: 10px 12px; border-radius: 6px;
	cursor: pointer; transition: all 0.2s ease;
}
.panel-item:hover {
	background: rgba(43, 125, 253, 0.08) !important;
	color: #2b7dfd !important;
	padding-left: 14px;
}

.back-to-top {
	position: fixed; bottom: 30px; right: 30px;
	width: 56px; height: 56px;
	background: #333; border-radius: 50%;
	display: flex; align-items: center; justify-content: center;
	cursor: pointer; z-index: 999;
	transition: all 0.3s ease;
}
.back-to-top:hover { transform: translateY(-3px); }

.progress-circle {
	position: absolute; width: 100%; height: 100%;
	transform: rotate(-90deg);
}
.progress-ring { stroke: url(#progress-gradient); }

.arrow-icon {
	color: #fff; font-size: 18px;
	display: flex; align-items: center; justify-content: center;
}

.fitness-carousel-wrapper { padding: 20px 0; }
.fitness-carousel-title { text-align: center; margin-bottom: 20px; }
.fitness-carousel-container {
	display: flex; align-items: center; justify-content: center;
	gap: 10px;
}
.fitness-carousel-btn {
	width: 40px; height: 40px;
	background: #333; border: none; border-radius: 50%;
	cursor: pointer; display: flex; align-items: center; justify-content: center;
}
.fitness-carousel-track {
	display: flex; gap: 10px; overflow: hidden;
	width: 600px;
}
.fitness-carousel-item { flex: 1; }
.fitness-carousel-item img { width: 100%; height: 150px; object-fit: cover; }

.home-comm { padding: 40px 20px; }
.comm-head { text-align: center; margin-bottom: 30px; }
.comm-head h3 { font-size: 24px; color: #333; }
.comm-head p { color: #999; }

.comm-list { display: flex; flex-direction: column; gap: 15px; max-width: 600px; margin: 0 auto; }
.comm-item {
	display: flex; gap: 15px; padding: 15px;
	background: #fff; border-radius: 12px;
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}
.user-avatar {
	width: 40px; height: 40px;
	background: linear-gradient(135deg, #4ecdc4, #22c3a3);
	border-radius: 50%; display: flex;
	align-items: center; justify-content: center;
	color: #fff; font-weight: bold;
}
.user-content { flex: 1; }
.user-content h4 { font-size: 16px; color: #333; margin-bottom: 5px; }
.user-content p { font-size: 12px; color: #999; }

.comm-more {
	text-align: center; padding: 15px;
	background: #f0f7f0; color: #6a994e;
	border-radius: 20px; margin-top: 20px;
	cursor: pointer;
	max-width: 200px; margin-left: auto;
	margin-right: auto;
}

.site-footer { background: #2c3e50; padding: 40px 20px; color: #fff; }
.footer-container {
	display: flex; justify-content: space-between;
	max-width: 1200px; margin: 0 auto;
	gap: 40px;
}
.footer-section { flex: 1; }
.footer-quote .quote-text {
	font-style: italic; color: #ccc;
	margin-bottom: 10px; font-size: 14px;
}
.qrcode-box { width: 120px; height: 120px; background: #fff; padding: 10px; border-radius: 10px; }
.qrcode-img { width: 100%; height: 100%; object-fit: cover; }
.info-list { margin-top: 15px; }
.info-item { margin-bottom: 8px; }
.info-label { color: #ccc; font-size: 14px; }
.social-icons { display: flex; gap: 15px; margin-top: 15px; }
.social-icon { color: #ccc; font-size: 20px; }

.footer-nav .nav-title { font-size: 16px; margin-bottom: 15px; }
.nav-list { list-style: none; padding: 0; }
.nav-item { margin-bottom: 10px; }
.nav-link { color: #ccc; text-decoration: none; }
.nav-link:hover { color: #fff; }

.footer-bottom {
	text-align: center; padding-top: 20px;
	border-top: 1px solid #4a5568;
	margin-top: 20px;
}
.copyright-text { color: #666; font-size: 14px; }
</style>
